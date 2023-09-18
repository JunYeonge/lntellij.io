package webtoon.service.toss;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import webtoon.dto.toss.PaymentReq;
import webtoon.dto.toss.SimplePaymentResponse;
import webtoon.entity.member.Member;
import webtoon.repository.member.MemberRepository;

import java.util.Base64;


@Service
@Transactional
@Slf4j
@Lazy
public class PaymentService {

    @Autowired
    private MemberRepository memberRepository;

    private final String secret;
    private final WebClient client;


    public PaymentService(MemberRepository memberRepository, @Value("${payment.key.secret}") String secret) {
        this.memberRepository = memberRepository;
        this.secret = secret;
        this.client = WebClient.builder()
                .baseUrl("https://api.tosspayments.com")
                .build();
    }

    private static final String AUTHORIZATION = "Authorization";


    public SimplePaymentResponse paymentTest(final PaymentReq paymentReq) throws Exception {
        ObjectMapper om = new ObjectMapper();
        String reqJSON = om.writeValueAsString(paymentReq);


        Mono<SimplePaymentResponse> mono = client.post()
                .uri("/v1/payments/confirm")
                .contentType(MediaType.APPLICATION_JSON)
                .header(AUTHORIZATION, getBasicKey())
                .body(BodyInserters.fromValue(reqJSON))
                .retrieve()
                .bodyToMono(SimplePaymentResponse.class);

        SimplePaymentResponse response = mono.block();

        return response;
    }

    public String getBasicKey() {
        String basic = "Basic ";
        String encodeKey = Base64.getEncoder().encodeToString(secret.getBytes());
        return basic + encodeKey;
    }
}