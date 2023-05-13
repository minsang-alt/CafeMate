package hello.cafemate.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@EqualsAndHashCode
public class Menu {
    private Long id;
    private String name;
    private Category category;
    private int price;
    private boolean onSale;
    private LocalDateTime registrationDate;

    public Menu() {
    }

    public Menu(Long id, String name, Category category, int price, boolean onSale, LocalDateTime registrationDate) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.price = price;
        this.onSale = onSale;
        this.registrationDate = registrationDate;
    }

    public Menu(String name, Category category, int price, boolean onSale, LocalDateTime registrationDate) {
        this.name = name;
        this.category = category;
        this.price = price;
        this.onSale = onSale;
        this.registrationDate = registrationDate;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
