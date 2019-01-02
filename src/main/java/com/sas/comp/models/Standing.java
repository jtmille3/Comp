package com.sas.comp.models;

public class Standing {

	private Integer rank;
	private String team;
	private Integer teamId;
	private Integer wins;
	private Integer losses;
	private Integer ties;
	private Integer points;
	private Integer goalsFor;
	private Integer goalsAgainst;
	private Integer goalDifferential;
	private Integer shutouts;
	private Integer leagueWinner;
	private Integer playoffWinner;
	private String season;

	public Integer getRank() {
		return rank;
	}

	public void setRank(final Integer rank) {
		this.rank = rank;
	}

	public String getTeam() {
		return team;
	}

	public void setTeam(final String team) {
		this.team = team;
	}

	public Integer getWins() {
		return wins;
	}

	public void setWins(final Integer wins) {
		this.wins = wins;
	}

	public Integer getLosses() {
		return losses;
	}

	public void setLosses(final Integer losses) {
		this.losses = losses;
	}

	public Integer getTies() {
		return ties;
	}

	public void setTies(final Integer ties) {
		this.ties = ties;
	}

	public Integer getPoints() {
		return points;
	}

	public void setPoints(final Integer points) {
		this.points = points;
	}

	public Integer getGoalsFor() {
		return goalsFor;
	}

	public void setGoalsFor(final Integer goalsFor) {
		this.goalsFor = goalsFor;
	}

	public Integer getGoalsAgainst() {
		return goalsAgainst;
	}

	public void setGoalsAgainst(final Integer goalsAgainst) {
		this.goalsAgainst = goalsAgainst;
	}

	public Integer getShutouts() {
		return shutouts;
	}

	public void setShutouts(final Integer shutouts) {
		this.shutouts = shutouts;
	}

	public Integer getTeamId() {
		return teamId;
	}

	public void setTeamId(final Integer teamId) {
		this.teamId = teamId;
	}

	public Integer getGoalDifferential() {
		return goalDifferential;
	}

	public void setGoalDifferential(final Integer goalDifferential) {
		this.goalDifferential = goalDifferential;
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

	public String getSeason() {
		return season;
	}

	public void setSeason(final String season) {
		this.season = season;
	}

	public Integer getGamesPlayed() {
		return new Integer(this.wins + this.losses + this.ties);
	}

}
