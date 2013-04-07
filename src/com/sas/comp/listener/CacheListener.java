package com.sas.comp.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import com.sas.comp.models.Competitive;
import com.sas.comp.models.Season;
import com.sas.comp.service.GoalService;
import com.sas.comp.service.ScheduleService;
import com.sas.comp.service.SeasonService;
import com.sas.comp.service.StandingService;
import com.sas.comp.service.StatisticService;

@WebListener
public class CacheListener implements ServletContextListener {

	@Override
	public void contextDestroyed(final ServletContextEvent arg0) {
	}

	private final SeasonService seasonService = new SeasonService();
	private final ScheduleService scheduleService = new ScheduleService();
	private final StandingService standingService = new StandingService();
	private final StatisticService statisticService = new StatisticService();
	private final GoalService goalService = new GoalService();

	@Override
	public void contextInitialized(final ServletContextEvent arg0) {
		final long startTime = System.currentTimeMillis();
		final Competitive competitive = new Competitive();

		competitive.setSeasons(this.seasonService.getSeasons());
		competitive.setPlayerStatistics(this.statisticService.getPlayerStatistics());
		competitive.setGoalieStatistics(this.statisticService.getGoalieStatistics());
		competitive.setGoals(this.goalService.getGoals());

		for (final Season season : competitive.getSeasons()) {
			season.setStandings(this.standingService.getStandings(season.getId()));
			season.setLeagueSchedule(this.scheduleService.getLeagueSchedule(season.getId()));
			season.setPlayoffSchedule(this.scheduleService.getPlayoffSchedule(season.getId()));
			season.setPlayerStatistics(this.statisticService.getPlayerStatistics(season.getId()));
			season.setGoalieStatistics(this.statisticService.getGoalieStatistics(season.getId()));
		}

		Cache.getCache().setCompetitive(competitive);
		final long elapsedTime = System.currentTimeMillis() - startTime;
		System.out.println(elapsedTime + "ms");
	}

}
