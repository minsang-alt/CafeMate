package hello.cafemate.repository;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.jdbc.support.JdbcUtils;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;
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

    protected String getUpdateQuery(String sql, List<String> paramList) {
        for(int i = 0; i< paramList.size(); i++){
            if(i== paramList.size()-1){
                sql += paramList.get(i);
                break;
            }

            sql += paramList.get(i)+", ";
        }

        sql += " where id=:id";
        return sql;
    }

}
