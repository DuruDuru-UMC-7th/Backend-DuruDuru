package com.backend.DuruDuru.global.service.OCRService;

import com.backend.DuruDuru.global.domain.enums.MajorCategory;
import com.backend.DuruDuru.global.domain.enums.MinorCategory;
import com.backend.DuruDuru.global.web.dto.Ingredient.IngredientResponseDTO;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.UUID;


@Service
@Slf4j
public class ClovaOCRReceiptService {

    @Value("${clova.ocr.api-url}")
    private String apiUrl;

    @Value("${clova.ocr.secret-key}")
    private String secretKey;

    private final CategoryMapper categoryMapper;

    public ClovaOCRReceiptService(CategoryMapper categoryMapper) {
        this.categoryMapper = categoryMapper;
    }


    public List<String> extractProductNames(MultipartFile file) {
        try {
            // Step 1: 리사이즈
            log.info("Original file size: {} bytes", file.getSize());
            byte[] resizedImage = ImageResizer.resizeImage(file, 1280, 720);
            log.info("Resized file size: {} bytes", resizedImage.length);

            // Step 2: Base64 인코딩
            String base64Image = Base64.getEncoder().encodeToString(resizedImage);

            // Step 3: HTTP 연결 생성 및 요청 전송
            HttpURLConnection connection = createRequestHeader(new URL(apiUrl));
            createRequestBody(connection, base64Image, "jpg");
            String response = getResponseData(connection);

            log.info("OCR Response: {}", response);

            // Step 4: 응답 데이터 처리
            if (isCoupangScreenshot(response)) {
                log.info("Detected Coupang screenshot.");
                return parseCoupangPurchaseNames(response);
            } else {
                log.info("Detected receipt image.");
                return parseProductNames(response);
            }
        } catch (Exception e) {
            log.error("Error during OCR process", e);
            return new ArrayList<>();
        }
    }

    private HttpURLConnection createRequestHeader(URL url) throws Exception {
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
        connection.setRequestProperty("X-OCR-SECRET", secretKey);
        connection.setDoOutput(true);
        return connection;
    }

