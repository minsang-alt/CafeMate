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

    public CustomerUpdateDto(){};
}
