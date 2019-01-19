package com.sas.comp.models;

public class GoalieDetailedStats {
	private Integer id;
	private String name;
	private Integer numSeasons;
	private Integer numGames;
	private Integer leagueWinner;
	private Integer playoffWinner;
	private Integer totalShutouts;
	private Integer totalGoalsAgainst;
	private String againstPerSeason;
	private String againstPerGame;
	private String shutoutsPerSeason;
	private String shutoutsPerGame;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getNumSeasons() {
		return numSeasons;
	}
	public void setNumSeasons(Integer numSeasons) {
		this.numSeasons = numSeasons;
	}
	public Integer getNumGames() {
		return numGames;
	}
	public void setNumGames(Integer numGames) {
		this.numGames = numGames;
	}
	public Integer getLeagueWinner() {
		return leagueWinner;
	}
	public void setLeagueWinner(Integer leagueWinner) {
		this.leagueWinner = leagueWinner;
	}
	public Integer getPlayoffWinner() {
		return playoffWinner;
	}
	public void setPlayoffWinner(Integer playoffWinner) {
		this.playoffWinner = playoffWinner;
	}
	public Integer getTotalShutouts() {
		return totalShutouts;
	}
	public void setTotalShutouts(Integer totalShutouts) {
		this.totalShutouts = totalShutouts;
	}
	public Integer getTotalGoalsAgainst() {
		return totalGoalsAgainst;
	}
	public void setTotalGoalsAgainst(Integer totalGoalsAgainst) {
		this.totalGoalsAgainst = totalGoalsAgainst;
	}
	public String getAgainstPerSeason() {
		return againstPerSeason;
	}
	public void setAgainstPerSeason(String againstPerSeason) {
		this.againstPerSeason = againstPerSeason;
	}
	public String getAgainstPerGame() {
		return againstPerGame;
	}
	public void setAgainstPerGame(String againstPerGame) {
		this.againstPerGame = againstPerGame;
	}
	public String getShutoutsPerSeason() {
		return shutoutsPerSeason;
	}
	public void setShutoutsPerSeason(String shutoutsPerSeason) {
		this.shutoutsPerSeason = shutoutsPerSeason;
	}
	public String getShutoutsPerGame() {
		return shutoutsPerGame;
	}
	public void setShutoutsPerGame(String shutoutsPerGame) {
		this.shutoutsPerGame = shutoutsPerGame;
	}
	@Override
	public String toString() {
		return "GoalieDetailedStats [id=" + id + ", name=" + name
				+ ", numSeasons=" + numSeasons + ", numGames=" + numGames
				+ ", leagueWinner=" + leagueWinner + ", playoffWinner="
				+ playoffWinner + ", totalShutouts=" + totalShutouts
				+ ", totalGoalsAgainst=" + totalGoalsAgainst
				+ ", againstPerSeason=" + againstPerSeason
				+ ", againstPerGame=" + againstPerGame + ", shutoutsPerSeason="
				+ shutoutsPerSeason + ", shutoutsPerGame=" + shutoutsPerGame
				+ "]";
	}
}
