package com.sas.comp.models;

import java.util.List;
import java.util.ArrayList;

public class Competitive {
	private List<Season> seasons;
	private List<Player> playerStatistics;
	private List<Player> goalieStatistics;
    private List<Player> shutoutStatistics;
	private List<Goal> goals;
	private List<Standing> champions = new ArrayList<Standing>();

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

    public void setShutoutStatistics(List<Player> shutoutStatistics) {
        this.shutoutStatistics = shutoutStatistics;
    }

    public List<Player> getShutoutStatistics() {
        return shutoutStatistics;
    }

	public List<Standing> getChampions() {
		return champions;
	}

	public void setChampions(final List<Standing> champions) {
		this.champions = champions;
	}
	
	public void addChampion(final Standing champion) {
		if( champion != null && ! this.champions.contains(champion) ) {
			this.champions.add(champion);
		}
	}
}
