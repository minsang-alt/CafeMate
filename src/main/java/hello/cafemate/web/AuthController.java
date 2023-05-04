package hello.cafemate.web;

import hello.cafemate.web.dto.auth.SignupDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthController {

    private static final Logger log = LoggerFactory.getLogger(AuthController.class);

    @GetMapping("/auth/signin")
    public String signinForm(){
        return "auth/signin";
    }

    @GetMapping("/auth/signup")
    public String signupForm(){
        return "auth/signup";
    }

    //회원가입버튼누르면->/auth/signup->/auth/signin 리다이렉션
    @PostMapping("/auth/signup")
    public String signup(SignupDto signupDto){

        //log.info(signupDto.toString());
        //user객체에 userDto의 데이터들을 주입해야하고 서비스에 이 user오브젝트를 전달한다음 서비스는 각종일을 처리한다.
        

        return "auth/signin";
    }
}
