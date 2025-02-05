package com.backend.DuruDuru.global.converter;

import com.backend.DuruDuru.global.domain.entity.Trade;
import com.backend.DuruDuru.global.domain.entity.TradeImg;

public class TradeImageConverter {

    public static TradeImg toTradeImg(String tradeImgUrl, Trade trade) {
        return TradeImg.builder()
                .tradeImgUrl(tradeImgUrl)
                .trade(trade)
                .build();
    }
}
