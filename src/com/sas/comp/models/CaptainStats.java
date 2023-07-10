package com.sas.comp.models;

public class CaptainStats {

	private Integer rank;
	private String name;
	private Integer seasons;
	private String goalsFor;
	private String goalsAgainst;
	private String goalDifferential;
	private String shutouts;
	private Integer leagueWinner;
	private Integer playoffWinner;
	private String lwpct;
	private String pwpct;
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getSeasons() {
		return seasons;
	}
	public void setSeasons(Integer seasons) {
		this.seasons = seasons;
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
	public String getLwpct() {
		return lwpct;
	}
	public void setLwpct(String lwpct) {
		this.lwpct = lwpct;
	}
	public String getPwpct() {
		return pwpct;
	}
	public void setPwpct(String pwpct) {
		this.pwpct = pwpct;
	}
	@Override
	public String toString() {
		return "CaptainStats [rank=" + rank + ", name=" + name + ", seasons=" + seasons + ", goalsFor=" + goalsFor
				+ ", goalsAgainst=" + goalsAgainst + ", goalDifferential=" + goalDifferential + ", shutouts=" + shutouts
				+ ", leagueWinner=" + leagueWinner + ", playoffWinner=" + playoffWinner + ", lwpct=" + lwpct
				+ ", pwpct=" + pwpct + ", winpct=" + winpct + ", losspct=" + losspct + ", tiepct=" + tiepct + "]";
	}
}
