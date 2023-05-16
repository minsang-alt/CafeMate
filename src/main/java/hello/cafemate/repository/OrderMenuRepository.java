package hello.cafemate.repository;

import hello.cafemate.domain.OrderMenu;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class OrderMenuRepository extends AbstractRepository<OrderMenu, OrderMenu> {
    public OrderMenuRepository(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public OrderMenu save(OrderMenu target) {
        String sql="insert into orders_menu(orders_id, menu_id) " +
                "values (:orderId, :menuId)";

        BeanPropertySqlParameterSource param = new BeanPropertySqlParameterSource(target);
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        template.update(sql, param, keyHolder);

        Long id = keyHolder.getKey().longValue();
        target.setId(id);

        return target;
    }

    @Override
    public Optional<OrderMenu> findById(Long id) {
        String sql="select id, orers_id, menu_id from orders_menu" +
                " where id=:id";

        try{
            Map<String, Object> param=Map.of("id", id);
            OrderMenu orderMenu = template.queryForObject(sql, param, orderMenuRowMapper());
            return Optional.of(orderMenu);
        }catch(EmptyResultDataAccessException e){
            return Optional.empty();
        }
    }

    public List<OrderMenu> findAll(Long orderId){
        String sql="select id, orders_id, menu_id from orders_menu" +
                " where orders_id=:orderId";

        MapSqlParameterSource param = new MapSqlParameterSource().addValue("orderId", orderId);
        return template.query(sql, param, orderMenuRowMapper());
    }

    @Override
    public void update(Long id, OrderMenu updateParam) {
    }

    @Override
    public void deleteById(Long id) {
        String sql="delete from orders_menu where id=:id";

        MapSqlParameterSource param = new MapSqlParameterSource().addValue("id", id);

        template.update(sql, param);
    }

    private RowMapper<OrderMenu> orderMenuRowMapper(){
        return (rs, rowNum) ->new OrderMenu(
                rs.getLong("id"),
                rs.getLong("orders_id"),
                rs.getLong("menu_id")
        );
    }
}
