package com.bcauction.infrastructure.repository;

import com.bcauction.domain.Auction;
import com.bcauction.domain.exception.RepositoryException;
import com.bcauction.domain.repository.IAuctionRepository;
import com.bcauction.infrastructure.repository.factory.AuctionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class AuctionRepository implements IAuctionRepository
{
	private JdbcTemplate jdbcTemplate;
	private SimpleJdbcInsert simpleJdbcInsert;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public List<Auction> 목록조회()
	{
		StringBuilder sbSql =  new StringBuilder("SELECT * FROM Auction WHERE is_valid=?");
		try {
			return this.jdbcTemplate.query(sbSql.toString(),
			                               new Object[]{ "V" }, (rs, rowNum) -> AuctionFactory.생성(rs));
		} catch (Exception e) {
			throw new RepositoryException(e, e.getMessage());
		}
	}

	@Override
	public Auction 조회(final long id)
	{
		StringBuilder sbSql =  new StringBuilder("SELECT * FROM Auction WHERE id=?");
		try {
			return this.jdbcTemplate.queryForObject(sbSql.toString(),
			                                        new Object[] { id }, (rs, rowNum) -> AuctionFactory.생성(rs) );
		} catch (EmptyResultDataAccessException e) {
			return null;
		} catch (Exception e) {
			throw new RepositoryException(e, e.getMessage());
		}
	}

	@Override
	public Auction 조회(final String txs_address)
	{
		StringBuilder sbSql =  new StringBuilder("SELECT * FROM Auction WHERE txs_address=?");
		try {
			return this.jdbcTemplate.queryForObject(sbSql.toString(),
			                                        new Object[] { txs_address }, (rs, rowNum) -> AuctionFactory.생성(rs) );
		} catch (EmptyResultDataAccessException e) {
			return null;
		} catch (Exception e) {
			throw new RepositoryException(e, e.getMessage());
		}
	}

	@Override
	public long 생성(final Auction 경매) {
		try {
			Map<String, Object> paramMap = new HashMap<>();
			paramMap.put("auction_creator_id", 경매.getAuctionCreatorId());
			paramMap.put("auction_id", 경매.getAuctionId());
			paramMap.put("create_time", 경매.getCreateTime());
			paramMap.put("is_valid", 경매.getIsValid());
			paramMap.put("start_time", 경매.getStartTime());
			paramMap.put("end_time", 경매.getEndTime());
			paramMap.put("lowest_price", 경매.getLowestPrice());
			paramMap.put("txs_address", 경매.getTxsAddress());

			this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
					.withTableName("Auction")
					.usingGeneratedKeyColumns("id");

			Number newId = simpleJdbcInsert.executeAndReturnKey(paramMap);
			return newId.longValue();
		} catch (Exception e) {
			throw new RepositoryException(e, e.getMessage());
		}
	}

	@Override
	public int 수정(final Auction 경매)
	{
		StringBuilder sbSql =  new StringBuilder("UPDATE Auction ");
		sbSql.append("SET is_valid=? AND end_time=? ");
		sbSql.append("where id=? AND auction_creator_id=? AND auction_id=?");
		try {
			return this.jdbcTemplate.update(sbSql.toString(),
			                                new Object[] {
					                           경매.getIsValid(),
					                           경매.getEndTime(),
					                           경매.getId(),
					                           경매.getAuctionCreatorId(),
					                           경매.getAuctionId()
			                                });
		} catch (Exception e) {
			throw new RepositoryException(e, e.getMessage());
		}
	}

	@Override
	public int 삭제(final long id)
	{
		StringBuilder sbSql =  new StringBuilder("DELETE FROM Auction WHERE id=?");

		try {
			return this.jdbcTemplate.update(sbSql.toString(),
			                                new Object[] { id });
		} catch (Exception e) {
			throw new RepositoryException(e, e.getMessage());
		}
	}
}
