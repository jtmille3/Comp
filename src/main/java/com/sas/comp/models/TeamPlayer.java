package com.sas.comp.models;

import java.io.Serializable;

@SuppressWarnings("serial")
public class TeamPlayer extends BaseModel implements Serializable {

	private Integer teamId;
	private Integer playerId;
	private Boolean isGoalie;
	private Boolean isCaptain;
	private Boolean isCoCaptain;

    public TeamPlayer(Team team, Player player, boolean isCaptain, boolean isCoCaptain, boolean isGoalie) {
        this.teamId = team.getId();
        this.playerId = player.getId();
        this.isCaptain = isCaptain;
        this.isCoCaptain = isCoCaptain;
        this.isGoalie = isGoalie;
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

    public Boolean getIsGoalie() {
        return isGoalie;
    }

    public void setIsGoalie(Boolean isGoalie) {
        this.isGoalie = isGoalie;
    }

    public Boolean getIsCaptain() {
        return isCaptain;
    }

    public void setIsCaptain(Boolean isCaptain) {
        this.isCaptain = isCaptain;
    }

    public Boolean getIsCoCaptain() {
        return isCoCaptain;
    }

    public void setIsCoCaptain(Boolean isCoCaptain) {
        this.isCoCaptain = isCoCaptain;
    }
}
