package com.sas.comp.models;

public class Standing {

	private Integer rank;
	private String team;
	private Integer wins;
	private Integer losses;
	private Integer ties;
	private Integer points;
	private Integer goalsFor;
	private Integer goalsAgainst;
	private Integer goalDifference;
	private Integer shutouts;

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

	public Integer getGoalDifference() {
		return goalDifference;
	}

	public void setGoalDifference(final Integer goalDifference) {
		this.goalDifference = goalDifference;
	}

	public Integer getShutouts() {
		return shutouts;
	}

	public void setShutouts(Integer shutouts) {
		this.shutouts = shutouts;
	}
}
