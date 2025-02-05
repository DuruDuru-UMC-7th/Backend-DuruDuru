package com.backend.DuruDuru.global.web.dto.Trade;

import com.backend.DuruDuru.global.domain.entity.TradeImg;
import com.backend.DuruDuru.global.domain.enums.Status;
import com.backend.DuruDuru.global.domain.enums.TradeType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public class TradeRequestDTO {

    @Setter
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CreateTradeRequestDTO {
        private Long ingredientCount;
        private String body;
        private TradeType tradeType;
        private List<MultipartFile> tradeImgs;
    }

    @Setter
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class UpdateTradeRequestDTO {
        private Long ingredientCount;
        private String body;
        private Status status;
        private TradeType tradeType;
        // TradeImg[] tradeImg;
    }
}
