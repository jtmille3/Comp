package com.sas.comp.service;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.sas.comp.models.Schedule;

public class ScheduleServiceTest {

	@Test
	public void testRegularSeason() {
		final ScheduleService service = new ScheduleService();
		final List<Schedule> schedules = service.getLeagueSchedule(4);
		Assert.assertNotNull(schedules);
		Assert.assertTrue(schedules.size() > 0);
	}

	@Test
	public void testPlayoffs() {
		final ScheduleService service = new ScheduleService();
		final List<Schedule> schedules = service.getPlayoffSchedule(4);
		Assert.assertNotNull(schedules);
		Assert.assertTrue(schedules.size() > 0);
	}
}
