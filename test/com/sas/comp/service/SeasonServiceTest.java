package com.sas.comp.service;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.sas.comp.models.Season;

public class SeasonServiceTest {

	@Test
	public void testSeasons() {
		final SeasonService service = new SeasonService();
		final List<Season> seasons = service.getSeasons();
		Assert.assertNotNull(seasons);
		Assert.assertTrue(seasons.size() > 0);
	}
}
