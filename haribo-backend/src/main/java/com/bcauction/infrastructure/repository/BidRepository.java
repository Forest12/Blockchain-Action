package com.bcauction.infrastructure.repository;

import com.bcauction.domain.Bid;
import com.bcauction.domain.exception.RepositoryException;
import com.bcauction.domain.repository.IBidRepository;
import com.bcauction.infrastructure.repository.factory.BidFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Repository
public class BidRepository implements IBidRepository
{

	public static final Logger logger = LoggerFactory.getLogger(BidRepository.class);

	private JdbcTemplate jdbcTemplate;
	private SimpleJdbcInsert simpleJdbcInsert;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public List<Bid> 목록조회() {
		StringBuilder sbSql =  new StringBuilder("SELECT * FROM Bid");
		try {
			return this.jdbcTemplate.query(sbSql.toString(),
							   new Object[]{}, (rs, rowNum) -> BidFactory.생성(rs));
		} catch (Exception e) {
			throw new RepositoryException(e, e.getMessage());
		}
	}

	@Override
	public Bid 조회(final long id) {
		StringBuilder sbSql =  new StringBuilder("SELECT * FROM Bid WHERE id=?");
		try {
			return this.jdbcTemplate.queryForObject(sbSql.toString(),
								new Object[] { id }, (rs, rowNum) -> BidFactory.생성(rs) );
		} catch (EmptyResultDataAccessException e) {
			return null;
		} catch (Exception e) {
			throw new RepositoryException(e, e.getMessage());
		}
	}

	@Override
	public Bid 조회(final Bid 입찰)
	{
		StringBuilder sbSql =  new StringBuilder("SELECT * FROM Bid WHERE auction_part_id=? AND auction_id=? AND bid_date=? AND bid_amount=?");
		try {
			return this.jdbcTemplate.queryForObject(sbSql.toString(),
								new Object[] {
										입찰.getAuctionPartId(),
										입찰.getAuctionId(),
										입찰.getBidDate(),
										입찰.getBidAmount() },
								(rs, rowNum) -> BidFactory.생성(rs) );
		} catch (EmptyResultDataAccessException e) {
			return null;
		} catch (Exception e) {
			throw new RepositoryException(e, e.getMessage());
		}
	}

	@Override
	public Bid 조회(final long 경매id, final long 낙찰자id, final BigInteger 최고가) {
		StringBuilder sbSql =  new StringBuilder("SELECT * FROM Bid WHERE auction_part_id=? AND auction_id=? AND bid_amount=?");
		try {
			return this.jdbcTemplate.queryForObject(sbSql.toString(),
								new Object[] {낙찰자id, 경매id, 최고가 }, (rs, rowNum) -> BidFactory.생성(rs) );
		} catch (EmptyResultDataAccessException e) {
			return null;
		} catch (Exception e) {
			throw new RepositoryException(e, e.getMessage());
		}
	}

	@Override
	public long 생성(final Bid 입찰) {
		try {
			Map<String, Object> paramMap = new HashMap<>();
			paramMap.put("auction_part_id", 입찰.getAuctionPartId());
			paramMap.put("auction_id", 입찰.getAuctionId());
			paramMap.put("bid_date", 입찰.getBidDate());
			paramMap.put("bid_amount", 입찰.getBidAmount());
			paramMap.put("is_bid", 입찰.getIsBid());

			logger.debug(paramMap.get("auction_part_id") + ", " + paramMap.get("auction_id") + ", " + paramMap.get("bid_amount"));

			this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
					.withTableName("Bid")
					.usingGeneratedKeyColumns("id");

			Number newId = simpleJdbcInsert.executeAndReturnKey(paramMap);
			return newId.longValue();

		} catch (Exception e) {
			throw new RepositoryException(e, e.getMessage());
		}
	}

	@Override
	public int 수정(final Bid 입찰){
		
		StringBuilder sbSql =  new StringBuilder("UPDATE Bid ");
		sbSql.append("SET is_bid=? ");
		sbSql.append("WHERE auction_part_id=? AND auction_id=? AND bid_amount=?");
		try {
			return this.jdbcTemplate.update(sbSql.toString(),
							new Object[] {
									입찰.getIsBid(),
									입찰.getAuctionPartId(),
									입찰.getAuctionId(),
									입찰.getBidAmount()
							});
		} catch (Exception e) {
			throw new RepositoryException(e, e.getMessage());
		}
	}

	@Override
	public int 수정(final long 경매id, final long 낙찰자id, final BigInteger 입찰최고가) {
		StringBuilder sbSql =  new StringBuilder("UPDATE Bid ");
		sbSql.append("SET is_bid=? ");
		sbSql.append("WHERE auction_id=? AND auction_part_id=? AND bid_amount=?");
		try {
			return this.jdbcTemplate.update(sbSql.toString(),
								new Object[] { "Y", 경매id, 낙찰자id, 입찰최고가 });
		} catch (Exception e) {
			throw new RepositoryException(e, e.getMessage());
		}
	}

	@Override
	public int 삭제(final long id) {
		StringBuilder sbSql =  new StringBuilder("DELETE FROM Bid WHERE id=?");
		try {
			return this.jdbcTemplate.update(sbSql.toString(), new Object[] { id });
		} catch (Exception e) {
			throw new RepositoryException(e, e.getMessage());
		}
	}


}
