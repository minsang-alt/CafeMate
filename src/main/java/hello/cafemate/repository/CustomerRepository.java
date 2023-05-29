package hello.cafemate.repository;

import hello.cafemate.dao.OrderCustomerDao;
import hello.cafemate.domain.Customer;
import hello.cafemate.dto.update_dto.CustomerUpdateDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Repository
public class CustomerRepository extends AbstractRepository<Customer, CustomerUpdateDto> {
    public CustomerRepository(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public Customer save(Customer target) {
        String sql = "insert into customer (customer_id, e_mail, password, name, phone_number, alias, saved_point,status)"
                + "values(:customerId, :eMail, :password, :name, :phoneNumber, :alias, :savedPoint,:status)";

        BeanPropertySqlParameterSource param = new BeanPropertySqlParameterSource(target);
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        template.update(sql, param, keyHolder);

        Long id = keyHolder.getKey().longValue();
        target.setId(id);

        return target;
    }

    @Override
    public Optional<Customer> findById(Long id) {
        String sql = "select id, customer_id, e_mail, password, name, phone_number, alias, saved_point,status " +
                "from customer where id=:id";

        try {
            Map<String, Object> param = Map.of("id", id);
            Customer customer = template.queryForObject(sql, param, customerRowMapper());
            return Optional.of(customer);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public Optional<Customer> findByCustomerId(String customerId) {
        String sql = "select id, customer_id, e_mail, password, name, phone_number, alias, saved_point,status" +
                " from customer where customer_id=:customerId";

        try {
            Map<String, Object> param = Map.of("customerId", customerId);
            Customer customer = template.queryForObject(sql, param, customerRowMapper());
            return Optional.of(customer);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public List<Customer> findAll() {
        String sql = "select id, customer_id, e_mail, password, name, phone_number, alias, saved_point,status from customer";

        return template.query(sql, customerRowMapper());
    }

    public OrderCustomerDao findOrderCustomerDaoById(Long id) {
        String sql = "select id, alias from customer where id=:id";

        MapSqlParameterSource param = new MapSqlParameterSource().addValue("id", id);
        return template.queryForObject(sql, param,
                (rs, rowNum) -> new OrderCustomerDao(
                        rs.getLong("id"),
                        rs.getString("alias")
                ));
    }

    @Override
    public void update(Long id, CustomerUpdateDto updateParam) {
        String sql = "update customer set ";

        String customerId = updateParam.getCustomerId();
        String name = updateParam.getName();
        String password = updateParam.getPassword();
        String alias = updateParam.getAlias();
        String phoneNumber = updateParam.getPhoneNumber();
        String eMail = updateParam.getEMail();
        Boolean status = updateParam.getStatus();
        int savedPoint = updateParam.getSavedPoint();

        List<String> paramList = new ArrayList<>();
        MapSqlParameterSource param = new MapSqlParameterSource();

        if (StringUtils.hasText(customerId)) {
            paramList.add("customer_id=:customerId");
            param.addValue("customerId", customerId);
        }

        if (StringUtils.hasText(name)) {
            paramList.add("name=:name");
            param.addValue("name", name);
        }

        if (StringUtils.hasText(password)) {
            paramList.add("password=:password");
            param.addValue("password", password);
        }

        if (StringUtils.hasText(alias)) {
            paramList.add("alias=:alias");
            param.addValue("alias", alias);
        }

        if (StringUtils.hasText(phoneNumber)) {
            paramList.add("phone_number=:phoneNumber");
            param.addValue("phoneNumber", phoneNumber);
        }

        if (StringUtils.hasText(eMail)) {
            paramList.add("e_mail=:eMail");
            param.addValue("eMail", eMail);
        }

        if (savedPoint != 0) {
            paramList.add("saved_point=:savedPoint");
            param.addValue("savedPoint", savedPoint);
        }
        if(status != true){
            paramList.add("status=:status");
            param.addValue("status",status);
        }

        sql = getUpdateQuery(sql, paramList);
        param.addValue("id", id);

        template.update(sql, param);
    }

    @Override
    public void deleteById(Long id) {
        String sql = "delete from customer where id=:id";

        MapSqlParameterSource param = new MapSqlParameterSource()
                .addValue("id", id);

        template.update(sql, param);
    }

    private RowMapper<Customer> customerRowMapper() {
        return (rs, rowNum) -> new Customer(
                rs.getLong("id"),
                rs.getString("customer_id"),
                rs.getString("e_mail"),
                rs.getString("password"),
                rs.getString("name"),
                rs.getString("phone_number"),
                rs.getString("alias"),
                rs.getInt("saved_point"),
                rs.getBoolean("status")
                );
    }

}
