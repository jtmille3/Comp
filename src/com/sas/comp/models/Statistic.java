package com.sas.comp.models;

public class Statistic {

	private Integer rank;
	private String team;
	private String player;
	private Integer goals;
	private Integer shutouts;
	private Boolean goalie;
	private Integer goalsAgainst;
	private Integer leagueWinner;
	private Integer playoffWinner;

	public String getTeam() {
		return team;
	}

	public void setTeam(final String team) {
		this.team = team;
	}

	public String getPlayer() {
		return player;
	}

	public void setPlayer(final String player) {
		this.player = player;
	}

	public Integer getGoals() {
		return goals;
	}

	public void setGoals(final Integer goals) {
		this.goals = goals;
	}

	public Integer getShutouts() {
		return shutouts;
	}

	public void setShutouts(final Integer shutouts) {
		this.shutouts = shutouts;
	}

	public Boolean getGoalie() {
		return goalie;
	}

	public void setGoalie(final Boolean goalie) {
		this.goalie = goalie;
	}

	public Integer getGoalsAgainst() {
		return goalsAgainst;
	}

	public void setGoalsAgainst(final Integer goalsAgainst) {
		this.goalsAgainst = goalsAgainst;
	}

	public Integer getLeagueWinner() {
		return leagueWinner;
	}

	public void setLeagueWinner(final Integer leagueWinner) {
		this.leagueWinner = leagueWinner;
	}

	public Integer getPlayoffWinner() {
		return playoffWinner;
	}

	public void setPlayoffWinner(final Integer playoffWinner) {
		this.playoffWinner = playoffWinner;
	}

	public Integer getRank() {
		return rank;
	}

	public void setRank(final Integer rank) {
		this.rank = rank;
	}

}
