package com.sas.comp.mysql;

import java.sql.Connection;

import org.junit.Assert;
import org.junit.Test;

public class DatabaseTest {

	@Test
	public void connectionTest() throws Exception {
		final Connection conn = Database.getConnection();
		Assert.assertNotNull(conn);
	}
}
