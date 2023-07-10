package com.sas.comp.models;

public class TeamStats {

	private Integer rank;
	private String season;
	private String name;
	private String captain;
	private Integer gamesPlayed;
	private String goalsFor;
	private String goalsAgainst;
	private String goalDifferential;
	private String shutouts;
	private Integer leagueWinner;
	private Integer playoffWinner;
	private String winpct;
	private String losspct;
	private String tiepct;
	public Integer getRank() {
		return rank;
	}
	public void setRank(Integer rank) {
		this.rank = rank;
	}
	public String getGoalsFor() {
		return goalsFor;
	}
	public void setGoalsFor(String goalsFor) {
		this.goalsFor = goalsFor;
	}
	public String getGoalsAgainst() {
		return goalsAgainst;
	}
	public void setGoalsAgainst(String goalsAgainst) {
		this.goalsAgainst = goalsAgainst;
	}
	public String getSeason() {
		return season;
	}
	public void setSeason(String season) {
		this.season = season;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCaptain() {
		return captain;
	}
	public void setCaptain(String captain) {
		this.captain = captain;
	}
	public Integer getGamesPlayed() {
		return gamesPlayed;
	}
	public void setGamesPlayed(Integer gamesPlayed) {
		this.gamesPlayed = gamesPlayed;
	}
	public String getGoalDifferential() {
		return goalDifferential;
	}
	public void setGoalDifferential(String goalDifferential) {
		this.goalDifferential = goalDifferential;
	}
	public String getShutouts() {
		return shutouts;
	}
	public void setShutouts(String shutouts) {
		this.shutouts = shutouts;
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
	public String getWinpct() {
		return winpct;
	}
	public void setWinpct(String winpct) {
		this.winpct = winpct;
	}
	public String getLosspct() {
		return losspct;
	}
	public void setLosspct(String losspct) {
		this.losspct = losspct;
	}
	public String getTiepct() {
		return tiepct;
	}
	public void setTiepct(String tiepct) {
		this.tiepct = tiepct;
	}
	@Override
	public String toString() {
		return "TeamStats [rank=" + rank + ", season=" + season + ", name=" + name + ", captain=" + captain
				+ ", gamesPlayed=" + gamesPlayed + ", goalsFor=" + goalsFor + ", goalsAgainst=" + goalsAgainst
				+ ", goalDifferential=" + goalDifferential + ", shutouts=" + shutouts + ", leagueWinner=" + leagueWinner
				+ ", playoffWinner=" + playoffWinner + ", winpct=" + winpct + ", losspct=" + losspct + ", tiepct="
				+ tiepct + "]";
	}
}
