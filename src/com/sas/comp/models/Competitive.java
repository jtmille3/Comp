package com.sas.comp.models;

import java.util.List;

public class Competitive {
	private List<Season> seasons;
	private List<Player> playerStatistics;
	private List<Player> goalieStatistics;
	private List<Goal> goals;

	public List<Season> getSeasons() {
		return seasons;
	}

	public void setSeasons(final List<Season> seasons) {
		this.seasons = seasons;
	}

	public List<Player> getPlayerStatistics() {
		return playerStatistics;
	}

	public void setPlayerStatistics(final List<Player> playerStatistics) {
		this.playerStatistics = playerStatistics;
	}

	public List<Player> getGoalieStatistics() {
		return goalieStatistics;
	}

	public void setGoalieStatistics(final List<Player> goalieStatistics) {
		this.goalieStatistics = goalieStatistics;
	}

	public List<Goal> getGoals() {
		return goals;
	}

	public void setGoals(final List<Goal> goals) {
		this.goals = goals;
	}

}
