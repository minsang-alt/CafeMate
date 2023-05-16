package hello.cafemate.web.api;

import hello.cafemate.web.dto.RespDto;
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

    @GetMapping("/api/menu")
    public ResponseEntity<?> menuList(){

        //menus = menuService.메뉴리스트()
       // System.out.println("여긴 오냐?");
        //List<Menu> menus = new ArrayList<>();//리스트초기화
       // LocalDateTime now = LocalDateTime.now();
       // menus.add(new Menu(1L,"아이스아메리카노", Category.Coffee,3000,true,now));
       // menus.add(new Menu(2L,"카페라떼", Category.Latte,2500,true,now));


      //  return new ResponseEntity<>(new RespDto<>(1,"성공",menus), HttpStatus.OK);
        return null;
    }


    @DeleteMapping("/api/menu/{id}")
    public ResponseEntity<?>menuDelete(@PathVariable long id)
    {
        //menu의 id전달하며 제거
        return new ResponseEntity<>(new RespDto<>(1,"댓글삭제성공",null),HttpStatus.OK);
    }

}
