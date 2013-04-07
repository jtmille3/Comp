package com.sas.comp.models;

import java.util.List;

public class Competitive {
	private List<Season> seasons;
	private List<Statistic> playerStatistics;
	private List<Statistic> goalieStatistics;

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

}
