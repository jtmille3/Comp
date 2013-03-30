package com.sas.comp.models;

import org.junit.Assert;
import org.junit.Test;

import com.sas.comp.hibernate.Hibernate;

public class SeasonTest {

	@Test
	public void testSeason() {
		final Season season = Hibernate.getInstance().find(Season.class, 1);
		Assert.assertTrue(season != null);
		Assert.assertNotNull(season.getId());
		Assert.assertNotNull(season.getName());
		Assert.assertTrue(season.getTeams().size() > 0);
	}
}
