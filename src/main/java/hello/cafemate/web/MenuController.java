package hello.cafemate.web;

import ch.qos.logback.core.net.SyslogOutputStream;
import hello.cafemate.handler.ex.CustomValidationException;
import hello.cafemate.service.MenuService;
import hello.cafemate.web.dto.menu.MenuDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.HashMap;
import java.util.Map;
@RequiredArgsConstructor
@Controller
public class MenuController {

    private final MenuService menuService;

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
            //상품 DB에 등록
            System.out.println(menuDto);
            menuService.registerMenu(menuDto);

            return "menu/menuList";
        }




    }

    @GetMapping("/menus")
    public String productListShow(){

        return "menu/menuList";
    }



}



