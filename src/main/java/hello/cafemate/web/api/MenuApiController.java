package hello.cafemate.web.api;

import hello.cafemate.service.MenuService;
import hello.cafemate.web.dto.RespDto;
import hello.cafemate.web.dto.menu.MenuDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
    public ResponseEntity<?>menuDelete(@PathVariable long id)
    {
        //menu의 id전달하며 제거
        return new ResponseEntity<>(new RespDto<>(1,"댓글삭제성공",null),HttpStatus.OK);
    }

}
