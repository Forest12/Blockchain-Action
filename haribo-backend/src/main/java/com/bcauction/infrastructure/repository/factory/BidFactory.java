package com.bcauction.infrastructure.repository.factory;

import com.bcauction.domain.Bid;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BidFactory
{
	public static Bid 생성(ResultSet rs) throws SQLException
	{
		if (rs == null) return null;
		Bid 입찰 = new Bid();
		입찰.setId(rs.getLong("id"));
		입찰.setAuctionId(rs.getLong("auction_id"));
		입찰.setAuctionPartId(rs.getLong("auction_part_id"));
		입찰.setBidDate(rs.getTimestamp("bid_date").toLocalDateTime());
		입찰.setBidAmount(rs.getBigDecimal("bid_amount"));
		입찰.setIsBid(rs.getString("is_bid"));

		return 입찰;
	}
}
