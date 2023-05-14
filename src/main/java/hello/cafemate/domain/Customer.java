package hello.cafemate.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@EqualsAndHashCode
@ToString
public class Customer {
    private long id;
    private String customerId;
    private String eMail;
    private String password;
    private String name;
    private String phoneNumber;
    private String alias;
    private int savedPoint;

    public Customer(long id, String customerId, String eMail, String password,
                    String name, String phoneNumber, String alias, int savedPoint) {
        this.id = id;
        this.customerId = customerId;
        this.eMail = eMail;
        this.password = password;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.alias = alias;
        this.savedPoint = savedPoint;
    }

    public Customer(String customerId, String eMail, String password, String name, String phoneNumber, String alias, int savedPoint) {
        this.customerId = customerId;
        this.eMail = eMail;
        this.password = password;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.alias = alias;
        this.savedPoint = savedPoint;
    }

    public Customer(String customerId, String eMail, String password,
                    String name, String phoneNumber, int savedPoint) {
        this.customerId = customerId;
        this.eMail = eMail;
        this.password = password;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.alias = null;
        this.savedPoint = savedPoint;
    }

    public void setId(long id) {
        this.id = id;
    }
}
