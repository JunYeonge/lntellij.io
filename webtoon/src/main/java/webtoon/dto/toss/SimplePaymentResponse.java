package webtoon.dto.toss;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class SimplePaymentResponse {

    private String mId;
    private String paymentKey;
    private String status;
    private String orderId;
    private String orderName;

    private Card card;

}
