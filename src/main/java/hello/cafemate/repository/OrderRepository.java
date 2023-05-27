package hello.cafemate.repository;

import hello.cafemate.domain.Menu;
import hello.cafemate.domain.Order;
import hello.cafemate.domain.OrderMenu;
import hello.cafemate.dto.update_dto.OrderUpdateDto;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class OrderRepository extends AbstractRepository<Order, OrderUpdateDto> {

    private OrderMenuRepository orderMenuRepository;

    public OrderRepository(DataSource dataSource) {
        super(dataSource);
        orderMenuRepository = new OrderMenuRepository(dataSource);
    }

    @Override
    public Order save(Order target) {
        String sql = "insert into orders(customer_id, quantity, payments, use_point_amount, is_complete, orders_date)" +
                " values(:customerId, :quantity, :payments, :usePointAmount, :isComplete, :orderDate)";

        ConvertSqlParameterSource param = new ConvertSqlParameterSource(target);
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        template.update(sql, param, keyHolder);

        Long id = keyHolder.getKey().longValue();
        target.setId(id);

        return target;
    }

    public Order saveMenus(Order order, List<OrderMenu> menuList){
        for (OrderMenu orderMenu : menuList) {
            orderMenuRepository.save(orderMenu);
        }

        order.addMenu(menuList);

        return order;
    }

    @Override
    public Optional<Order> findById(Long id) {
        String sql = "select id, customer_id, quantity, payments, use_point_amount, is_complete, orders_date" +
                " from orders where id=:id";

        try {
            Map<String, Object> param = Map.of("id", id);
            Order order = template.queryForObject(sql, param, orderRowMapper());

            List<OrderMenu> orderMenus = orderMenuRepository.findAll(id);

            if (orderMenus != null)
                order.addMenu(orderMenus);

            return Optional.of(order);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }



    public List<Order> findOrdersByCustomerId(Long customerId){
        String sql="select id, customer_id, quantity, payments, use_point_amount, is_complete, orders_date" +
                " from orders where customer_id=:customerId";

        MapSqlParameterSource param =
                new MapSqlParameterSource().addValue("customerId", customerId);

        List<Order> orders = template.query(sql, param, orderRowMapper());
        for (Order order : orders) {
            List<OrderMenu> orderMenus = orderMenuRepository.findAll(order.getId());
            order.addMenu(orderMenus);
        }

        return orders;
    }

    @Override
    public void update(Long id, OrderUpdateDto updateParam) {
        String sql = "update orders set ";

        Integer quantity = updateParam.getQuantity();
        Integer payments = updateParam.getPayments();
        Integer usePointAmount = updateParam.getUsePointAmount();
        Boolean isComplete = updateParam.getIsComplete();
        LocalDateTime orderDate = updateParam.getOrderDate();

        List<String> paramList = new ArrayList<>();
        MapSqlParameterSource param = new MapSqlParameterSource();

        if (quantity != null) {
            paramList.add("quantity=:quantity");
            param.addValue("quantity", quantity);
        }

        if (payments != null) {
            paramList.add("payments=:payments");
            param.addValue("payments", payments);
        }

        if (usePointAmount != null) {
            paramList.add("use_point_amount=:usePointAmount");
            param.addValue("usePointAmount", usePointAmount);
        }

        if (isComplete != null) {
            paramList.add("is_complete=:isComplete");
            param.addValue("isComplete", isComplete ? 1 : 0);
        }

        if (orderDate != null) {
            paramList.add("order_date=:orderDate");
            param.addValue("orderDate", orderDate);
        }

        if(paramList.size()==0) return;

        sql = getUpdateQuery(sql, paramList);
        param.addValue("id", id);

        template.update(sql, param);
    }

    @Override
    public void deleteById(Long id) {
        List<OrderMenu> orderMenus = orderMenuRepository.findAll(id);
        for (OrderMenu orderMenu : orderMenus) {
            orderMenuRepository.deleteById(orderMenu.getId());
        }

        String sql = "delete from orders where id=:id";

        MapSqlParameterSource param = new MapSqlParameterSource().addValue("id", id);
        template.update(sql, param);
    }

    private RowMapper<Order> orderRowMapper() {
        return (rs, rowNum) -> new Order(
                rs.getLong("id"),
                rs.getLong("customer_id"),
                rs.getInt("quantity"),
                rs.getInt("payments"),
                rs.getInt("use_point_amount"),
                rs.getInt("is_complete") == 1 ? true : false,
                rs.getTimestamp("orders_date")
        );
    }

    private static class ConvertSqlParameterSource
            extends BeanPropertySqlParameterSource {

        /**
         * Create a new BeanPropertySqlParameterSource for the given bean.
         *
         * @param object the bean instance to wrap
         */
        public ConvertSqlParameterSource(Object object) {
            super(object);
        }

        @Override
        public Object getValue(String paramName) throws IllegalArgumentException {
            Object value = super.getValue(paramName);

            if (value instanceof Timestamp) {
                return ((Timestamp) value).toLocalDateTime();
            }

            if (paramName.equals("isComplete")) {
                return ((Boolean)value)?1:0;
            }

            return value;
        }
    }
}
