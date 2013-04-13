package com.sas.comp.models;

public class Player {
	private Integer teamId;
	private Integer playerId;
	private String name;
	private Integer goals;

	public Integer getTeamId() {
		return teamId;
	}

	public void setTeamId(final Integer teamId) {
		this.teamId = teamId;
	}

	public Integer getPlayerId() {
		return playerId;
	}

	public void setPlayerId(final Integer playerId) {
		this.playerId = playerId;
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
}
