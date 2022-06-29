package com.daniel.domain.DTO.order.response.kakaoPay;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@Builder
public class CardInfo {

    private String purchaseCorp, purchaseCorpCode;
    private String issuerCorp, issuerCorpCode;
    private String bin, cardType, installMonth, approvedId, cardMid;
    private String interestFreeInstall, cardItemCode;
}