package com.backend.DuruDuru.global.service.IngredientService;

import com.backend.DuruDuru.global.S3.AmazonS3Manager;
import com.backend.DuruDuru.global.apiPayload.ApiResponse;
import com.backend.DuruDuru.global.apiPayload.code.status.ErrorStatus;
import com.backend.DuruDuru.global.apiPayload.code.status.SuccessStatus;
import com.backend.DuruDuru.global.apiPayload.exception.AuthException;
import com.backend.DuruDuru.global.apiPayload.exception.handler.FridgeHandler;
import com.backend.DuruDuru.global.apiPayload.exception.handler.IngredientHandler;
import com.backend.DuruDuru.global.apiPayload.exception.handler.MemberHandler;
import com.backend.DuruDuru.global.converter.IngredientConverter;
import com.backend.DuruDuru.global.domain.entity.*;
import com.backend.DuruDuru.global.domain.enums.MajorCategory;
import com.backend.DuruDuru.global.domain.enums.MinorCategory;
import com.backend.DuruDuru.global.domain.enums.StorageType;
import com.backend.DuruDuru.global.repository.*;
import com.backend.DuruDuru.global.web.dto.Ingredient.IngredientResponseDTO;
import com.backend.DuruDuru.global.web.dto.Ingredient.IngredientRequestDTO;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class IngredientCommandServiceImpl implements IngredientCommandService {
    @Autowired // 엔티티매니저에만 해당
    private EntityManager entityManager;

    private final MemberRepository memberRepository;
    private final IngredientRepository ingredientRepository;
    private final FridgeRepository fridgeRepository;
    private final AmazonS3Manager s3Manager;
    private final IngredientImageRepository ingredientImageRepository;
    private final UuidRepository uuidRepository;


    private Member findMemberById(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberHandler(ErrorStatus.MEMBER_ID_NULL));
    }

    private Fridge findFridgeById(Long fridgeId) {
        return fridgeRepository.findById(fridgeId)
                .orElseThrow(() -> new FridgeHandler(ErrorStatus.FRIDGE_NOT_FOUND));
    }

    private Ingredient findIngredientById(Long ingredientId) {
        return ingredientRepository.findById(ingredientId)
                .orElseThrow(() -> new IngredientHandler(ErrorStatus.INGREDIENT_NOT_FOUND));
    }

    @Override
    @Transactional
    public Ingredient createRawIngredient(Member member, IngredientRequestDTO.CreateRawIngredientDTO request) {
        validateLoggedInMember(member);
        Fridge fridge = findFridgeById(member.getFridgeId());

        Ingredient newIngredient = IngredientConverter.toIngredient(request);
        newIngredient.setMember(member);
        newIngredient.setFridge(fridge);

        Ingredient savedIngredient = ingredientRepository.save(newIngredient);
        //log.info("New ingredient created. ID: {}", savedIngredient.getIngredientId());
        return savedIngredient;
    }


    @Override
    public Ingredient updateIngredient(Member member, Long ingredientId, IngredientRequestDTO.UpdateIngredientDTO request) {
        validateLoggedInMember(member);
        Ingredient ingredient = findIngredientById(ingredientId);

        ingredient.update(request);
        return ingredient;

    }

    @Override
    public void deleteIngredient(Member member, Long ingredientId) {
        validateLoggedInMember(member);
        Ingredient ingredient = findIngredientById(ingredientId);

        ingredientRepository.delete(ingredient);
    }

    @Override
    public Ingredient registerPurchaseDate(Member member, Long ingredientId, IngredientRequestDTO.PurchaseDateRequestDTO request) {
        validateLoggedInMember(member);
        Ingredient ingredient = findIngredientById(ingredientId);

        ingredient.setPurchaseDate(request.getPurchaseDate());
        return ingredient;
    }

    @Override
    public Ingredient registerExpiryDate(Member member, Long ingredientId, IngredientRequestDTO.ExpiryDateRequestDTO request) {
        validateLoggedInMember(member);
        Ingredient ingredient = findIngredientById(ingredientId);

        ingredient.setExpiryDate(request.getExpiryDate());
        return ingredient;
    }

    @Override
    public Ingredient setStorageType(Member member, Long ingredientId, IngredientRequestDTO.StorageTypeRequestDTO request) {
        validateLoggedInMember(member);
        Ingredient ingredient = findIngredientById(ingredientId);

        ingredient.setStorageType(request.getStorageType());
        return ingredient;
    }


    @Transactional
    @Override
    public Ingredient registerIngredientImage(Member member, Long ingredientId, IngredientRequestDTO.IngredientImageRequestDTO request) {
        validateLoggedInMember(member);
        Ingredient ingredient = findIngredientById(ingredientId);

        if (ingredient.getIngredientImg() != null) {
            s3Manager.deleteFile(ingredient.getIngredientImg().getIngredientImgUrl());
            ingredientImageRepository.deleteByIngredientId(ingredientId);
            entityManager.flush(); // DB에서 Id에 해당하는 튜플 삭제
            entityManager.clear(); // 영속성 컨텍스트 초기화
            ingredient.setIngredientImg(null);
        }

        String uuid = UUID.randomUUID().toString();
        Uuid savedUuid = uuidRepository.save(Uuid.builder().uuid(uuid).build());
        String fileUrl = s3Manager.uploadFile(s3Manager.generatePostName(savedUuid), request.getImage());

        IngredientImg ingredientImg = IngredientImg.builder()
                .ingredientImgUrl(fileUrl)
                .ingredient(ingredient)
                .build();

        ingredient.setIngredientImg(ingredientImg);
        ingredientImageRepository.save(ingredientImg);

        return ingredient;
    }


    @Transactional
    @Override
    public Ingredient setCategory(Member member, Long ingredientId, IngredientRequestDTO.SetCategoryRequestDTO request) {
        validateLoggedInMember(member);
        // 대분류 검증
        MajorCategory majorCategory;
        try {
            majorCategory = MajorCategory.valueOf(request.getMajorCategory());
        } catch (IllegalArgumentException e) {
            throw new IngredientHandler(ErrorStatus.INGREDIENT_NO_MAJOR_CATEGORY);
        }
        // 소분류 검증
        MinorCategory minorCategory;
        try {
            minorCategory = MinorCategory.valueOf(request.getMinorCategory());
        } catch (IllegalArgumentException e) {
            throw new IngredientHandler(ErrorStatus.INGREDIENT_NO_MINOR_CATEGORY);
        }
        // 대분류와 소분류 매칭 검증
        if (!MinorCategory.isValidCategory(majorCategory, minorCategory)) {
            throw new IngredientHandler(ErrorStatus.INGREDIENT_MAJOR_MINOR_NOT_MATCH);
        }

        Ingredient ingredient = findIngredientById(ingredientId);

        LocalDate purchaseDate = (ingredient.getPurchaseDate() != null) ? ingredient.getPurchaseDate() : LocalDate.now();
        int shelfLifeDays = minorCategory.getShelfLifeDays();
        LocalDate updatedExpiryDate = purchaseDate.plusDays(shelfLifeDays);
        StorageType storageType = minorCategory.getStorageType();

        ingredient.updateCategory(majorCategory, minorCategory);
        ingredient.setPurchaseDate(purchaseDate);
        ingredient.setExpiryDate(updatedExpiryDate); // 소비기한 자동 설정
        ingredient.setStorageType(storageType); // 보관 방식 자동 설정 (사용자 변경가능)

        return ingredientRepository.save(ingredient);
    }

    // 로그인 여부 확인
    private void validateLoggedInMember(Member member) {
        if (member == null) {
            throw new AuthException(ErrorStatus.LOGIN_REQUIRED);
        }
    }


}