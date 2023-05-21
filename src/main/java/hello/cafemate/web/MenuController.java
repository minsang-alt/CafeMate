package hello.cafemate.web;

import ch.qos.logback.core.net.SyslogOutputStream;
import hello.cafemate.handler.ex.CustomValidationException;
import hello.cafemate.service.MenuService;
import hello.cafemate.web.dto.menu.MenuDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
    //메뉴 수정 페이지
    @GetMapping("/admin/menus/{menuId}")
    public String menuUpdate(@PathVariable Long menuId, Model model){
        //메뉴id와 관련된 dto를 메뉴수정페이지에 넘김
        MenuDto menuDto = menuService.findOne(menuId);
        model.addAttribute("dto",menuDto);

        return "menu/menuUpdateForm";
    }

    @GetMapping("/menus")
    public String productListShow(){

        return "menu/menuList";
    }



}



