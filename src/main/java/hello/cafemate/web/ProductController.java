package hello.cafemate.web;

import hello.cafemate.handler.ex.CustomValidationException;
import hello.cafemate.web.dto.product.ProductDto;
import jakarta.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.HashMap;
import java.util.Map;

@Controller
public class ProductController {

    @GetMapping("/admin/products")
    public String showProductRegistrationForm() {


        return "product/productsRegisterForm";
    }

    @PostMapping("/admin/products")
    public String productRegist(@Valid ProductDto productDto , BindingResult bindingResult){

        if(bindingResult.hasErrors()){
            Map<String,String> errorMap = new HashMap<>();

            for(FieldError error : bindingResult.getFieldErrors()){
                errorMap.put(error.getField(),error.getDefaultMessage());
                System.out.println(error.getDefaultMessage());
            }
            throw new CustomValidationException("유효성검사 실패",errorMap);
        }else{
            //상품 DB에 등록하고


            return "product/productList";
        }




    }

    @GetMapping("/products")
    public String productListShow(){

        return "product/productList";
    }



}



