package hello.cafemate.repository;

import hello.cafemate.domain.Category;
import hello.cafemate.domain.Menu;
import hello.cafemate.dto.update_dto.MenuUpdateDto;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class MenuRepository extends AbstractRepository<Menu, MenuUpdateDto> {
    public MenuRepository(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public Menu save(Menu target) {
        String sql="insert into menu(name, category, price, on_sale, registration_date)" +
                " values(:name, :category, :price, :onSale, :registrationDate)";

        ConvertSqlParameterSource param = new ConvertSqlParameterSource(target);
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        template.update(sql, param, keyHolder);

        Long id = keyHolder.getKey().longValue();
        target.setId(id);

        return target;
    }

    @Override
    public Optional<Menu> findById(Long id) {
        String sql="select id, name, category, price, on_sale, registration_date" +
                " from menu where id=:id";

        try{
            Map<String, Object> param=Map.of("id", id);
            Menu menu = template.queryForObject(sql, param, menuRowMapper());
            return Optional.of(menu);
        }catch(EmptyResultDataAccessException e){
            return Optional.empty();
        }
    }

    public List<Menu> findAll(){
        String sql="select id, name, category, price, on_sale, registration_date" +
                " from menu";

        return template.query(sql, menuRowMapper());
    }

    public Optional<Menu> findByName(String name){
        String sql="select id, name, category, price, on_sale, registration_date" +
                " from menu";

        try{
            Map<String, Object> param = Map.of("name", name);
            Menu menu = template.queryForObject(sql, param, menuRowMapper());
            return Optional.of(menu);
        }catch(EmptyResultDataAccessException e){
            return Optional.empty();
        }
    }

    @Override
    public void update(Long id, MenuUpdateDto updateParam) {
        String sql="update menu set ";

        String name = updateParam.getName();
        Category category = updateParam.getCategory();
        Integer price = updateParam.getPrice();
        Boolean onSale = updateParam.getOnSale();
        LocalDateTime registrationDate = updateParam.getRegistrationDate();

        List<String> paramList = new ArrayList<>();
        MapSqlParameterSource param=new MapSqlParameterSource();

        if(StringUtils.hasText(name)){
            paramList.add("name=:name");
            param.addValue("name", name);
        }

        if(category!=null){
            paramList.add("category=:category");
            param.addValue("category", category.toString());
        }

        if(price!=null){
            paramList.add("price=:price");
            param.addValue("price", price);
        }

        if(onSale!=null){
            paramList.add("on_sale=:onSale");
            param.addValue("onSale", onSale?1:0);
        }

        if(registrationDate!=null){
            paramList.add("registration_date=:registrationDate");
            param.addValue("registrationDate", registrationDate);
        }

        if(paramList.size()==0) return;

        sql=getUpdateQuery(sql, paramList);
        param.addValue("id", id);

        template.update(sql, param);
    }

    @Override
    public void deleteById(Long id) {
        String sql="delete from menu where id=:id";

        MapSqlParameterSource param =
                new MapSqlParameterSource().addValue("id", id);

        template.update(sql, param);
    }

    private RowMapper<Menu> menuRowMapper(){
        return (rs, rowNum)->new Menu(
                rs.getLong("id"),
                rs.getString("name"),
                Category.valueOf(rs.getString("category")),
                rs.getInt("price"),
                rs.getInt("on_sale")==0?true:false,
                rs.getTimestamp("registration_date")
        );
    }

    private static class ConvertSqlParameterSource
        extends BeanPropertySqlParameterSource{

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
            Object value=super.getValue(paramName);

            if(paramName.equals("category")){
               return value.toString();
            }

            return value;
        }
    }
}
