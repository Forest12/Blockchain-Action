package com.bcauction.infrastructure.repository;

import com.bcauction.domain.Member;
import com.bcauction.domain.exception.RepositoryException;
import com.bcauction.domain.repository.IMemberRepository;
import com.bcauction.infrastructure.repository.factory.MemberFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class MemberRepository implements IMemberRepository {

    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert simpleJdbcInsert;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public List<Member> 목록조회() {
        StringBuilder sbSql =  new StringBuilder("SELECT * FROM Member ");
        try {
            return this.jdbcTemplate.query(sbSql.toString(),
                    new Object[]{}, (rs, rowNum) -> MemberFactory.생성(rs));
        } catch (Exception e) {
            throw new RepositoryException(e, e.getMessage());
        }
    }

    @Override
    public Member 조회(long id) {
        StringBuilder sbSql =  new StringBuilder("SELECT * FROM Member WHERE id=?");
        try {
            return this.jdbcTemplate.queryForObject(sbSql.toString(),
                    new Object[] { id }, (rs, rowNum) -> MemberFactory.생성(rs) );
        } catch (EmptyResultDataAccessException e) {
            return null;
        } catch (Exception e) {
            throw new RepositoryException(e, e.getMessage());
        }
    }

    @Override
    public Member 조회(final String 이메일)
    {
        StringBuilder sbSql = new StringBuilder("SELECT * FROM Member WHERE email=?");
        try{
            return this.jdbcTemplate.queryForObject(sbSql.toString(),
                                                    new Object[] { 이메일 }, (rs, rowNum) -> MemberFactory.생성(rs) );
        } catch (EmptyResultDataAccessException e) {
            return null;
        } catch (Exception e) {
            throw new RepositoryException(e, e.getMessage());
        }
    }


    @Override
    public long addMember(Member member) {
        try {
            Map<String, Object> paramMap = new HashMap<>();
            paramMap.put("username", member.getUsername());
            paramMap.put("email", member.getEmail());
            paramMap.put("registration_date", LocalDateTime.now());
            paramMap.put("password", member.getPassword());

            this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                    .withTableName("Member")
                    .usingGeneratedKeyColumns("id");

            Number newId = simpleJdbcInsert.executeAndReturnKey(paramMap);
            return newId.longValue();

        } catch (Exception e) {
            throw new RepositoryException(e, e.getMessage());
        }
    }

    @Override
    public int 수정(Member 회원) {
        StringBuilder sbSql =  new StringBuilder("UPDATE Member ");
        sbSql.append("SET username=?, email=?, password=? ");
        sbSql.append("WHERE id=?");
        try {
            return this.jdbcTemplate.update(sbSql.toString(),
                    new Object[] { 회원.getUsername(), 회원.getEmail(), 회원.getPassword(), 회원.getId() });
        } catch (Exception e) {
            throw new RepositoryException(e, e.getMessage());
        }
    }

    @Override
    public int 삭제(long id) {
        StringBuilder sbSql =  new StringBuilder("DELETE FROM Member ");
        sbSql.append("WHERE id=?");
        try {
            return this.jdbcTemplate.update(sbSql.toString(),
                    new Object[] { id });
        } catch (Exception e) {
            throw new RepositoryException(e, e.getMessage());
        }
    }
}
