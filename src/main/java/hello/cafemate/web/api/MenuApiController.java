package hello.cafemate.web.api;

import hello.cafemate.web.dto.RespDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class MenuApiController {

    @GetMapping("/api/menu")
    public ResponseEntity<?> imageStory(){

        //menus = menuService.메뉴리스트()
        return new ResponseEntity<>(new RespDto<>(1,"성공",menus), HttpStatus.OK);
    }

}
