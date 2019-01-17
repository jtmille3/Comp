package com.sas.comp.models;

public class PlayerDetailedStats {
	private Integer id;
	private String name;
	private Integer numSeasons;
	private Integer numGames;
	private Integer leagueWinner;
	private Integer playoffWinner;
	private Integer totalGoals = 0;  // default data that views will not populate with zero.
	private String avgGoalsSeason = "0.0";
	private Integer maxGoalsInSeason = 0;
	private Integer maxGoalsInGame = 0;
	private Integer win;
	private Integer loss;
	private Integer tie;
	private String winpct;
	private String losspct;
	private String tiepct;
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
	public Integer getTotalGoals() {
		return totalGoals;
	}
	public void setTotalGoals(Integer totalGoals) {
		this.totalGoals = totalGoals;
	}
	public String getAvgGoalsSeason() {
		return avgGoalsSeason;
	}
	public void setAvgGoalsSeason(String avgGoalsSeason) {
		this.avgGoalsSeason = avgGoalsSeason;
	}
	public Integer getMaxGoalsInSeason() {
		return maxGoalsInSeason;
	}
	public void setMaxGoalsInSeason(Integer maxGoalsInSeason) {
		this.maxGoalsInSeason = maxGoalsInSeason;
	}
	public Integer getMaxGoalsInGame() {
		return maxGoalsInGame;
	}
	public void setMaxGoalsInGame(Integer maxGoalsInGame) {
		this.maxGoalsInGame = maxGoalsInGame;
	}
	public Integer getWin() {
		return win;
	}
	public void setWin(Integer win) {
		this.win = win;
	}
	public Integer getLoss() {
		return loss;
	}
	public void setLoss(Integer loss) {
		this.loss = loss;
	}
	public Integer getTie() {
		return tie;
	}
	public void setTie(Integer tie) {
		this.tie = tie;
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
		return "PlayerDetailedStats [id=" + id + ", name=" + name
				+ ", numSeasons=" + numSeasons + ", numGames=" + numGames
				+ ", leagueWinner=" + leagueWinner + ", playoffWinner="
				+ playoffWinner + ", totalGoals=" + totalGoals
				+ ", avgGoalsSeason=" + avgGoalsSeason + ", maxGoalsInSeason="
				+ maxGoalsInSeason + ", maxGoalsInGame=" + maxGoalsInGame
				+ ", win=" + win + ", loss=" + loss + ", tie=" + tie
				+ ", winpct=" + winpct + ", losspct=" + losspct + ", tiepct="
				+ tiepct + "]";
	}
}
