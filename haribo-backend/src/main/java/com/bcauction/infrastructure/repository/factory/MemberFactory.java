package com.bcauction.infrastructure.repository.factory;

import com.bcauction.domain.Member;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MemberFactory {

    public static Member 생성(ResultSet rs) throws SQLException {
        if (rs == null) return null;
        Member 회원 = new Member();
        회원.setId(rs.getLong("id"));
        회원.setUsername(rs.getString("username"));
        회원.setEmail(rs.getString("email"));
        회원.setPassword(rs.getString("password"));
        회원.setRegistrationDate(rs.getTimestamp("registration_date").toLocalDateTime());

        return 회원;
    }
}
