package hello.cafemate.service;

import hello.cafemate.dao.OrderCustomerDao;
import hello.cafemate.dao.OrderResponseDao;
import hello.cafemate.domain.Customer;
import hello.cafemate.domain.Order;
import hello.cafemate.domain.OrderMenu;
import hello.cafemate.dto.update_dto.MenuUpdateDto;
import hello.cafemate.dto.update_dto.OrderUpdateDto;
import hello.cafemate.repository.OrderMenuRepository;
import hello.cafemate.web.dto.order.OrderResponseDto;
import hello.cafemate.web.dto.user.CustomerDto;
import hello.cafemate.dto.simple_dto.OrderDto;
import hello.cafemate.repository.CustomerRepository;
import hello.cafemate.repository.MenuRepository;
import hello.cafemate.repository.OrderRepository;
import hello.cafemate.web.dto.menu.MenuDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {

    private final OrderMenuRepository orderMenuRepository;
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

    public List<OrderResponseDto> getUnCompletedOrders(){
        //모든 완료되지 않은 주문 데이터 조회
        List<Order> unCompleteOrders = orderRepository.findAllUncompletedOrders();

        //각 주문별 OrderCustomerDto(고객ID, 별명) 조회
        List<OrderCustomerDao> customerDaoList = new ArrayList<>();

        for (Order unCompleteOrder : unCompleteOrders) {
            OrderCustomerDao customerDao = customerRepository.findOrderCustomerDaoById(unCompleteOrder.getCustomerId());
            customerDaoList.add(customerDao);
        }

        //각 주문 포함 메뉴 이름 목록 조회
        Map<Long, List<String>> orderMenuNameMap = new HashMap<>();

        for (Order unCompleteOrder : unCompleteOrders) {
            List<Long> menuIds = orderMenuRepository.findAllMenuIds(unCompleteOrder.getId());
            List<String> menuNameList = new ArrayList<>();

            for (Long menuId : menuIds) {
                String menuName = menuRepository.getMenuName(menuId);
                menuNameList.add(menuName);
            }

            orderMenuNameMap.put(unCompleteOrder.getId(), menuNameList);
        }

        //각 주문별 OrderResponseDao 조회
        List<OrderResponseDao> orderResponseDaoList = new ArrayList<>();

        for (Order unCompleteOrder : unCompleteOrders) {
            OrderResponseDao orderResponseDao = orderRepository.findOrderResponseDaoById(unCompleteOrder.getId());
            orderResponseDaoList.add(orderResponseDao);
        }

        //orderResponseDto 목록 생성
        List<OrderResponseDto> orderResponseDtoList = new ArrayList<>();
        for(int i=0; i<unCompleteOrders.size(); i++){
            Long orderId = unCompleteOrders.get(i).getId();

            OrderCustomerDao customerDao = customerDaoList.get(i);
            Long customerId = customerDao.getCustomerId();
            String alias = customerDao.getAlias();

            List<String> menuNames = orderMenuNameMap.get(orderId);

            OrderResponseDao orderResponseDao = orderResponseDaoList.get(i);
            Integer amount = orderResponseDao.getQuantity();
            Timestamp orderDate = orderResponseDao.getOrderDate();

            OrderResponseDto orderResponseDto = new OrderResponseDto(orderId, customerId, alias, menuNames, amount, orderDate);
            orderResponseDtoList.add(orderResponseDto);
        }

        return orderResponseDtoList;
    }

    public List<OrderResponseDto> getAllOrders(){
        //모든 주문 데이터 조회
        List<Order> orders = orderRepository.findAll();

        //각 주문별 OrderCustomerDto(고객ID, 별명) 조회
        List<OrderCustomerDao> customerDaoList = new ArrayList<>();

        for (Order order : orders) {
            OrderCustomerDao customerDao = customerRepository.findOrderCustomerDaoById(order.getCustomerId());
            customerDaoList.add(customerDao);
        }

        //각 주문 포함 메뉴 이름 목록 조회
        Map<Long, List<String>> orderMenuNameMap = new HashMap<>();

        for (Order order : orders) {
            List<Long> menuIds = orderMenuRepository.findAllMenuIds(order.getId());
            List<String> menuNameList = new ArrayList<>();

            for (Long menuId : menuIds) {
                String menuName = menuRepository.getMenuName(menuId);
                menuNameList.add(menuName);
            }

            orderMenuNameMap.put(order.getId(), menuNameList);
        }

        // 각 주문별 OrderResponseDao 조회
        List<OrderResponseDao> orderResponseDaoList = new ArrayList<>();
        for (Order order : orders) {
            OrderResponseDao orderResponseDao = orderRepository.findOrderResponseDaoById(order.getId());
            orderResponseDaoList.add(orderResponseDao);
        }

        // orderResponseDto 목록 생성
        List<OrderResponseDto> orderResponseDtoList = new ArrayList<>();

        for(int i=0; i<orders.size(); i++){
            Long orderId = orders.get(i).getId();

            OrderCustomerDao customerDao = customerDaoList.get(i);
            Long customerId = customerDao.getCustomerId();
            String alias = customerDao.getAlias();

            List<String> menuNames = orderMenuNameMap.get(orderId);

            OrderResponseDao orderResponseDao = orderResponseDaoList.get(i);
            Integer amount = orderResponseDao.getQuantity();
            Timestamp orderDate = orderResponseDao.getOrderDate();

            OrderResponseDto orderResponseDto = new OrderResponseDto(orderId, customerId, alias, menuNames, amount, orderDate);
            orderResponseDtoList.add(orderResponseDto);
        }

        return orderResponseDtoList;
    }

    @Transactional(readOnly = false)
    public void updateCompleteOrder(Long orderId){
       Optional<Order> findOrder = orderRepository.findById(orderId);

        if(findOrder.isEmpty()){
            throw new IllegalStateException("주문이 존재하지 않습니다");
        }

        OrderUpdateDto orderUpdateDto = new OrderUpdateDto();
        orderUpdateDto.setIsComplete(true);

        orderRepository.update(orderId,orderUpdateDto);
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
