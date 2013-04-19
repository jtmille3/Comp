package com.sas.comp.service;

import com.sas.comp.models.Competitive;
import com.sas.comp.models.Season;

public class CompetitiveService {

	private final SeasonService seasonService = new SeasonService();
	private final GameService gameService = new GameService();
	private final StandingService standingService = new StandingService();
	private final StatisticService statisticService = new StatisticService();
	private final GoalService goalService = new GoalService();

	public Competitive getCompetition() {
		final Competitive competitive = new Competitive();

		competitive.setSeasons(this.seasonService.getSeasons());
		competitive.setPlayerStatistics(this.statisticService.getPlayerStatistics());
		competitive.setGoalieStatistics(this.statisticService.getGoalieStatistics());
		competitive.setGoals(this.goalService.getGoals());

		for (final Season season : competitive.getSeasons()) {
			season.setStandings(this.standingService.getStandings(season.getId()));
			season.setLeagueSchedule(this.gameService.getLeagueSchedule(season.getId()));
			season.setPlayoffSchedule(this.gameService.getPlayoffSchedule(season.getId()));
			season.setPlayerStatistics(this.statisticService.getPlayerStatistics(season.getId()));
			season.setGoalieStatistics(this.statisticService.getGoalieStatistics(season.getId()));
		}

		return competitive;
	}
}