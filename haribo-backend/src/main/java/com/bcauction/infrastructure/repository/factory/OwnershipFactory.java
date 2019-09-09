package com.bcauction.infrastructure.repository.factory;

import com.bcauction.domain.Ownership;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class OwnershipFactory
{
	public static Ownership 생성(ResultSet rs) throws SQLException
	{
		if(rs == null) return null;
		Ownership 소유권 = new Ownership();

		소유권.setId(rs.getLong("id"));
		소유권.setOwnerId(rs.getLong("owner_id"));
		소유권.setWorkId(rs.getLong("work_id"));
		if(rs.getString("own_start_date") != null)
			소유권.setOwnStartDate(LocalDateTime.parse(rs.getString("own_start_date")));
		if(rs.getString("own_end_date") != null)
			소유권.setOwnEndDate(LocalDateTime.parse(rs.getString("own_end_date")));

		return 소유권;
	}
}
