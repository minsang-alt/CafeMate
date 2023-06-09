package hello.cafemate.repository;

import hello.cafemate.domain.Point;
import hello.cafemate.dto.update_dto.PointUpdateDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Repository
public class PointRepository extends AbstractRepository<Point, PointUpdateDto> {
    public PointRepository(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public Point save(Point target) {
        String sql = "insert into point (customer_id, amount, saved_date)" +
                " values(:customerId, :amount, :savedDate)";

        BeanPropertySqlParameterSource param = new BeanPropertySqlParameterSource(target);
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        template.update(sql, param, keyHolder);

        Long id = keyHolder.getKey().longValue();
        target.setId(id);

        return target;
    }

    @Override
    public Optional<Point> findById(Long id) {
        String sql = "select id, customer_id, amount, saved_date " +
                "from point where id=:id";

        try {
            Map<String, Object> param = Map.of("id", id);
            Point point = template.queryForObject(sql, param, pointRowMapper());
            return Optional.of(point);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public List<Point> findAllByCustomerId(Long customerId) {
        String sql = "select id, customer_id, amount, saved_date" +
                " from point where customer_id=:customerId";

        MapSqlParameterSource param =
                new MapSqlParameterSource().addValue("customerId", customerId);

        return template.query(sql, param, pointRowMapper());
    }

    @Override
    public void update(Long id, PointUpdateDto updateParam) {
        String sql = "update point set ";

        Integer amount = updateParam.getAmount();
        Timestamp savedDate = updateParam.getSavedDate();

        List<String> paramList = new ArrayList<>();
        MapSqlParameterSource param = new MapSqlParameterSource();

        if (amount != null) {
            paramList.add("amount=:amount");
            param.addValue("amount", amount);
        }

        if (savedDate != null) {
            paramList.add("saved_date=:savedDate");
            param.addValue("savedDate", savedDate);
        }

        if (paramList.size() == 0) return;

        sql = getUpdateQuery(sql, paramList);
        param.addValue("id", id);

        template.update(sql, param);
    }

    @Override
    public void deleteById(Long id) {
        String sql = "delete from point where id=:id";

        MapSqlParameterSource param =
                new MapSqlParameterSource().addValue("id", id);

        template.update(sql, param);
    }

    private RowMapper<Point> pointRowMapper() {
        return (rs, rowNum) -> new Point(
                rs.getLong("id"),
                rs.getLong("customer_id"),
                rs.getInt("amount"),
                rs.getTimestamp("saved_date")
        );
    }
}
