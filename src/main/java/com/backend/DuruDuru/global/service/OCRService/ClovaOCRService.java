package com.backend.DuruDuru.global.service.OCRService;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

@Service
public class ClovaOCRService {

    @Value("${clova.ocr.api-url}")
    private String apiUrl;

    @Value("${clova.ocr.secret-key}")
    private String secretKey;

    public List<String> extractProductNames(MultipartFile file) {
        try {
            // 파일을 Base64로 변환
            String base64Image = encodeFileToBase64(file);

            // HTTP 연결 생성 및 요청/응답 처리
            HttpURLConnection connection = createRequestHeader(new URL(apiUrl));
            createRequestBody(connection, base64Image);
            String response = getResponseData(connection);

            // 응답 데이터에서 상품명 추출
            return parseProductNames(response);
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    private String encodeFileToBase64(MultipartFile file) throws Exception {
        byte[] fileBytes = file.getBytes();
        return Base64.getEncoder().encodeToString(fileBytes);
    }

    private HttpURLConnection createRequestHeader(URL url) throws Exception {
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
        connection.setRequestProperty("X-OCR-SECRET", secretKey); // Secret Key 전달
        connection.setDoOutput(true);
        return connection;
    }


    private void createRequestBody(HttpURLConnection connection, String base64Image) throws Exception {
        JSONObject image = new JSONObject();
        image.put("format", "jpg");
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
}
