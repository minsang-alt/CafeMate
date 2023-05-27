package hello.cafemate.dao;


import lombok.Getter;

@Getter
public class OrderCustomerDao {
    private Long customerId;
    private String alias;

    public OrderCustomerDao(Long customerId, String alias) {
        this.customerId = customerId;
        this.alias = alias;
    }
}
