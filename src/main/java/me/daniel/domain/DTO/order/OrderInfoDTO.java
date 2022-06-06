package me.daniel.domain.DTO.order;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class OrderInfoDTO {

    String cid, cidSecret, tid;
    String status, partnerOrderId, partnerUserId, paymentMethodType;
    AmountDTO amountDTO;
    CanceledAmountDTO canceledAmountDTO;
    CancelAvailableAmountDTO cancelAvailableAmountDTO;
    String itemName, itemCode;
    Integer quantity;
    Date createdAt, approvedAt, canceledAt;
    SelectedCardInfoDTO selectedCardInfoDTO;
    PaymentActionDetailsDTO[] paymentActionDetails;
}