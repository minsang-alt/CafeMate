package hello.cafemate.web.dto.order;

import lombok.Data;

import java.util.List;

@Data
public class OrderRequestDto {
    private int quantity;
    private List<MenuItem> menuData;
    private String customerId;
    private int total_amount;
    private int point;




}
