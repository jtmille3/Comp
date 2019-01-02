package com.sas.comp.service;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.sas.comp.models.Goal;

public class GoalServiceTest {

	@Test
	public void testGoals() {
		final GoalService service = new GoalService();
		final List<Goal> goals = service.getGoals();
		Assert.assertNotNull(goals);
		Assert.assertTrue(goals.size() > 0);
	}
}
