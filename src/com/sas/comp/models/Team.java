package com.sas.comp.models;

// Generated Mar 30, 2013 1:36:42 PM by Hibernate Tools 3.4.0.CR1

import java.io.Serializable;

@SuppressWarnings("serial")
public class Team extends BaseModel implements Serializable {
	private Integer seasonId;
	private String name;

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
}
