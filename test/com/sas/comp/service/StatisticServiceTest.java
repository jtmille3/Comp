package com.sas.comp.service;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.sas.comp.models.Player;

public class StatisticServiceTest {

	@Test
	public void testPlayerStatistics() {
		final StatisticService statisticService = new StatisticService();
		final List<Player> statistics = statisticService.getPlayerStatistics(5);
		Assert.assertNotNull(statistics);
		Assert.assertTrue(statistics.size() > 0);
	}

	@Test
	public void testGoalieStatistics() {
		final StatisticService statisticService = new StatisticService();
		final List<Player> statistics = statisticService.getGoalieStatistics(5);
		Assert.assertNotNull(statistics);
		Assert.assertTrue(statistics.size() > 0);
	}

	@Test
	public void testAllTimePlayerStatistics() {
		final StatisticService statisticService = new StatisticService();
		final List<Player> statistics = statisticService.getPlayerStatistics();
		Assert.assertNotNull(statistics);
		Assert.assertTrue(statistics.size() > 0);
	}

	@Test
	public void testAllTimeGoalieStatistics() {
		final StatisticService statisticService = new StatisticService();
		final List<Player> statistics = statisticService.getGoalieStatistics();
		Assert.assertNotNull(statistics);
		Assert.assertTrue(statistics.size() > 0);
	}
}
