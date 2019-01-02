package com.sas.comp.models;

// Generated Mar 30, 2013 1:36:42 PM by Hibernate Tools 3.4.0.CR1

import java.io.Serializable;

@SuppressWarnings("serial")
public class Team extends BaseModel implements Serializable {
	private Integer seasonId;
	private String name;
    private Boolean leagueWinner;
    private Boolean playoffWinner;

	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public Integer getSeasonId() {
		return seasonId;
	}

	public void setSeasonId(final Integer seasonId) {
		this.seasonId = seasonId;
	}

    public Boolean getLeagueWinner() {
        return leagueWinner;
    }

    public void setLeagueWinner(Boolean leagueWinner) {
        this.leagueWinner = leagueWinner;
    }

    public Boolean getPlayoffWinner() {
        return playoffWinner;
    }

    public void setPlayoffWinner(Boolean playoffWinner) {
        this.playoffWinner = playoffWinner;
    }
}
