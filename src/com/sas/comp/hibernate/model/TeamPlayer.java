package com.sas.comp.hibernate.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@SuppressWarnings("serial")
@Entity(name = "team_player")
public class TeamPlayer implements Serializable {

	@Id
	private Integer id;
	@Column(name = "team_id")
	private Integer teamId;
	@Column(name = "player_id")
	private Integer playerId;
	private Byte isGoalie;
	private Byte isCaptain;
	private Byte isCoCaptain;

	public Integer getId() {
		return id;
	}

	public void setId(final Integer id) {
		this.id = id;
	}

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

	public Byte getIsGoalie() {
		return isGoalie;
	}

	public void setIsGoalie(final Byte isGoalie) {
		this.isGoalie = isGoalie;
	}

	public Byte getIsCaptain() {
		return isCaptain;
	}

	public void setIsCaptain(final Byte isCaptain) {
		this.isCaptain = isCaptain;
	}

	public Byte getIsCoCaptain() {
		return isCoCaptain;
	}

	public void setIsCoCaptain(final Byte isCoCaptain) {
		this.isCoCaptain = isCoCaptain;
	}

}
