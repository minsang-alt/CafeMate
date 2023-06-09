package hello.cafemate.web.api;

import hello.cafemate.domain.OrderMenu;
import hello.cafemate.dto.simple_dto.OrderDto;
import hello.cafemate.service.OrderService;
import hello.cafemate.web.dto.RespDto;
import hello.cafemate.web.dto.menu.MenuDto;
import hello.cafemate.web.dto.order.MenuItem;
import hello.cafemate.web.dto.order.OrderRequestDto;
import hello.cafemate.web.dto.order.OrderResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
public class OrderApiController {
    private final OrderService orderService;

    //주문등록
    @PostMapping("/api/order")
    public ResponseEntity<?> orderRegisteration(@RequestBody OrderRequestDto orderRequestDto){

        //데이터 제대로 받아오는 지 체크
        /*

        System.out.println("sssss");
        List<MenuItem> menuItems = orderRequestDto.getMenuData();

        for(MenuItem item : menuItems){
            System.out.println(item.getMenu()+item.getQty());
        }

        */
        //고객아이디 받아오기
        String customerId = orderRequestDto.getCustomerId();
        //주문데이터 받아오기
        OrderDto orderDto = new OrderDto(orderRequestDto.getQuantity(), orderRequestDto.getTotal_amount(),orderRequestDto.getPoint(),false,null);
        //메뉴와 수량 받아오기
        Map<MenuDto,Integer> orderMenuInfo = new HashMap<MenuDto,Integer>();
        List<MenuItem> menuItems = orderRequestDto.getMenuData();
        for(MenuItem item : menuItems){
            String menuName = item.getMenu();
            int qty = item.getQty();

            MenuDto menuDto = new MenuDto(menuName);

            orderMenuInfo.put(menuDto,qty);
        }

        //주문생성 서비스 실행
        orderService.createOrder(customerId,orderDto,orderMenuInfo);

        return new ResponseEntity<>(new HashMap<>(), HttpStatus.OK);


    }

    //주문완료로 수정
    @DeleteMapping ("/api/order/{id}")
    public ResponseEntity<?>orderDelete(@PathVariable long id){


        orderService.updateCompleteOrder(id);


        return new ResponseEntity<>(new RespDto<>(1,"주문삭제완료",null),HttpStatus.OK);
    }




    //주문리스트 로드하기
    @GetMapping("/api/orders")
    public ResponseEntity<?> orderList(){

       List<OrderResponseDto> orders =  orderService.getUnCompletedOrders();

        return new ResponseEntity<>(new RespDto<>(1,"성공",orders),HttpStatus.OK);
    }
}
