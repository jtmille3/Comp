package com.sas.comp.models;

import java.util.List;

public class Competitive {
	private List<Season> seasons;
	private List<Statistic> playerStatistics;
	private List<Statistic> goalieStatistics;
	private List<Goal> goals;

	public List<Season> getSeasons() {
		return seasons;
	}

	public void setSeasons(final List<Season> seasons) {
		this.seasons = seasons;
	}

	public List<Statistic> getPlayerStatistics() {
		return playerStatistics;
	}

	public void setPlayerStatistics(final List<Statistic> playerStatistics) {
		this.playerStatistics = playerStatistics;
	}

	public List<Statistic> getGoalieStatistics() {
		return goalieStatistics;
	}

	public void setGoalieStatistics(final List<Statistic> goalieStatistics) {
		this.goalieStatistics = goalieStatistics;
	}

	public List<Goal> getGoals() {
		return goals;
	}

	public void setGoals(final List<Goal> goals) {
		this.goals = goals;
	}

}
