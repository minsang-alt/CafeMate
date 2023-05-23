package hello.cafemate.web;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@RequiredArgsConstructor
@Controller
public class userController {

    @GetMapping("/admin/customers")
    public String showUserList(){
        return "customer/customerList";
    }

}
