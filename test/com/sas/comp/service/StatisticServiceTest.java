package com.sas.comp.service;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.sas.comp.models.GoalieDetailedStats;
import com.sas.comp.models.Player;
import com.sas.comp.models.PlayerDetailedStats;

public class StatisticServiceTest {

	@Test
	public void testPlayerStatistics() {
		final StatisticService statisticService = new StatisticService();
		statisticService.initializePlayerGoalieStatistics();
		final List<Player> statistics = statisticService.getSeasonStatistics(5).get("overall").get("player");
		Assert.assertNotNull(statistics);
		Assert.assertTrue(statistics.size() > 0);
	}

    @Test
    public void testShutoutStatistics() {
        final StatisticService statisticService = new StatisticService();
        final List<Player> statistics = statisticService.getShutoutStatistics();
        Assert.assertNotNull(statistics);
        Assert.assertTrue(statistics.size() > 0);
    }

	@Test
	public void testGoalieStatistics() {
		final StatisticService statisticService = new StatisticService();
		statisticService.initializePlayerGoalieStatistics();
		final List<Player> statistics = statisticService.getSeasonStatistics(5).get("overall").get("goalie");
		Assert.assertNotNull(statistics);
		Assert.assertTrue(statistics.size() > 0);
	}

	@Test
	public void testAllTimePlayerStatistics() {
		final StatisticService statisticService = new StatisticService();
		final List<PlayerDetailedStats> statistics = statisticService.getPlayerDetailedStatsMap().get("overall");
		Assert.assertNotNull(statistics);
		Assert.assertTrue(statistics.size() > 0);
	}

	@Test
	public void testAllTimeGoalieStatistics() {
		final StatisticService statisticService = new StatisticService();
		final List<GoalieDetailedStats> statistics = statisticService.getGoalieDetailedStatsMap().get("overall");
		Assert.assertNotNull(statistics);
		Assert.assertTrue(statistics.size() > 0);
	}
}
