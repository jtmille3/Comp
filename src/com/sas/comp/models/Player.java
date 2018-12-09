package com.sas.comp.models;

public class Player extends BaseModel {

	private Integer rank;
	private String team;
	private Integer teamId;
	private String name;
	private Integer goals;
	private Integer shutouts;
	private Boolean goalie;
	private Integer goalsAgainst;
	private Integer leagueWinner;
	private Integer playoffWinner;
	private Boolean coCaptain;
	private Boolean captain;
	private Integer seasonsPlayed;
	private String goalsPerSeason;

	public String getTeam() {
		return team;
	}

	public void setTeam(final String team) {
		this.team = team;
	}

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
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

	public Integer getTeamId() {
		return teamId;
	}

	public void setTeamId(final Integer teamId) {
		this.teamId = teamId;
	}

	public Boolean getCoCaptain() {
		return coCaptain;
	}

	public void setCoCaptain(final Boolean coCaptain) {
		this.coCaptain = coCaptain;
	}

	public Boolean getCaptain() {
		return captain;
	}

	public void setCaptain(final Boolean captain) {
		this.captain = captain;
	}

	public Integer getSeasonsPlayed() {
		return seasonsPlayed;
	}

	public void setSeasonsPlayed(final Integer seasonsPlayed) {
		this.seasonsPlayed = seasonsPlayed;
	}

	public String getGoalsPerSeason() {
		return goalsPerSeason;
	}

	public void setGoalsPerSeason(final String goalsPerSeason) {
		this.goalsPerSeason = goalsPerSeason;
	}

}
