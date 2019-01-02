package com.sas.comp.service;

import java.sql.ResultSet;

import com.sas.comp.mysql.Database;

public class AuthenticationService {

	public Boolean authenticate(final String password) {
        return Database.doReturnTransaction(Boolean.class, "SELECT * FROM users WHERE username=? and password=?", (pstmt) -> {
            pstmt.setString(1, "admin");
            pstmt.setString(2, password);

            final ResultSet rs = pstmt.executeQuery();
            return rs.next();
        });
	}
}
