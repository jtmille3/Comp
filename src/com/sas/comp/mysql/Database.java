package com.sas.comp.mysql;

import java.sql.Connection;
import java.sql.DriverManager;

public class Database {

	public static Connection getConnection() throws Exception {
		Class.forName("com.mysql.jdbc.Driver");
		return DriverManager.getConnection("jdbc:mysql://pip.na.sas.com:3306/soccer", "soccer", "i<3soccer");
        //return DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/soccer", "soccer", "i<3soccer");
	}
}
