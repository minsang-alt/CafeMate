package hello.cafemate.handler;

import hello.cafemate.handler.ex.CustomValidationException;
import hello.cafemate.util.Script;
import hello.cafemate.web.dto.RespDto;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController//데이터로 응답
@ControllerAdvice //모든 예외처리 이 클래스가 낚아챔
public class ControllerExceptionHandler {

    @ExceptionHandler(CustomValidationException.class) //RuntimeException이 발동하는 모든 것을 이 함수가 낚아챔
    public String validationException(CustomValidationException e){
        return Script.back(e.getErrorMap().toString());
    }
}
