package com.bcauction.infrastructure.repository;

import com.bcauction.domain.DigitalWork;
import com.bcauction.domain.exception.RepositoryException;
import com.bcauction.domain.repository.IDigitalWorkRepository;
import com.bcauction.infrastructure.repository.factory.DigitalWorkFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Repository
public class DigitalWorkRepository implements IDigitalWorkRepository
{
	private JdbcTemplate jdbcTemplate;
	private SimpleJdbcInsert simpleJdbcInsert;

	public static final Logger logger = LoggerFactory.getLogger(DigitalWorkRepository.class);

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public List<DigitalWork> 목록조회() {
		StringBuilder sbSql =  new StringBuilder("SELECT * FROM DigitalWork WHERE is_disclosure=? AND is_valid=?");
		try {
			return this.jdbcTemplate.query(sbSql.toString(),
							   new Object[]{"Y", "Y"}, (rs, rowNum) -> DigitalWorkFactory.생성(rs));
		} catch (Exception e) {
			throw new RepositoryException(e, e.getMessage());
		}
	}


	@Override
	public List<DigitalWork> 사용자작품목록조회(final long 회원id) {
		StringBuilder sbSql =  new StringBuilder("SELECT * FROM DigitalWork WHERE is_valid=? AND member_id=? ");
		try {
			return this.jdbcTemplate.query(sbSql.toString(),
							   new Object[]{"Y", 회원id}, (rs, rowNum) -> DigitalWorkFactory.생성(rs));
		} catch (Exception e) {
			throw new RepositoryException(e, e.getMessage());
		}
	}

	@Override
	public DigitalWork 조회(final long id) {
		StringBuilder sbSql =  new StringBuilder("SELECT * FROM DigitalWork WHERE id=?");
		try {
			return this.jdbcTemplate.queryForObject(sbSql.toString(),
								new Object[] { id }, (rs, rowNum) -> DigitalWorkFactory.생성(rs) );
		} catch (EmptyResultDataAccessException e) {
			return null;
		} catch (Exception e) {
			throw new RepositoryException(e, e.getMessage());
		}
	}

	@Override
	public DigitalWork 조회(final long 회원id, final String 이름) {
		StringBuilder sbSql =  new StringBuilder("SELECT * FROM DigitalWork WHERE member_id=? AND work_name=?");
		try {
			return this.jdbcTemplate.queryForObject(sbSql.toString(),
			                                        new Object[] { 회원id, 이름 }, (rs, rowNum) -> DigitalWorkFactory.생성(rs) );
		} catch (EmptyResultDataAccessException e) {
			return null;
		} catch (Exception e) {
			throw new RepositoryException(e, e.getMessage());
		}
	}

	@Override
	public long 추가(final DigitalWork 작품) {
		try {
			
			logger.debug("추가"+작품.toString());

			Map<String, Object> paramMap = new HashMap<>();
			paramMap.put("work_name", 작품.getWorkName());
			// logger.debug("check~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
			paramMap.put("description", 작품.getDescription());
			paramMap.put("is_disclosure", 작품.getIsDisclosure());
			paramMap.put("is_valid", 작품.getIsValid());
			paramMap.put("member_id", 작품.getMemberId());

			this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
					.withTableName("DigitalWork")
					.usingGeneratedKeyColumns("id");

			Number newId = simpleJdbcInsert.executeAndReturnKey(paramMap);
			
			return newId.longValue();
		} catch (Exception e) {
			throw new RepositoryException(e, e.getMessage());
		}
	}

	@Override
	public int 수정(final DigitalWork 작품) {
		StringBuilder sbSql =  new StringBuilder("UPDATE DigitalWork ");
		sbSql.append("SET work_name=?, description=?, is_disclosure=?, is_valid=?, member_id=? ");
		sbSql.append("where id=?");
		try {
			return this.jdbcTemplate.update(sbSql.toString(),
								new Object[] {
										작품.getWorkName(),
										작품.getDescription(),
										작품.getIsDisclosure(),
										작품.getIsValid(),
										작품.getMemberId(),
										작품.getId()
			                                });
		} catch (Exception e) {
			throw new RepositoryException(e, e.getMessage());
		}
	}

	@Override
	public int 삭제(final long id) { // 상태를 N으로 업데이트
		StringBuilder sbSql =  new StringBuilder("UPDATE DigitalWork ");
		sbSql.append("SET is_valid=?, is_disclosure=? ");
		sbSql.append("where id=?");

		try {
			return this.jdbcTemplate.update(sbSql.toString(),
								new Object[] { "N", "N", id });
		} catch (Exception e) {
			throw new RepositoryException(e, e.getMessage());
		}

	}

}
