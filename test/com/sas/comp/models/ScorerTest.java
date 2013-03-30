package com.sas.comp.models;

import org.junit.Assert;
import org.junit.Test;

import com.sas.comp.hibernate.Hibernate;

public class ScorerTest {

	@Test
	public void testScorer() {
		final ScorerId id = new ScorerId();
		id.setGameId(6);
		id.setPlayerId(1);

		final Goal goal = Hibernate.getInstance().find(Goal.class, id);
		Assert.assertNotNull(goal.getId());
		Assert.assertNotNull(goal.getGame());
		Assert.assertNotNull(goal.getPlayer());
	}
}
