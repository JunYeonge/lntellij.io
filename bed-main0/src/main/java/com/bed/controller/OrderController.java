package com.bed.controller;


import com.bed.config.CustomUserDetails;
import com.bed.constant.Role;
import com.bed.dto.OrderDto;
import com.bed.dto.OrderHistDto;
import com.bed.dto.PayDetailResponseDto;
import com.bed.entity.*;
import com.bed.exception.CustomBusinessException;
import com.bed.repository.DeliveryRepository;
import com.bed.repository.ItemImgRepository;
import com.bed.repository.ItemRepository;
import com.bed.repository.MemberRepository;
import com.bed.service.OrderService;
import com.bed.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;
    private final ItemImgRepository itemImgRepository;
    private final PaymentService payService;
    @Autowired
    private DeliveryRepository deliveryRepository;

    @PostMapping(value = "/order")
    public @ResponseBody ResponseEntity order
            (@RequestBody @Valid OrderDto orderDto
            , BindingResult bindingResult, Principal principal){


        //주문 정보를 받는 orderDto 객체에 데이터 바인딩시 에러가 있는지 검사
        if(bindingResult.hasErrors()){
            StringBuilder sb = new StringBuilder();
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();

            for (FieldError fieldError : fieldErrors) {
                sb.append(fieldError.getDefaultMessage());
            }
            //에러정보를  ResponseEntity에 담아서 반환
            return new ResponseEntity<String>(sb.toString(),
                    HttpStatus.BAD_REQUEST);
        }

        String email = principal.getName();
        // 현재 로그인 유저의 정보를 얻기위해 @Controller 어노테이션이 선언된
        // 클래서에서 메소드 인자로
        //  principal 객체로 넘겨 줄 경우 해당 객체에 직접 접근할 수 있습니다.
        // principal 객체에서 현재 로그인한 회언의 이메일 정보를 조회합니다.
        Long orderId;

        try {
            orderId = orderService.order(orderDto, email);
            //화면으로 부터 넘어오는 주문 정보와 회원의 이메일 정보를
            // 이용하여 주문 로직을 호출
        } catch(Exception e){
            return new ResponseEntity<String>(e.getMessage(),
                    HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<Long>(orderId, HttpStatus.OK);
        //결과값으로 생성된 주문 번호와 요청이 성공했다는 Http 응답 상태 코드를 반환
    }
    @GetMapping(value = {"/{memberId}/orders","/{memberId}/orders/{page}"})//주문이력 페이지
    public String orderList(@PathVariable("page") Optional<Integer>page,
                            Principal principal, Model model){
        Pageable pageable = PageRequest.of
                (page.isPresent() ? page.get() : 0, 10);
        //한번에 가지고 올 수 있는 주문에 갯수는 4개로 설정
        Page<OrderHistDto> ordersHistDtoList = orderService.
                getOrderList(principal.getName(), pageable);
        //현재 로그인한 회원은 이메일과 페이징 객체를 파라미터로 전달하여 화면에 전달한
        // 주문 목록 데이터를 리턴값으로 받습니다.

        //주문 목록데이터
        model.addAttribute("orders", ordersHistDtoList);

        //현재페이지 번호
        model.addAttribute("page", pageable.getPageNumber());

        //1페이지내 최대 목록
        model.addAttribute("maxPage", 5);

        // header 이미지
        model.addAttribute("headerImg","/images/header.jpg");

        return "order/orderList";
    }
    @GetMapping(value = {"/admin/orders","/admin/orders/{page}"})//관리자주문내역 페이지
    public String orderHist(@PathVariable("page") Optional<Integer>page,
                            Principal principal, Model model){
        Pageable pageable = PageRequest.of
                (page.isPresent() ? page.get() : 0, 4);
        //한번에 가지고 올 수 있는 주문에 갯수는 4개로 설정
        Page<OrderHistDto> ordersHistDtoList = orderService.
                getAllOrderList( pageable);
        //현재 로그인한 회원은 이메일과 페이징 객체를 파라미터로 전달하여 화면에 전달한
        // 주문 목록 데이터를 리턴값으로 받습니다.

        //주문 목록데이터
        model.addAttribute("orders", ordersHistDtoList);

        //현재페이지 번호
        model.addAttribute("page", pageable.getPageNumber());

        //1페이지내 최대 목록
        model.addAttribute("maxPage", 5);

        // header 이미지
        model.addAttribute("headerImg","/images/header.jpg");

        return "order/orderHist";
    }
    @PostMapping("/admin/orders/{orderId}/cancel")
    public @ResponseBody ResponseEntity cancelOrder
            (@PathVariable("orderId") Long orderId , Principal principal){

        // principal에서 권한을 얻어옵니다.
        UserDetails userDetails = (UserDetails) ((Authentication) principal).getPrincipal();
        String role = userDetails.getAuthorities().iterator().next().getAuthority();

//        if(!orderService.validateOrder(orderId, principal.getName()) && role == Role.USER){
        if(!orderService.validateOrder(orderId, principal.getName()) && role.equals("ROLE_USER")){
            return new ResponseEntity<String>("주문 취소 권한이 없습니다.",
                    HttpStatus.FORBIDDEN);
        }

        orderService.cancelOrder(orderId);
        return new ResponseEntity<Long>(orderId, HttpStatus.OK);
    }
    @PostMapping("/order/{orderId}/cancel")
    public @ResponseBody ResponseEntity userCancelOrder
            (@PathVariable("orderId") Long orderId , Principal principal){

        if(!orderService.validateOrder(orderId, principal.getName())){
            return new ResponseEntity<String>("주문 취소 권한이 없습니다.",
                    HttpStatus.FORBIDDEN);
        }

        orderService.cancelOrder(orderId);
        return new ResponseEntity<Long>(orderId, HttpStatus.OK);
    }
//    @GetMapping("/detailPayment/{itemId}/{count}")
//    public String detailPay(@PathVariable Long itemId, @PathVariable int count, Model model){
//        Item item = itemRepository.findById(itemId).orElseThrow(()->{
//            return new CustomBusinessException("상품을 찾을 수 없습니다.");
//        });
//        ItemImg itemImg = itemImgRepository.findById(itemId).orElseThrow(()->{
//            return new CustomBusinessException("상품 이미지 경로를 찾을 수 없습니다.");
//        });
//        PayDetailResponseDto payDetailResponseDto = payService.makeDetailResponseDto(item,count,itemImg);
//        model.addAttribute("payDetailDto",payDetailResponseDto);
//        return "pay/detailPayment";
//    }

    @RequestMapping(value="/delivery", method = {RequestMethod.GET, RequestMethod.POST})
    public String saveDeliveryInfo(@RequestParam("name") String name,
                                   @RequestParam("phone") String phone,
                                   @RequestParam("address") String address,
                                   HttpSession session,
                                   @ModelAttribute("order") Order order) {
        // Delivery 객체 생성 및 값 설정
        Delivery delivery = new Delivery();
        delivery.setDeliveryName(name);
        delivery.setDeliveryPhone(phone);
        delivery.setDeliveryAddress(address);
        // Delivery와 Order 간의 연관 관계 설정
        order.setDelivery(delivery);
        delivery.setOrder(order);

        // Delivery 정보 저장
        deliveryRepository.save(delivery);

        // 저장 후, 결제 페이지 또는 다음 단계로 이동하도록 리다이렉트
        return "redirect:/pay";
    }

    @GetMapping(value = {"/pay", "/pay/{page}"})
    public String detailPayment(@PathVariable("page") Optional<Integer> page, Principal principal, Model model) {
        String email = principal.getName(); // 현재 로그인한 사용자의 이메일
        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 1, Sort.by(Sort.Direction.DESC, "orderDate")); // 페이지네이션 정보 설정 (첫 페이지, 1개 아이템, 최신 순으로 정렬)
        Page<OrderHistDto> orderList = orderService.getOrderList(email, pageable); // 최신 구매 목록 1개 조회
        int maxPage = 5; // 1페이지 내 최대 목록 수
        String headerImg = "/images/white.png"; // header 이미지 경로

        model.addAttribute("maxPage", maxPage); // 최대 페이지 수 추가
        model.addAttribute("headerImg", headerImg); // header 이미지 추가
        model.addAttribute("orders", orderList); // 주문 목록 데이터 추가
        model.addAttribute("page", pageable.getPageNumber()); // 현재 페이지 번호 추가

        // 현재 사용자 정보를 가져와서 Model에 추가
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        String loggedInUsername = userDetails.getName();
        String loggedInPhoneNumber = userDetails.getPhoneNumber();
        String loggedInUserEmail = userDetails.getUsername();
        model.addAttribute("loggedInUsername", loggedInUsername);
        model.addAttribute("loggedInPhoneNumber", loggedInPhoneNumber);
        model.addAttribute("loggedInUserEmail", loggedInUserEmail);

        return "pay/payment";
    }

    @GetMapping(value = {"/cartPay", "/cartPay/{page}"})
    public String cartPayment(@PathVariable("page") Optional<Integer> page, Principal principal, Model model) {
        String email = principal.getName(); // 현재 로그인한 사용자의 이메일
        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 1, Sort.by(Sort.Direction.DESC, "orderDate")); // 페이지네이션 정보 설정 (첫 페이지, 1개 아이템, 최신 순으로 정렬)

        Page<OrderHistDto> orderList = orderService.getOrderList(email, pageable); // 최신 구매 목록 1개 조회

        Page<OrderHistDto> orderHistDto = orderService.getOrderCartList(email, pageable);

        int maxPage = 5; // 1페이지 내 최대 목록 수
        String headerImg = "/images/white.png"; // header 이미지 경로

        model.addAttribute("orderHistDto", orderHistDto);

        model.addAttribute("maxPage", maxPage); // 최대 페이지 수 추가
        model.addAttribute("headerImg", headerImg); // header 이미지 추가
        model.addAttribute("orders", orderList); // 주문 목록 데이터 추가
        model.addAttribute("page", pageable.getPageNumber()); // 현재 페이지 번호 추가

        // 현재 사용자 정보를 가져와서 Model에 추가
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        String loggedInUsername = userDetails.getName();
        String loggedInPhoneNumber = userDetails.getPhoneNumber();
        String loggedInUserEmail = userDetails.getUsername();
        model.addAttribute("loggedInUsername", loggedInUsername);
        model.addAttribute("loggedInPhoneNumber", loggedInPhoneNumber);
        model.addAttribute("loggedInUserEmail", loggedInUserEmail);

        return "pay/cartPayment";
    }

//    @GetMapping("/cartPayment/{userId}")
//    public String CartPayment(@PathVariable int userId, Model model){
//        Member member = memberRepository.findById(userId).orElseThrow(()->{
//            return new CustomBusinessException("사용자를 찾을 수 없습니다.");
//        });
//        List<Cart> cartList = cartService.loadCart(userId);
//        Map<String,Object> responseMap = payService.makeCartResponseDto(cartList);
//        model.addAttribute("responseMap",responseMap);
//        return "pay/cartPayment";
//    }
}
