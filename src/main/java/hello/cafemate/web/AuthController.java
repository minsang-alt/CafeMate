package hello.cafemate.web;

import hello.cafemate.web.dto.user.CustomerDto;
import hello.cafemate.web.dto.user.MemberDto;
import hello.cafemate.handler.ex.CustomValidationException;
import hello.cafemate.service.CustomerService;
import hello.cafemate.service.MemberService;
import hello.cafemate.web.dto.auth.SignupDto;
import hello.cafemate.web.dto.auth.SignupMemberDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.HashMap;
import java.util.Map;
@RequiredArgsConstructor//final 필드를 DI할때 사용 즉 final인 변수들을 생성자를 이용해 주입
@Controller
public class AuthController {

    private static final Logger log = LoggerFactory.getLogger(AuthController.class);
    private final CustomerService customerService;
    private final MemberService memberService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

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
    public String signup(@Valid SignupDto signupDto, BindingResult bindingResult){

        //log.info(signupDto.toString());
        //user객체에 userDto의 데이터들을 주입해야하고 서비스에 이 user오브젝트를 전달한다음 서비스는 각종일을 처리한다.
        if(bindingResult.hasErrors()){
            Map<String,String> errorMap = new HashMap<>();
            for(FieldError error : bindingResult.getFieldErrors()){
                errorMap.put(error.getField(),error.getDefaultMessage());
                System.out.println(error.getDefaultMessage());
            }
            throw new CustomValidationException("유효성검사 실패",errorMap);
        }else{
            //원래 서비스쪽에서 비밀번호 암호화 만드는게 베스트
            signupDto.setPassword(bCryptPasswordEncoder.encode(signupDto.getPassword()));
            CustomerDto customerDto = signupDto.toEntity();

            //여기서 가입하는 사람은 무조건 customer이고 member는 db에서 따로 등록하거나 추후에 다른 방법 만들 예정
            customerService.join(customerDto);

            return "auth/signin";
        }


    }

    @GetMapping("/auth/admin/signup")
    public String signupMemberForm(){
        return "/auth/signupMember";
    }
    @PostMapping("/auth/admin/signup")
    public String signupMember(@Valid SignupMemberDto signupMemberDto, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            Map<String,String> errorMap = new HashMap<>();
            for(FieldError error : bindingResult.getFieldErrors()){
                errorMap.put(error.getField(),error.getDefaultMessage());
                System.out.println(error.getDefaultMessage());
            }
            throw new CustomValidationException("유효성검사 실패",errorMap);
        }else{
            //관리자 일단 가입 후 다른 회원이 가입하지 못하고 승인받게 다른 방법 추가 필요
            //System.out.println(signupMemberDto.isIsAdmin());
            signupMemberDto.setPassword(bCryptPasswordEncoder.encode(signupMemberDto.getPassword()));
            MemberDto memberDto = signupMemberDto.toEntity();

            memberService.join(memberDto);

            return "auth/signin";
        }
    }
}
