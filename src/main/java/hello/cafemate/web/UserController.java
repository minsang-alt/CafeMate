package hello.cafemate.web;

import hello.cafemate.service.CustomerService;
import hello.cafemate.web.dto.user.CustomerDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RequiredArgsConstructor
@Controller
public class UserController {

    private final CustomerService customerService;

    @GetMapping("/admin/customers")
    public String showUserList(){
        return "customer/customerList";
    }

    //customer 수정 페이지
    @GetMapping("/admin/customers/{customerId}")
    public String customerUpdate(@PathVariable Long customerId, Model model){
       CustomerDto customerDto =  customerService.findOne(customerId);
       model.addAttribute("dto",customerDto);

       return "customer/customerUpdateForm";
    }

}
