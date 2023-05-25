package hello.cafemate.service;

import hello.cafemate.domain.Customer;
import hello.cafemate.domain.Order;
import hello.cafemate.domain.OrderMenu;
import hello.cafemate.web.dto.user.CustomerDto;
import hello.cafemate.dto.simple_dto.OrderDto;
import hello.cafemate.repository.CustomerRepository;
import hello.cafemate.repository.MenuRepository;
import hello.cafemate.repository.OrderRepository;
import hello.cafemate.web.dto.menu.MenuDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final MenuRepository menuRepository;

    @Transactional
    public OrderDto createOrder(String customerId, OrderDto orderDto, Map<MenuDto, Integer> orderMenuInfo){
        Optional<Customer> foundCustomer =
                customerRepository.findByCustomerId(customerId);

        if(foundCustomer.isEmpty()){
            throw new IllegalStateException("존재하지 않는 고객입니다.");
        }

        Long customerPK = foundCustomer.get().getId();
        orderDto.setCustomerId(customerPK);
        Order order = orderDtoToEntity(orderDto);
        Long orderId = orderRepository.save(order).getId();

        List<MenuDto> menuList = orderMenuInfo.keySet().stream().collect(Collectors.toList());
        List<Long> menuIds = getMenuIds(menuList);

        List<Integer> amountList = orderMenuInfo.values().stream().collect(Collectors.toList());
        List<OrderMenu> orderMenuList = getOrderMenuList(orderId, menuIds, amountList);
        orderRepository.saveMenus(order, orderMenuList)
                .addMenu(orderMenuList);

        return orderEntityToDto(order);
    }

    public List<OrderDto> getCustomerOrders(CustomerDto customerDto){
        Optional<Customer> findCustomer =
                customerRepository.findByCustomerId(customerDto.getCustomerId());

        if(findCustomer.isEmpty()){
            throw new IllegalStateException("주문을 조회하려는 고객이 존재하지 않습니다");
        }

        Long customerId = findCustomer.get().getId();
        List<Order> orders = orderRepository.findOrdersByCustomerId(customerId);

        return orders.stream()
                .map(order ->{
                    OrderDto orderDto = orderEntityToDto(order);
                    orderDto.setOrderMenuList(order.getOrderMenuList());
                    return orderDto;
                })
                .collect(Collectors.toList());
    }

    private List<OrderMenu> getOrderMenuList(Long orderId, List<Long> menuIds, List<Integer> amountList){
        List<OrderMenu> orderMenus = new ArrayList<>();

        for(int i=0; i<menuIds.size(); i++){
            orderMenus.add(new OrderMenu(orderId, menuIds.get(i), amountList.get(i)));
        }

        return orderMenus;
    }

    private List<Long> getMenuIds(List<MenuDto> menuList) {
        return menuList.stream()
                .map(menuDto->menuRepository.findByName(menuDto.getProduct_name())
                        .orElseThrow(()->new IllegalArgumentException("추가하려는 메뉴가 존재하지 않습니다."))
                        .getId())
                .collect(Collectors.toList());
    }

    private OrderDto orderEntityToDto(Order order){
        return new OrderDto(
                order.getQuantity(),
                order.getPayments(),
                order.getUsePointAmount(),
                order.isComplete(),
                order.getOrderDate()
        );
    }

    private Order orderDtoToEntity(OrderDto orderDto){
        return new Order(
                orderDto.getCustomerId(),
                orderDto.getPayments(),
                orderDto.getQuantity(),
                orderDto.getUsePointAmount(),
                orderDto.isComplete(),
                orderDto.getOrderDate()
        );
    }

    private OrderMenu createOrderMenu(Long orderId, Long menuId, int amount){
        return new OrderMenu(orderId, menuId, amount);
    }

}
