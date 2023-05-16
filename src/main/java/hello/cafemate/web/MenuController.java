package hello.cafemate.web;

import hello.cafemate.handler.ex.CustomValidationException;
import hello.cafemate.web.dto.menu.MenuDto;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.HashMap;
import java.util.Map;

@Controller
public class MenuController {

    @GetMapping("/admin/menus")
    public String showMenuRegistrationForm() {


        return "menu/menuRegisterForm";
    }

    @PostMapping("/admin/menus")
    public String menuRegist(@Valid MenuDto menuDto, BindingResult bindingResult){

        if(bindingResult.hasErrors()){
            Map<String,String> errorMap = new HashMap<>();

            for(FieldError error : bindingResult.getFieldErrors()){
                errorMap.put(error.getField(),error.getDefaultMessage());
                System.out.println(error.getDefaultMessage());
            }
            throw new CustomValidationException("유효성검사 실패",errorMap);
        }else{
            //상품 DB에 등록하고


            return "menu/menuList";
        }




    }

    @GetMapping("/menus")
    public String productListShow(){

        return "menu/menuList";
    }



}



