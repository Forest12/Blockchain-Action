package com.bcauction.infrastructure.repository.factory;

import com.bcauction.domain.DigitalWork;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DigitalWorkFactory
{
	public static DigitalWork 생성(ResultSet rs) throws SQLException
	{
		if (rs == null) return null;
		DigitalWork 작품 = new DigitalWork();
		작품.setId(rs.getLong("id"));
		작품.setMemberId(rs.getLong("member_id"));
		작품.setWorkName(rs.getString("work_name"));
		작품.setDescription(rs.getString("description"));
		작품.setIsValid(rs.getString("is_valid"));
		작품.setIsDisclosure(rs.getString("is_disclosure"));
		작품.setWork_url(rs.getString("work_url"));
		// System.out.println("**************************아노오냐!!!!!!!!!!!!!!***********************"+rs.getString("work_name"));
		return 작품;
	}
}
