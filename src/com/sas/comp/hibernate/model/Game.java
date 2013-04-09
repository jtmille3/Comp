package com.sas.comp.hibernate.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@SuppressWarnings("serial")
@Entity(name = "games")
public class Game implements Serializable {

	@Id
	private Integer id;
	@Column(name = "home_team_id")
	private Integer homeId;
	@Column(name = "away_team_id")
	private Integer awayId;
	private Date date;
	@Column(name = "home_score")
	private Integer homeScore;
	@Column(name = "away_score")
	private Integer awayScore;
	private Boolean playoff;

	public Integer getId() {
		return id;
	}

	public void setId(final Integer id) {
		this.id = id;
	}

	public Integer getHomeId() {
		return homeId;
	}

	public void setHomeId(final Integer homeId) {
		this.homeId = homeId;
	}

	public Integer getAwayId() {
		return awayId;
	}

	public void setAwayId(final Integer awayId) {
		this.awayId = awayId;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(final Date date) {
		this.date = date;
	}

	public Integer getHomeScore() {
		return homeScore;
	}

	public void setHomeScore(final Integer homeScore) {
		this.homeScore = homeScore;
	}

	public Integer getAwayScore() {
		return awayScore;
	}

	public void setAwayScore(final Integer awayScore) {
		this.awayScore = awayScore;
	}

	public Boolean getPlayoff() {
		return playoff;
	}

	public void setPlayoff(final Boolean playoff) {
		this.playoff = playoff;
	}
}
