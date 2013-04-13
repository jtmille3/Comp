package com.sas.comp.models;

import java.util.List;

public class Season {

	private Integer id;
	private String name;
	private List<Standing> standings;
	private List<Game> playoffSchedule;
	private List<Game> leagueSchedule;
	private List<Player> playerStatistics;
	private List<Player> goalieStatistics;

	public Integer getId() {
		return this.id;
	}

	public void setId(final Integer id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public void setLeagueSchedule(final List<Game> leagueSchedule) {
		this.leagueSchedule = leagueSchedule;
	}

	public List<Game> getLeagueSchedule() {
		return leagueSchedule;
	}

	public List<Game> getPlayoffSchedule() {
		return playoffSchedule;
	}

	public void setPlayoffSchedule(final List<Game> playoffSchedule) {
		this.playoffSchedule = playoffSchedule;
	}

	public List<Standing> getStandings() {
		return standings;
	}

	public void setStandings(final List<Standing> standings) {
		this.standings = standings;
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
}
