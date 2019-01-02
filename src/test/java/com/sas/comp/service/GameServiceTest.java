package com.sas.comp.service;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.sas.comp.models.Game;
import com.sas.comp.models.Player;

public class GameServiceTest {

	private final GameService service = new GameService();

	@Test
	public void testGamePlayers() {
		final List<Player> players = service.getPlayers(1);
		Assert.assertNotNull(players);
		Assert.assertTrue(players.size() > 0);
	}

	@Test
	public void testRegularSeason() {
		final List<Game> schedules = service.getLeagueSchedule(4);
		Assert.assertNotNull(schedules);
		Assert.assertTrue(schedules.size() > 0);
	}

	@Test
	public void testPlayoffs() {
		final List<Game> schedules = service.getPlayoffSchedule(4);
		Assert.assertNotNull(schedules);
		Assert.assertTrue(schedules.size() > 0);
	}
}
