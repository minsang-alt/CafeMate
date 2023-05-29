package hello.cafemate.dto.update_dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerUpdateDto {
    private String customerId;
    private String eMail;
    private String password;
    private String name;
    private String phoneNumber;
    private String alias;
    private int savedPoint;
    private Boolean status;


    public CustomerUpdateDto(){
        //회원수정버튼을 눌렀을때 항상 status는 active상태여야하므로 여기서 true로 설정 추후에 문제되면
        //이것을 없애고 customerUpdateForm에서 status=true를 넘기는 방식으로 해야함
        this.status=true;
    };
}
