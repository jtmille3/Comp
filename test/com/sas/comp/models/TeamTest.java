package com.sas.comp.models;

import org.junit.Assert;
import org.junit.Test;

import com.sas.comp.hibernate.Hibernate;

public class TeamTest {

	@Test
	public void testTeam() {
		final Team team = Hibernate.getInstance().find(Team.class, 1);
		Assert.assertNotNull(team);
		Assert.assertTrue(team.getId() == 1);
		Assert.assertNotNull(team.getName());
		Assert.assertNotNull(team.getSeason());
		Assert.assertTrue(team.getTeamPlayers().size() > 0);
		Assert.assertTrue(team.getAwayGames().size() > 0);
		Assert.assertTrue(team.getHomeGames().size() > 0);
	}
}