    private void createRequestBody(HttpURLConnection connection, String base64Image, String format) throws Exception {
        JSONObject image = new JSONObject();
        image.put("format", format);
        image.put("data", base64Image);
        image.put("name", "receipt_test");

        JSONArray images = new JSONArray();
        images.put(image);

        JSONObject request = new JSONObject();
        request.put("version", "V2");
        request.put("requestId", UUID.randomUUID().toString());
        request.put("timestamp", System.currentTimeMillis());
        request.put("images", images);

        try (OutputStream os = connection.getOutputStream()) {
            byte[] input = request.toString().getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);
        }
    }

    private String getResponseData(HttpURLConnection connection) throws Exception {
        int responseCode = connection.getResponseCode();
        if (responseCode != 200) {
            try (BufferedReader errorReader = new BufferedReader(new InputStreamReader(connection.getErrorStream(), StandardCharsets.UTF_8))) {
                StringBuilder errorResponse = new StringBuilder();
                String line;
                while ((line = errorReader.readLine()) != null) {
                    errorResponse.append(line.trim());
                }
                log.error("API Error Response: {}", errorResponse);
                throw new RuntimeException("Error from API: " + errorResponse);
            }
        }
        try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8))) {
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                response.append(line.trim());
            }
            return response.toString();
        }
    }

    public static class ImageResizer {
        public static byte[] resizeImage(MultipartFile file, int targetWidth, int targetHeight) throws IOException {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

            Thumbnails.of(file.getInputStream())
                    .size(targetWidth, targetHeight)
                    .outputFormat("jpg")
                    .toOutputStream(outputStream);

            return outputStream.toByteArray();
        }
    }

    private List<String> parseProductNames(String response) {
        List<String> productNames = new ArrayList<>();
        try {
            JSONObject jsonResponse = new JSONObject(response);
            JSONArray images = jsonResponse.getJSONArray("images");
            JSONObject image = images.getJSONObject(0);
            JSONObject receipt = image.getJSONObject("receipt");
            JSONArray subResults = receipt.getJSONObject("result").getJSONArray("subResults");

            for (int i = 0; i < subResults.length(); i++) {
                JSONArray items = subResults.getJSONObject(i).getJSONArray("items");
                for (int j = 0; j < items.length(); j++) {
                    JSONObject item = items.getJSONObject(j);
                    String productName = item.getJSONObject("name").getString("text");
                    productNames.add(productName);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return productNames;
    }


    private List<String> parseCoupangPurchaseNames(String response) {
        List<String> productNames = new ArrayList<>();
        try {
            JSONObject jsonResponse = new JSONObject(response);
            JSONArray images = jsonResponse.getJSONArray("images");

            for (int i = 0; i < images.length(); i++) {
                JSONObject image = images.getJSONObject(i);

                // OCR 인식 결과 확인
                if (!image.has("fields")) continue;
                JSONArray fields = image.getJSONArray("fields");

                StringBuilder coupangTextBuilder = new StringBuilder();
                for (int j = 0; j < fields.length(); j++) {
                    JSONObject field = fields.getJSONObject(j);

                    // 텍스트 추출
                    String inferText = field.getString("inferText");
                    // 쿠팡 구매내역 스크린샷의 경우 특정 패턴으로 데이터 추출
                    coupangTextBuilder.append(inferText).append(" ");
                }

                // 텍스트를 라인 단위로 나누고, 특정 규칙을 적용하여 상품명 추출
                String[] lines = coupangTextBuilder.toString().split("\n");
                for (String line : lines) {
                    if (line.matches(".*\\d+.*원.*")) { // "가격"이 포함된 라인
                        String[] splitLine = line.split("원"); // "원"으로 분리
                        if (splitLine.length > 0) {
                            String productName = splitLine[0].trim();
                            productNames.add(productName);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return productNames;
    }

    private boolean isCoupangScreenshot(String response) {
        try {
            JSONObject jsonResponse = new JSONObject(response);
            JSONArray images = jsonResponse.getJSONArray("images");

            for (int i = 0; i < images.length(); i++) {
                JSONObject image = images.getJSONObject(i);

                if (!image.has("fields")) continue;
                JSONArray fields = image.getJSONArray("fields");

                for (int j = 0; j < fields.length(); j++) {
                    JSONObject field = fields.getJSONObject(j);
                    String inferText = field.getString("inferText");

                    // "로켓프레시", "배송완료" 키워드가 포함된 경우, 쿠팡 주문상세 스크린샷으로 간주
                    if (inferText.contains("로켓프레시")) {
                        return true;
                    } else if (inferText.contains("배송완료")) {
                        return true;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    private String getFileExtension(String filename) {
        if (filename == null || !filename.contains(".")) {
            return "jpg"; // 기본값
        }
        return filename.substring(filename.lastIndexOf(".") + 1).toLowerCase();
    }



    public List<IngredientResponseDTO.SetCategoryResultDTO> extractAndCategorizeProductNames(MultipartFile file) {
        try {
            // Step 1: OCR로 상품명 추출
            List<String> productNames = extractProductNames(file);

            // Step 2: 상품명을 기반으로 카테고리 분류
            List<IngredientResponseDTO.SetCategoryResultDTO> categorizedProducts = new ArrayList<>();
            for (String productName : productNames) {
                MinorCategory minorCategory = categoryMapper.mapToMinorCategory(productName);
                MajorCategory majorCategory = minorCategory != null ? minorCategory.getMajorCategory() : MajorCategory.기타;

                categorizedProducts.add(IngredientResponseDTO.SetCategoryResultDTO.builder()
                        .ingredientName(productName)
                        .majorCategory(majorCategory.name())
                        .minorCategory(minorCategory != null ? minorCategory.name() : "기타")
                        .build());
            }

            return categorizedProducts;
        } catch (Exception e) {
            log.error("Error during OCR process", e);
            return new ArrayList<>();
        }
    }



}
