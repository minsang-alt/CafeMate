package hello.cafemate.web.dto.menu;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class MenuDto {

@NotBlank
private String product_name;
@NotBlank
private String category;

private Integer price;

private Boolean on_sale;

}
