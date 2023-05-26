package hello.cafemate.web.dto.menu;

import hello.cafemate.domain.Category;
import hello.cafemate.domain.Menu;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.sql.Timestamp;


@Data
public class MenuDto {
private Long id;
@NotBlank
private String product_name;
@NotNull
private Category category;

private Integer price;

private Boolean on_sale;

private Timestamp registrationDate;
    public MenuDto() {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        registrationDate = timestamp;
    }

    public MenuDto(Long id,String product_name, Category category,Integer price,Boolean on_sale,Timestamp localDateTime)
    {
        this.id = id;
        this.product_name = product_name;
        this.category = category;
        this.price = price;
        this.on_sale = on_sale;
        this.registrationDate = localDateTime;
    }


    public MenuDto(Menu menu) {
        this.id = menu.getId();
        this.product_name = menu.getName();
        this.category = menu.getCategory();
        this.price = menu.getPrice();
        this.on_sale = menu.isOnSale();
        this.registrationDate = menu.getRegistrationDate();
    }

    public MenuDto(String product_name){
        this.product_name = product_name;
    }

public void setCategory(String category)
{
    try{

        this.category = Category.valueOf(category.toUpperCase());

    }catch (IllegalArgumentException e){
        this.category = null;
    }
}

public Category getCategory(){
    return category;
}
}
