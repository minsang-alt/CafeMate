package hello.cafemate.dto.simple_dto;

import lombok.Getter;

@Getter
public class CustomerDto {
    private String customerId;
    private String eMail;
    private String password;
    private String name;
    private String phoneNumber;
    private String alias;
    private int savedPoint;

    public CustomerDto(String customerId, String eMail, String password, String name,
                       String phoneNumber, String alias, int savedPoint) {
        this.customerId = customerId;
        this.eMail = eMail;
        this.password = password;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.alias = alias;
        this.savedPoint = savedPoint;
    }
}
