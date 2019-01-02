package com.sas.comp.mysql;

import java.sql.Connection;
import java.sql.ResultSet;

import org.junit.Assert;
import org.junit.Test;

public class DatabaseTest {

	@Test
	public void connectionTest() throws Exception {
		Database.doVoidTransaction("SELECT * FROM seasons", (pstmt) -> {
            ResultSet rs = pstmt.executeQuery();
            Assert.assertTrue(rs.next());
        });
	}
}
