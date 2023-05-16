package hello.cafemate.repository;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.jdbc.support.JdbcUtils;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Optional;


public abstract class AbstractRepository<T, V> {
    private final DataSource dataSource;
    protected final NamedParameterJdbcTemplate template;

    public AbstractRepository(DataSource dataSource) {
        this.dataSource = dataSource;
        template=new NamedParameterJdbcTemplate(dataSource);
    }

    public abstract T save(T target);
    public abstract Optional<T> findById(Long id);

    public abstract void update(Long id, V updateParam);

    public abstract void deleteById(Long id);

    protected void close(Connection con, Statement stmt, ResultSet rs){
        JdbcUtils.closeResultSet(rs);
        JdbcUtils.closeStatement(stmt);

        DataSourceUtils.releaseConnection(con, dataSource);
    }

    protected Connection getConnection(){
        Connection con=DataSourceUtils.getConnection(dataSource);
        return con;
    }
}
