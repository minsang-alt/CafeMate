package hello.cafemate.web;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@RequiredArgsConstructor
@Controller
public class OrderController {

    //주문 등록 페이지 응답
    @GetMapping("/admin/order")
    public String showOrderRegistrationForm(){
        return "order/orderRegisterForm";
    }

    //전체 주문 목록
    @GetMapping("/admin/orders")
    public String showOrdersShow(){
        return "order/orderList";
    }
}
