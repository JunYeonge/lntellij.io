package webtoon.controller.toss;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import webtoon.dto.toss.PaymentReq;
import webtoon.dto.toss.SimplePaymentResponse;
import webtoon.entity.member.Member;
import webtoon.repository.member.MemberRepository;
import webtoon.service.member.MemberService;
import webtoon.service.toss.PaymentService;


@Controller
@Slf4j
public class PaymentTestController {


    @Autowired
    private MemberRepository memberRepository;

    private final PaymentService paymentService;


    @Autowired
    public PaymentTestController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }


    @GetMapping("/payment/toss")
    public String getPaymentPage(Model model, RedirectAttributes redirectAttributes) {

        String successMessage = (String) redirectAttributes.getFlashAttributes().get("successMessage");
        String errorMessage = (String) redirectAttributes.getFlashAttributes().get("errorMessage");

        if (successMessage != null && !successMessage.isEmpty()) {
            model.addAttribute("successMessage", successMessage);
        }
        if (errorMessage != null && !errorMessage.isEmpty()) {
            model.addAttribute("errorMessage", errorMessage);
        }

        return "toss/toss";
    }

    @GetMapping("/payment/success")
    public ResponseEntity<SimplePaymentResponse> getPaymentSuccessTest(
            @RequestParam(name = "paymentKey") String paymentKey,
            @RequestParam(name = "orderId") String orderId,
            @RequestParam(name = "amount") int amount) throws Exception {
        log.info("GET paymentKey, orderId, amount is [{}],[{}],[{}]", paymentKey, orderId, amount);
        PaymentReq paymentReq = PaymentReq.make(paymentKey, orderId, amount);

        // 현재 인증된 사용자의 정보 가져오기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null) {
            String email = authentication.getName(); // 현재 로그인한 사용자의 닉네임
            Member member = memberRepository.findByEmail(email);
            if (member != null) {
                double paymentAmount = paymentReq.getAmount();
                int currentPoint = member.getPoint(); // int로 형변환
                int updatedPoint = currentPoint + (int) paymentAmount; // int로 형변환하여 더하기
                member.setPoint(updatedPoint); // 포인트 업데이트
                memberRepository.save(member); // 업데이트를 데이터베이스에 저장

                // 리다이렉트 응답을 반환합니다.
                // 결제 성공후 메인화면으로 이동
                HttpHeaders headers = new HttpHeaders();
                headers.add("Location", "/payment/success/end?amount=" + amount);
                return new ResponseEntity<>(headers, HttpStatus.FOUND);
            } else {
                String failureMessage = "결제가 실패했습니다. 다시 시도해주세요.";
                // 리다이렉트하고 경고 메시지를 쿼리 문자열로 전달
                return ResponseEntity.status(HttpStatus.FOUND).header("Location", "/payment/error?message=" + failureMessage).build();

            }
        }
        return ResponseEntity.ok().body(paymentService.paymentTest(paymentReq));
    }

    @GetMapping("/payment/success/end")
    public String getPaymentEndPage(Model model, RedirectAttributes redirectAttributes) {
        return "toss/success";
    }

    @GetMapping("/payment/error")
    public String getPaymentErrorPage(Model model, RedirectAttributes redirectAttributes) {
        return "toss/error";
    }
}
