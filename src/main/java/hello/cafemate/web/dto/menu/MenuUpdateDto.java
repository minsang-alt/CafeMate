package hello.cafemate.web.dto.menu;

import hello.cafemate.domain.Category;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class MenuUpdateDto {
private Long id;
@NotBlank
private String product_name;
@NotNull
private Category category;

private Integer price;

private Boolean on_sale;

private Timestamp registrationDate;
    public MenuUpdateDto() {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        registrationDate = timestamp;
    }

    public MenuUpdateDto(Long id, String product_name, Category category, Integer price, Boolean on_sale, Timestamp localDateTime)
    {
        this.id = id;
        this.product_name = product_name;
        this.category = category;
        this.price = price;
        this.on_sale = on_sale;
        this.registrationDate = localDateTime;
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
