package com.backend.DuruDuru.global.service.OCRService;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ClovaOCRImageService {

    @Value("${clova.image.api-url}")
    private String apiUrl;

    @Value("${clova.image.secret-key}")
    private String secretKey;







}
