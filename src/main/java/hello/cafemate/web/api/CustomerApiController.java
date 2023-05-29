package hello.cafemate.web.api;

import hello.cafemate.dto.update_dto.CustomerUpdateDto;
import hello.cafemate.handler.ex.CustomValidationException;
import hello.cafemate.service.CustomerService;
import hello.cafemate.service.MemberService;
import hello.cafemate.web.dto.RespDto;
import hello.cafemate.web.dto.user.CustomerDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
public class CustomerApiController {
    private final CustomerService customerService;

    //고객리스트 데이터들 불러오기
    @GetMapping("/api/customers")
    public ResponseEntity<?>customerList(){
        List<CustomerDto> customers = customerService.findAll();
        return new ResponseEntity<>(new RespDto<>(1,"성공",customers), HttpStatus.OK);
    }


    /*  @GetMapping("/api/customers/{customerId}")
    public ResponseEntity<?> customerDetail(){

    }*/

    //customer삭제
    @PutMapping("/api/customers/{id}/status")
    public ResponseEntity<?>customerDelete(@PathVariable long id){
        customerService.updateStatusCustomer(id);
        return new ResponseEntity<>(new RespDto<>(1,"회원삭제성공",null),HttpStatus.OK);


    }

    //회원 수정
    @PutMapping("/api/customers/{id}")
    public ResponseEntity<?>customerUpdate(@PathVariable long id, @Valid CustomerUpdateDto customerUpdateDto, BindingResult bindingResult){
        Map<String,String> errorMap = new HashMap<>();
        if(bindingResult.hasErrors()) {


            for(FieldError error : bindingResult.getFieldErrors()) {
                errorMap.put(error.getField(), error.getDefaultMessage());
                //	System.out.println(error.getDefaultMessage());

            }
            throw new CustomValidationException("유효성검사 실패",errorMap);

        }else{

            customerService.updateOne(id,customerUpdateDto);

            return new ResponseEntity<>(new RespDto<>(1,"회원수정완료",null),HttpStatus.OK);
        }
    }

 }
