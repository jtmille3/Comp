package com.sas.comp.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.sas.comp.mysql.Database;

public class AuthenticationService {

	public Boolean authenticate(final String password) {
		Boolean authenticated = false;

		try {
			final Connection conn = Database.getConnection();
			final PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM users WHERE username=? and password=?");
			pstmt.setString(1, "admin");
			pstmt.setString(2, password);

			final ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				authenticated = true;
			}

			rs.close();
			pstmt.close();
			conn.close();
		} catch (final Exception e) {
			e.printStackTrace();
		}

		return authenticated;
	}
}
