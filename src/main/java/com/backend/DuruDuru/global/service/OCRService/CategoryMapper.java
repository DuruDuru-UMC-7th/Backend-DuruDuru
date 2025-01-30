package com.backend.DuruDuru.global.service.OCRService;

//import com.backend.DuruDuru.global.domain.enums.MinorCategory;
//import org.springframework.stereotype.Component;
//
//@Component
//public class CategoryMapper {
//
//    public MinorCategory mapToMinorCategory(String productName) {
//        if (productName.contains("닭")) {
//            return MinorCategory.닭고기;
//        } else if (productName.contains("양배추")) {
//            return MinorCategory.잎채소;
//        } else if (productName.contains("대파")) {
//            return MinorCategory.대파;
//        }
//        return null; // 매핑되지 않는 경우 null 반환
//    }
//}

import com.backend.DuruDuru.global.domain.enums.MinorCategory;
import org.apache.commons.text.similarity.LevenshteinDistance;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class CategoryMapper {

    private final Map<String, MinorCategory> keywordToMinorCategoryMap;
    private final LevenshteinDistance levenshteinDistance;

    public CategoryMapper() {
        this.keywordToMinorCategoryMap = new HashMap<>();
        this.levenshteinDistance = new LevenshteinDistance();

        // 과일
        keywordToMinorCategoryMap.put("바나나", MinorCategory.과채류);
        keywordToMinorCategoryMap.put("참외", MinorCategory.과채류);
        keywordToMinorCategoryMap.put("수박", MinorCategory.과채류);
        keywordToMinorCategoryMap.put("아보카도", MinorCategory.과채류);

        keywordToMinorCategoryMap.put("딸기", MinorCategory.베리류);
        keywordToMinorCategoryMap.put("블루베리", MinorCategory.베리류);
        keywordToMinorCategoryMap.put("라즈베리", MinorCategory.베리류);
        keywordToMinorCategoryMap.put("체리", MinorCategory.베리류);

        keywordToMinorCategoryMap.put("포도", MinorCategory.포도);
        keywordToMinorCategoryMap.put("사과", MinorCategory.사과);
        keywordToMinorCategoryMap.put("배", MinorCategory.배);
        keywordToMinorCategoryMap.put("감", MinorCategory.감);
        keywordToMinorCategoryMap.put("복숭아", MinorCategory.복숭아);
        keywordToMinorCategoryMap.put("망고", MinorCategory.망고);
        keywordToMinorCategoryMap.put("멜론", MinorCategory.멜론);
        keywordToMinorCategoryMap.put("파인애플", MinorCategory.파인애플);

        keywordToMinorCategoryMap.put("땅콩", MinorCategory.견과류);
        keywordToMinorCategoryMap.put("호두", MinorCategory.견과류);
        keywordToMinorCategoryMap.put("아몬드", MinorCategory.견과류);
        keywordToMinorCategoryMap.put("캐슈넛", MinorCategory.견과류);
        keywordToMinorCategoryMap.put("피스타치오", MinorCategory.견과류);
        keywordToMinorCategoryMap.put("해바라기씨", MinorCategory.견과류);
        keywordToMinorCategoryMap.put("호박씨", MinorCategory.견과류);

        keywordToMinorCategoryMap.put("귤", MinorCategory.감귤류);
        keywordToMinorCategoryMap.put("레몬", MinorCategory.감귤류);
        keywordToMinorCategoryMap.put("오렌지", MinorCategory.감귤류);
        keywordToMinorCategoryMap.put("유자", MinorCategory.감귤류);
        keywordToMinorCategoryMap.put("한라봉", MinorCategory.감귤류);


        // 육류
        keywordToMinorCategoryMap.put("삼겹살", MinorCategory.돼지고기);
        keywordToMinorCategoryMap.put("목살", MinorCategory.돼지고기);
        keywordToMinorCategoryMap.put("등심", MinorCategory.돼지고기);
        keywordToMinorCategoryMap.put("앞다리살", MinorCategory.돼지고기);
        keywordToMinorCategoryMap.put("살", MinorCategory.돼지고기);
        keywordToMinorCategoryMap.put("돈육", MinorCategory.돼지고기);

        keywordToMinorCategoryMap.put("부채살", MinorCategory.소고기);
        keywordToMinorCategoryMap.put("안창살", MinorCategory.소고기);
        keywordToMinorCategoryMap.put("안심스테이크", MinorCategory.소고기);


        keywordToMinorCategoryMap.put("닭볶음탕", MinorCategory.닭고기);
        keywordToMinorCategoryMap.put("닭가슴살", MinorCategory.닭고기);
        keywordToMinorCategoryMap.put("닭다리", MinorCategory.닭고기);
        keywordToMinorCategoryMap.put("닭날개", MinorCategory.닭고기);
        keywordToMinorCategoryMap.put("닭안심", MinorCategory.닭고기);
        keywordToMinorCategoryMap.put("닭다리살", MinorCategory.닭고기);

        keywordToMinorCategoryMap.put("베이컨", MinorCategory.가공육);
        keywordToMinorCategoryMap.put("비엔나", MinorCategory.가공육);
        keywordToMinorCategoryMap.put("소시지", MinorCategory.가공육);
        keywordToMinorCategoryMap.put("햄", MinorCategory.가공육);
        keywordToMinorCategoryMap.put("훈제오리", MinorCategory.가공육);
        keywordToMinorCategoryMap.put("핫도그", MinorCategory.가공육);

        // 수산물
        keywordToMinorCategoryMap.put("대구", MinorCategory.담백한생선);
        keywordToMinorCategoryMap.put("명태", MinorCategory.담백한생선);
        keywordToMinorCategoryMap.put("갈치", MinorCategory.담백한생선);
        keywordToMinorCategoryMap.put("광어", MinorCategory.담백한생선);
        keywordToMinorCategoryMap.put("오징어", MinorCategory.두족류);
        keywordToMinorCategoryMap.put("문어", MinorCategory.두족류);
        keywordToMinorCategoryMap.put("낙지", MinorCategory.두족류);

        keywordToMinorCategoryMap.put("연어", MinorCategory.기름진생선);
        keywordToMinorCategoryMap.put("참치", MinorCategory.기름진생선);
        keywordToMinorCategoryMap.put("고등어", MinorCategory.기름진생선);
        keywordToMinorCategoryMap.put("바지락", MinorCategory.조개류);
        keywordToMinorCategoryMap.put("홍합", MinorCategory.조개류);
        keywordToMinorCategoryMap.put("전복", MinorCategory.조개류);

        keywordToMinorCategoryMap.put("새우", MinorCategory.갑각류);
        keywordToMinorCategoryMap.put("게", MinorCategory.갑각류);
        keywordToMinorCategoryMap.put("랍스터", MinorCategory.갑각류);
        keywordToMinorCategoryMap.put("대하", MinorCategory.갑각류);
        keywordToMinorCategoryMap.put("꽃게", MinorCategory.갑각류);
        keywordToMinorCategoryMap.put("꼬막", MinorCategory.갑각류);
        keywordToMinorCategoryMap.put("바닷가재", MinorCategory.갑각류);
        keywordToMinorCategoryMap.put("바닷게", MinorCategory.갑각류);


        // 달걀
        keywordToMinorCategoryMap.put("생란", MinorCategory.달걀);
        keywordToMinorCategoryMap.put("삶은달걀", MinorCategory.달걀);
        keywordToMinorCategoryMap.put("란", MinorCategory.달걀);

        // 유제품
        keywordToMinorCategoryMap.put("치즈", MinorCategory.치즈);
        keywordToMinorCategoryMap.put("요거트", MinorCategory.요거트);
        keywordToMinorCategoryMap.put("버터", MinorCategory.버터);
        keywordToMinorCategoryMap.put("우유", MinorCategory.우유);
        keywordToMinorCategoryMap.put("크림", MinorCategory.크림);
        keywordToMinorCategoryMap.put("아이스크림", MinorCategory.아이스크림);
        keywordToMinorCategoryMap.put("요플레", MinorCategory.요거트);
        keywordToMinorCategoryMap.put("야쿠르트", MinorCategory.요거트);
        keywordToMinorCategoryMap.put("비피더스", MinorCategory.요거트);
        keywordToMinorCategoryMap.put("생크림", MinorCategory.크림);
        keywordToMinorCategoryMap.put("휘핑크림", MinorCategory.크림);



        // 채소
        keywordToMinorCategoryMap.put("양배추", MinorCategory.잎채소);
        keywordToMinorCategoryMap.put("상추", MinorCategory.잎채소);
        keywordToMinorCategoryMap.put("깻잎", MinorCategory.잎채소);
        keywordToMinorCategoryMap.put("케일", MinorCategory.잎채소);
        keywordToMinorCategoryMap.put("시금치", MinorCategory.잎채소);

        keywordToMinorCategoryMap.put("대파", MinorCategory.대파);

        keywordToMinorCategoryMap.put("양파", MinorCategory.양파);

        keywordToMinorCategoryMap.put("감자", MinorCategory.뿌리채소);
        keywordToMinorCategoryMap.put("당근", MinorCategory.뿌리채소);
        keywordToMinorCategoryMap.put("고구마", MinorCategory.뿌리채소);
        keywordToMinorCategoryMap.put("무", MinorCategory.뿌리채소);
        keywordToMinorCategoryMap.put("도라지", MinorCategory.뿌리채소);
        keywordToMinorCategoryMap.put("우엉", MinorCategory.뿌리채소);

        keywordToMinorCategoryMap.put("토마토", MinorCategory.과채류);
        keywordToMinorCategoryMap.put("호박", MinorCategory.과채류);
        keywordToMinorCategoryMap.put("오이", MinorCategory.과채류);
        keywordToMinorCategoryMap.put("피망", MinorCategory.과채류);
        keywordToMinorCategoryMap.put("파프리카", MinorCategory.과채류);
        keywordToMinorCategoryMap.put("가지", MinorCategory.과채류);
        keywordToMinorCategoryMap.put("풋고추", MinorCategory.과채류);
        keywordToMinorCategoryMap.put("고추", MinorCategory.과채류);

        keywordToMinorCategoryMap.put("브로콜리", MinorCategory.브로콜리);

        keywordToMinorCategoryMap.put("콩나물", MinorCategory.콩나물);
        keywordToMinorCategoryMap.put("숙주나물", MinorCategory.숙주나물);

        keywordToMinorCategoryMap.put("콩", MinorCategory.콩류);
        keywordToMinorCategoryMap.put("녹두", MinorCategory.콩류);
        keywordToMinorCategoryMap.put("팥", MinorCategory.콩류);
        keywordToMinorCategoryMap.put("완두콩", MinorCategory.콩류);
        keywordToMinorCategoryMap.put("검정콩", MinorCategory.콩류);
        keywordToMinorCategoryMap.put("강낭콩", MinorCategory.콩류);

        keywordToMinorCategoryMap.put("마늘", MinorCategory.마늘);
        keywordToMinorCategoryMap.put("생강", MinorCategory.마늘);
        keywordToMinorCategoryMap.put("다진마늘", MinorCategory.마늘);
        keywordToMinorCategoryMap.put("다진생강", MinorCategory.마늘);
        keywordToMinorCategoryMap.put("마늘쫑", MinorCategory.마늘);
        keywordToMinorCategoryMap.put("마늘장아찌", MinorCategory.마늘);
        keywordToMinorCategoryMap.put("깐마늘", MinorCategory.마늘);


        // 버섯
        keywordToMinorCategoryMap.put("팽이버섯", MinorCategory.버섯);
        keywordToMinorCategoryMap.put("새송이버섯", MinorCategory.버섯);
        keywordToMinorCategoryMap.put("표고버섯", MinorCategory.버섯);
        keywordToMinorCategoryMap.put("느타리버섯", MinorCategory.버섯);
        keywordToMinorCategoryMap.put("맛타리버섯", MinorCategory.버섯);
        keywordToMinorCategoryMap.put("피망버섯", MinorCategory.버섯);


        // 두부
        keywordToMinorCategoryMap.put("두부", MinorCategory.두부);
        keywordToMinorCategoryMap.put("순두부", MinorCategory.가공두부);
        keywordToMinorCategoryMap.put("연두부", MinorCategory.가공두부);
        keywordToMinorCategoryMap.put("찌개용두부", MinorCategory.가공두부);


        // 건조식품
        keywordToMinorCategoryMap.put("말린망고", MinorCategory.건조과일);
        keywordToMinorCategoryMap.put("말린바나나", MinorCategory.건조과일);
        keywordToMinorCategoryMap.put("건미역", MinorCategory.건미역);
        keywordToMinorCategoryMap.put("건포도", MinorCategory.건조과일);


        // 기타
        keywordToMinorCategoryMap.put("참치캔", MinorCategory.통조림);
        keywordToMinorCategoryMap.put("꽁치통조림", MinorCategory.통조림);
        keywordToMinorCategoryMap.put("동원참치", MinorCategory.통조림);

        keywordToMinorCategoryMap.put("과자", MinorCategory.과자);
        keywordToMinorCategoryMap.put("초콜릿", MinorCategory.초콜릿);
        keywordToMinorCategoryMap.put("탄산음료", MinorCategory.탄산음료);

        keywordToMinorCategoryMap.put("묵류", MinorCategory.묵류);
        keywordToMinorCategoryMap.put("도토리묵", MinorCategory.묵류);

        keywordToMinorCategoryMap.put("고추장", MinorCategory.장류);
        keywordToMinorCategoryMap.put("된장", MinorCategory.장류);
        keywordToMinorCategoryMap.put("간장", MinorCategory.장류);
        keywordToMinorCategoryMap.put("청국장", MinorCategory.장류);
        keywordToMinorCategoryMap.put("쌈장", MinorCategory.장류);

        keywordToMinorCategoryMap.put("밀떡", MinorCategory.밀떡);
        keywordToMinorCategoryMap.put("쌀떡", MinorCategory.쌀떡);

        keywordToMinorCategoryMap.put("파스타면", MinorCategory.건조면류);
        keywordToMinorCategoryMap.put("쌀면", MinorCategory.건조면류);
        keywordToMinorCategoryMap.put("메밀면", MinorCategory.건조면류);
        keywordToMinorCategoryMap.put("전분면", MinorCategory.건조면류);
        keywordToMinorCategoryMap.put("건면", MinorCategory.건조면류);
        keywordToMinorCategoryMap.put("밀면", MinorCategory.생면);
        keywordToMinorCategoryMap.put("생면", MinorCategory.생면);

        keywordToMinorCategoryMap.put("라면", MinorCategory.라면);
        keywordToMinorCategoryMap.put("신라면", MinorCategory.라면);
        keywordToMinorCategoryMap.put("진라면", MinorCategory.라면);
        keywordToMinorCategoryMap.put("너구리", MinorCategory.라면);
        keywordToMinorCategoryMap.put("짜파게티", MinorCategory.라면);
        keywordToMinorCategoryMap.put("짬뽕", MinorCategory.라면);
        keywordToMinorCategoryMap.put("컵라면", MinorCategory.라면);
        keywordToMinorCategoryMap.put("튀김우동", MinorCategory.라면);
        keywordToMinorCategoryMap.put("새우탕", MinorCategory.라면);

        keywordToMinorCategoryMap.put("케찹", MinorCategory.소스류);
        keywordToMinorCategoryMap.put("마요네즈", MinorCategory.소스류);
        keywordToMinorCategoryMap.put("머스타드", MinorCategory.소스류);
        keywordToMinorCategoryMap.put("바베큐소스", MinorCategory.소스류);

        keywordToMinorCategoryMap.put("빵", MinorCategory.기타);
        keywordToMinorCategoryMap.put("과자", MinorCategory.기타);
        keywordToMinorCategoryMap.put("초콜릿", MinorCategory.기타);
        keywordToMinorCategoryMap.put("밥", MinorCategory.기타);
        keywordToMinorCategoryMap.put("햇반", MinorCategory.기타);

        keywordToMinorCategoryMap.put("설탕", MinorCategory.조미료류);
        keywordToMinorCategoryMap.put("소금", MinorCategory.조미료류);


        // 가루
        keywordToMinorCategoryMap.put("밀가루", MinorCategory.곡물가루);
        keywordToMinorCategoryMap.put("쌀가루", MinorCategory.곡물가루);
        keywordToMinorCategoryMap.put("옥수수가루", MinorCategory.곡물가루);
        keywordToMinorCategoryMap.put("감자가루", MinorCategory.곡물가루);
        keywordToMinorCategoryMap.put("부침가루", MinorCategory.곡물가루);
        keywordToMinorCategoryMap.put("빵가루", MinorCategory.곡물가루);
        keywordToMinorCategoryMap.put("콩가루", MinorCategory.곡물가루);
        keywordToMinorCategoryMap.put("찹쌀가루", MinorCategory.곡물가루);
        keywordToMinorCategoryMap.put("튀김가루", MinorCategory.곡물가루);

        keywordToMinorCategoryMap.put("고추가루", MinorCategory.고춧가루);

        keywordToMinorCategoryMap.put("참깨가루", MinorCategory.깨가루);
        keywordToMinorCategoryMap.put("들깨가루", MinorCategory.깨가루);

        keywordToMinorCategoryMap.put("옥수수전분", MinorCategory.전분);
        keywordToMinorCategoryMap.put("감자전분", MinorCategory.전분);

        keywordToMinorCategoryMap.put("콩가루", MinorCategory.콩가루);

    }

    public MinorCategory mapToMinorCategory(String productName) {
        // Step 1: 키워드 기반 매칭
        for (Map.Entry<String, MinorCategory> entry : keywordToMinorCategoryMap.entrySet()) {
            if (productName.contains(entry.getKey())) {
                return entry.getValue();
            }
        }

        // Step 2: Fuzzy Matching 기반 매칭
        String bestMatch = null;
        int bestDistance = Integer.MAX_VALUE;

        for (String keyword : keywordToMinorCategoryMap.keySet()) {
            int distance = levenshteinDistance.apply(productName, keyword);
            if (distance < bestDistance) {
                bestDistance = distance;
                bestMatch = keyword;
            }
        }

        // 거리 임계값(Threshold) 설정: 5 이내로 유사할 경우만 매칭
        return (bestMatch != null && bestDistance < 5) ? keywordToMinorCategoryMap.get(bestMatch) : null;
    }
}

