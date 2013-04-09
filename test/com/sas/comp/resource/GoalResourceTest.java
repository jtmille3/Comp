package com.sas.comp.resource;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.sas.comp.hibernate.model.Goal;

public class GoalResourceTest {

	@Test
	public void testGoalResource() {
		final GoalResource resource = new GoalResource();
		final List<Goal> goals = resource.findAll();
		Assert.assertNotNull(goals);
		Assert.assertTrue(goals.size() > 0);

		final Goal goal = new Goal();
		goal.setGameId(1);
		goal.setPlayerId(1);

		resource.save(goal);
		System.out.println(goal.getId());
		Assert.assertTrue(goal.getId() > 0);

		resource.destroy(goal.getId());

		final Goal exists = resource.find(goal.getId());
		Assert.assertNull(exists);
	}
}
