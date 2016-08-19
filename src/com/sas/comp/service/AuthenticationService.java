package com.sas.comp.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.sas.comp.mysql.Database;
import com.sun.org.apache.xpath.internal.operations.Bool;

public class AuthenticationService {

	public Boolean authenticate(final String password) {
        Boolean authenticated = Database.doReturnTransaction(Boolean.class, "SELECT * FROM users WHERE username=? and password=?", (pstmt) -> {
            pstmt.setString(1, "admin");
            pstmt.setString(2, password);

            final ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return true;
            }
            return false;
        });
		return authenticated;
	}
}
