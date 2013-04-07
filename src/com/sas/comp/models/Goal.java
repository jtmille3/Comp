package com.sas.comp.models;

public class Goal {

	private Integer gameId;
	private Integer playerId;
	private Integer teamId;
	private String player;
	private String team;

	public Integer getGameId() {
		return gameId;
	}

	public void setGameId(final Integer gameId) {
		this.gameId = gameId;
	}

	public Integer getPlayerId() {
		return playerId;
	}

	public void setPlayerId(final Integer playerId) {
		this.playerId = playerId;
	}

	public Integer getTeamId() {
		return teamId;
	}

	public void setTeamId(final Integer teamId) {
		this.teamId = teamId;
	}

	public String getPlayer() {
		return player;
	}

	public void setPlayer(final String player) {
		this.player = player;
	}

	public String getTeam() {
		return team;
	}

	public void setTeam(final String team) {
		this.team = team;
	}

}
