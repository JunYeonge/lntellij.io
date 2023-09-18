package webtoon.dto.toss;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class PaymentReq {

    private String paymentKey;
    private int amount;
    private String orderId;


    public static PaymentReq make(final String paymentKey, final String orderId, final int amount) {
        return PaymentReq.builder()
                .paymentKey(paymentKey)
                .orderId(orderId)
                .amount(amount)
                .build();
    }
}
