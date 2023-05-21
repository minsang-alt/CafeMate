package hello.cafemate.web.api;

import hello.cafemate.web.dto.menu.MenuUpdateDto;
import hello.cafemate.handler.ex.CustomValidationException;
import hello.cafemate.service.MenuService;
import hello.cafemate.web.dto.RespDto;
import hello.cafemate.web.dto.menu.MenuDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
public class MenuApiController {

    private final MenuService menuService;
    @GetMapping("/api/menu")
    public ResponseEntity<?> menuList(){

        List<MenuDto> menus = menuService.findAll();



      return new ResponseEntity<>(new RespDto<>(1,"성공",menus), HttpStatus.OK);

    }


    @DeleteMapping("/api/menu/{id}")
    public ResponseEntity<?>menuDelete(@PathVariable long id){
        //menu의 id전달하며 제거
        menuService.deleteById(id);
        return new ResponseEntity<>(new RespDto<>(1,"댓글삭제성공",null),HttpStatus.OK);
    }

    @PutMapping("/api/menu/{id}")
    public ResponseEntity<?>menuUpdate(@PathVariable long id, @Valid MenuUpdateDto menuUpdateDto, BindingResult bindingResult){
        if(bindingResult.hasErrors()) {
            Map<String,String> errorMap = new HashMap<>();

            for(FieldError error : bindingResult.getFieldErrors()) {
                errorMap.put(error.getField(), error.getDefaultMessage());
                //	System.out.println(error.getDefaultMessage());

            }
            throw new CustomValidationException("유효성검사 실패",errorMap);

        }else{
            menuService.updateById(id,menuUpdateDto);

            return new ResponseEntity<>(new RespDto<>(1,"회원수정완료",null),HttpStatus.OK);
        }
    }


}
