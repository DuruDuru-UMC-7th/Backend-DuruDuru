package com.backend.DuruDuru.global.service.IngredientService;

import com.backend.DuruDuru.global.S3.AmazonS3Manager;
import com.backend.DuruDuru.global.converter.IngredientConverter;
import com.backend.DuruDuru.global.domain.entity.*;
import com.backend.DuruDuru.global.repository.*;
import com.backend.DuruDuru.global.web.dto.Ingredient.IngredientRequestDTO;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
                .orElseThrow(() -> new IllegalArgumentException("Member not found. ID: " + memberId));
    }

    private Fridge findFridgeById(Long fridgeId) {
        return fridgeRepository.findById(fridgeId)
                .orElseThrow(() -> new IllegalArgumentException("Fridge not found. ID: " + fridgeId));
    }

    @Override
    @Transactional
    public Ingredient createRawIngredient(Long memberId, IngredientRequestDTO.CreateRawIngredientDTO request) {
        Member member = findMemberById(memberId);
        Fridge fridge = member.getFridge();
        if (fridge == null) {
            throw new IllegalArgumentException("사용자의 냉장고가 없습니다.");
        }

        Ingredient newIngredient = IngredientConverter.toIngredient(request);
        newIngredient.setMember(member);
        newIngredient.setFridge(fridge);

        Ingredient savedIngredient = ingredientRepository.save(newIngredient);
        //log.info("New ingredient created. ID: {}", savedIngredient.getIngredientId());
        return savedIngredient;
    }


    @Override
    public Ingredient updateIngredient(Long memberId, Long ingredientId, IngredientRequestDTO.UpdateIngredientDTO request) {
        Member member = findMemberById(memberId);
        //Fridge fridge = findFridgeById(fridgeId);
        Ingredient ingredient = ingredientRepository.findById(ingredientId)
                .orElseThrow(() -> new IllegalArgumentException("Ingredient not found. ID: " + ingredientId));

        ingredient.update(request);
        return ingredient;

    }

    @Override
    public void deleteIngredient(Long memberId, Long fridgeId, Long ingredientId) {
        Member member = findMemberById(memberId);
        Fridge fridge = findFridgeById(fridgeId);
        Ingredient ingredient = ingredientRepository.findById(ingredientId)
                .orElseThrow(() -> new IllegalArgumentException("Ingredient not found. ID: " + ingredientId));

        ingredientRepository.delete(ingredient);
    }


    @Override
    public Ingredient registerPurchaseDate(Long memberId, Long ingredientId, IngredientRequestDTO.PurchaseDateRequestDTO request) {
        Member member = findMemberById(memberId);
        Ingredient ingredient = ingredientRepository.findById(ingredientId)
                .orElseThrow(() -> new IllegalArgumentException("Ingredient not found. ID: " + ingredientId));

        ingredient.setPurchaseDate(request.getPurchaseDate());
        return ingredient;
    }

    @Override
    public Ingredient setStorageType(Long memberId, Long ingredientId, IngredientRequestDTO.StorageTypeRequestDTO request) {
        Member member = findMemberById(memberId);
        Ingredient ingredient = ingredientRepository.findById(ingredientId)
                .orElseThrow(() -> new IllegalArgumentException("Ingredient not found. ID: " + ingredientId));

        ingredient.setStorageType(request.getStorageType());
        return ingredient;
    }



    @Transactional
    @Override
    public Ingredient registerIngredientImage(Long memberId, Long ingredientId, IngredientRequestDTO.IngredientImageRequestDTO request) {
        Ingredient ingredient = ingredientRepository.findById(ingredientId)
                .orElseThrow(() -> new IllegalArgumentException("Ingredient not found. ID: " + ingredientId));
        if (ingredient.getIngredientImg() != null) {
            IngredientImg existingImage = ingredient.getIngredientImg();

            s3Manager.deleteFile(existingImage.getIngredientImgUrl());
            ingredient.setIngredientImg(null);

            ingredientRepository.save(ingredient);
            // 삭제 작업을 DB에 반영
            entityManager.flush();
            entityManager.clear();
        }
        // 새로운 식재료 이미지 업로드
        String uuid = UUID.randomUUID().toString();
        String fileUrl = s3Manager.uploadFile(uuid, request.getImage());

        IngredientImg newImage = IngredientImg.builder()
                .ingredientImgUrl(fileUrl)
                .ingredient(ingredient)
                .build();
        ingredient.setIngredientImg(newImage);
        // 식재료 이미지 업데이트
        return ingredientRepository.save(ingredient);
    }



}