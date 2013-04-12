package com.sas.comp.service;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.sas.comp.models.Player;

public class GameServiceTest {

	@Test
	public void testGamePlayers() {
		final GameService service = new GameService();
		final List<Player> players = service.getPlayers(1);
		Assert.assertNotNull(players);
		Assert.assertTrue(players.size() > 0);
	}
}
