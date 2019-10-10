package com.bcauction.infrastructure.repository.factory;

import com.bcauction.domain.Auction;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AuctionFactory
{
	public static Auction 생성(ResultSet rs) throws SQLException
	{
		if(rs == null) return null;
		Auction 경매 = new Auction();

		경매.setId(rs.getInt("id"));
		경매.setAuctionCreatorId(rs.getLong("auction_creator_id"));
		경매.setAuctionId(rs.getLong("auction_id"));
		경매.setCreateTime(rs.getTimestamp("create_time").toLocalDateTime());
		경매.setStartTime(rs.getTimestamp("start_time").toLocalDateTime());
		경매.setEndTime(rs.getTimestamp("end_time").toLocalDateTime());
		경매.setIsValid(rs.getString("is_valid"));
		경매.setLowestPrice(rs.getBigDecimal("lowest_price").toBigInteger());
		경매.setTxsAddress(rs.getString("txs_address"));

		return 경매;
	}
}
