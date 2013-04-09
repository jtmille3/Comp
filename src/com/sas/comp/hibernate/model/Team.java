package com.sas.comp.hibernate.model;

// Generated Mar 30, 2013 1:36:42 PM by Hibernate Tools 3.4.0.CR1

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@SuppressWarnings("serial")
@Entity(name = "teams")
public class Team implements Serializable {

	@Id
	private Integer id;
	@Column(name = "season_id")
	private Integer seasonId;
	private String name;

	public Integer getId() {
		return this.id;
	}

	public void setId(final Integer id) {
		this.id = id;
	}

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
