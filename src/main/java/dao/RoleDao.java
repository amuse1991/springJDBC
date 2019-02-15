package dao;

import dto.Role;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static dao.RoleDaoSqls.*;

@Repository // DAO객체는 repository 어노테이션 붙여준다.
public class RoleDao {


    //spring JDBC 객체
    private NamedParameterJdbcTemplate jdbc;
    // 데이터를 DTO에 매핑시켜주는 객체(데이터 -> DTO)
    private RowMapper<Role> rowMapper = BeanPropertyRowMapper.newInstance(Role.class);
    //insert를 위한 객체
    private SimpleJdbcInsert insertAction;


    //config/DBConfig에서 bean으로 등록한 dataSource 객체가 생성자에 인자로 전달된다.
    public RoleDao(DataSource dataSource) {
        this.jdbc = new NamedParameterJdbcTemplate(dataSource);
        this.insertAction = new SimpleJdbcInsert(dataSource)
                .withTableName("role");
    }


    public List<Role> selectAll(){
        // quert() 메소드는 rowMapper를 이용해 각각의 데이터를 DTO로 변환하고, 결과물을 리스트에 담아 반환한다.
        return jdbc.query(SELECT_ALL, Collections.emptyMap(),rowMapper);
    }

    public int insert(Role role){
        SqlParameterSource params = new BeanPropertySqlParameterSource(role);
        return insertAction.execute(params);
    }

    public int update(Role role) {
        SqlParameterSource params = new BeanPropertySqlParameterSource(role);
        return jdbc.update(UPDATE, params);
    }

    public int deleteById(Integer id) {
        //SqlParameterSource params = new BeanPropertySqlParameterSource(role); 사용해도 됨.
        Map<String, ?> params = Collections.singletonMap("roleId", id);
        return jdbc.update(DELETE_BY_ROLE_ID, params);
    }

    public Role selectById(Integer id) {
        try {
            Map<String, ?> params = Collections.singletonMap("roleId", id);
            return jdbc.queryForObject(SELECT_BY_ROLE_ID, params, rowMapper);
        }catch(EmptyResultDataAccessException e) {
            return null;
        }
    }
}
