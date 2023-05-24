package hello.cafemate.web.api;

import hello.cafemate.service.CustomerService;
import hello.cafemate.service.MemberService;
import hello.cafemate.web.dto.RespDto;
import hello.cafemate.web.dto.user.CustomerDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
 }
