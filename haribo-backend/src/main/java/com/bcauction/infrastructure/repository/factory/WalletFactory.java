package com.bcauction.infrastructure.repository.factory;

import com.bcauction.domain.Wallet;

import java.sql.ResultSet;
import java.sql.SQLException;

public class WalletFactory
{
	public static Wallet 생성(ResultSet rs) throws SQLException
	{
		if (rs == null) return null;
		Wallet 지갑 = new Wallet();
		지갑.setId(rs.getLong("id"));
		지갑.setOwnerId(rs.getLong("owner_id"));
		지갑.setAddress(rs.getString("address"));
		지갑.setBalance(rs.getBigDecimal("balance"));
		지갑.setChargeNum(rs.getInt("charge_num"));

		return 지갑;
	}
}
