package webtoon.dto.toss;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class Card {

    private String number;
    private String cardType;
    private String ownerType;
    private String acquireStatus;
    private int amount;
}
