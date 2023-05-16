package hello.cafemate.repository;

import hello.cafemate.domain.Member;
import hello.cafemate.dto.update_dto.MemberUpdateDto;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class MemberRepository extends AbstractRepository<Member, MemberUpdateDto> {
    public MemberRepository(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public Member save(Member target) {
        String sql="insert into member (member_id, password, name, phone_number, e_mail, is_admin) " +
                "values (:memberId, :password, :name, :phoneNumber, :eMail, :isAdmin)";

        BeanPropertySqlParameterSource param = new BeanPropertySqlParameterSource(target);
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        template.update(sql, param, keyHolder);

        Long id = keyHolder.getKey().longValue();
        target.setId(id);

        return target;
    }

    @Override
    public Optional<Member> findById(Long id) {
        String sql="select id, member_id, password, name, phone_number, e_mail, is_admin " +
                "from member where id=:id";

        try{
            Map<String, Object> param=Map.of("id", id);
            Member member = template.queryForObject(sql, param, memberRowMapper());
            return Optional.of(member);
        }catch(EmptyResultDataAccessException e){
            return Optional.empty();
        }
    }

    public List<Member> findByMemberId(String memberId){
        String sql="select id, member_id, password, name, phone_number, e_mail, is_admin " +
                "from member where member_id=:memberId";

        MapSqlParameterSource param =
                new MapSqlParameterSource().addValue("memberId", memberId);

        return template.query(sql, param, memberRowMapper());
    }

    public List<Member> findAll(){
        String sql="select id, member_id, password, name, phone_number, e_mail, is_admin " +
                "from member";

        return template.query(sql, memberRowMapper());
    }

    @Override
    public void update(Long id, MemberUpdateDto updateParam) {
        String sql="update member set ";

        String memberId = updateParam.getMemberId();
        String password = updateParam.getPassword();
        String name = updateParam.getName();
        String phoneNumber = updateParam.getPhoneNumber();
        String eMail = updateParam.getEMail();
        Boolean isAdmin = updateParam.getIsAdmin();

        MapSqlParameterSource param = new MapSqlParameterSource();

        if(StringUtils.hasText(memberId)){
            sql+="member_id=:memberId";
            param.addValue("memberId", memberId);
        }

        if(StringUtils.hasText(password)){
            sql+=" password=:password";
            param.addValue("password", password);
        }

        if(StringUtils.hasText(name)){
            sql+=" name=:name";
            param.addValue("name", name);
        }

        if(StringUtils.hasText(phoneNumber)){
            sql+=" phone_number=:phoneNumber";
            param.addValue("phoneNumber", phoneNumber);
        }

        if(StringUtils.hasText(eMail)){
            sql+=" e_mail=:eMail";
            param.addValue("eMail", eMail);
        }

        if(isAdmin!=null){
            sql+=" is_admin=:isAdmin";
            param.addValue("isAdmin", isAdmin?1:0);
        }

        sql+=" where id=:id";
        param.addValue("id", id);

        template.update(sql, param);
    }

    @Override
    public void deleteById(Long id) {
        String sql="delete from member where id=:id";

        MapSqlParameterSource param =
                new MapSqlParameterSource().addValue("id", id);

        template.update(sql, param);
    }

    private RowMapper<Member> memberRowMapper(){
        return (rs, rowNum)->new Member(
                rs.getLong("id"),
                rs.getString("member_id"),
                rs.getString("password"),
                rs.getString("name"),
                rs.getString("phone_number"),
                rs.getString("e_mail"),
                rs.getInt("is_admin")==1?true:false
        );
    }
}
