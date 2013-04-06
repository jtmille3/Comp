package com.sas.comp.service;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.sas.comp.models.Standing;

public class StandingServiceTest {

	@Test
	public void testStandings() {
		final StandingService standingService = new StandingService();
		final List<Standing> standings = standingService.getStandings(5);
		Assert.assertNotNull(standings);
		Assert.assertTrue(standings.size() > 0);
	}
}
