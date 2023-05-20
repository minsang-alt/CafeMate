package hello.cafemate.dto.simple_dto;

import hello.cafemate.domain.Category;
import lombok.Getter;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Getter
public class MenuDto {
    private String name;
    private Category category;
    private int price;
    private boolean onSale;
    private Timestamp registrationDate;

    public MenuDto(String name, Category category,
                   int price, boolean onSale, Timestamp registrationDate) {
        this.name = name;
        this.category = category;
        this.price = price;
        this.onSale = onSale;
        this.registrationDate = registrationDate;
    }
}
