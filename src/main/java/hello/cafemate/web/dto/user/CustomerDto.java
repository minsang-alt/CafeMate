package hello.cafemate.web.dto.user;

import lombok.Getter;

@Getter
public class CustomerDto {
    private Long id;
    private String customerId;
    private String eMail;
    private String password;
    private String name;
    private String phoneNumber;
    private String alias;
    private int savedPoint;
    private Boolean status;
    public CustomerDto(String customerId, String eMail, String password, String name,
                       String phoneNumber, String alias, int savedPoint,Boolean status) {
        this.customerId = customerId;
        this.eMail = eMail;
        this.password = password;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.alias = alias;
        this.savedPoint = savedPoint;
        this.status = status;
    }
    public CustomerDto(Long id, String customerId, String eMail, String password, String name,
                       String phoneNumber, String alias, int savedPoint,Boolean status){
        this.id = id;
        this.customerId = customerId;
        this.eMail = eMail;
        this.password = password;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.alias = alias;
        this.savedPoint = savedPoint;
        this.status = status;
    }
}
