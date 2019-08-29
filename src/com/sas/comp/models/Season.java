package com.sas.comp.models;

import java.util.List;
import java.util.Map;

public class Season extends BaseModel {

	private String name;
	private List<Standing> standings;
	private List<Game> playoffSchedule;
	private List<Game> leagueSchedule;
	private List<Player> playerStatistics;
	private List<Player> goalieStatistics;
	private Map<String,Map<String,List<Player>>> seasonStatistics;
	
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

	public Map<String, Map<String,List<Player>>> getSeasonStatistics() {
		return seasonStatistics;
	}

	public void setSeasonStatistics(final Map<String, Map<String,List<Player>>> seasonStatistics) {
		this.seasonStatistics = seasonStatistics;
	}

}
