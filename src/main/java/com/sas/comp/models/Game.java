package com.sas.comp.models;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Game extends BaseModel {
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm");

    @JsonIgnore
    private Integer seasonId;
	private String home;
	private Integer homeId;
	private String away;
	private Integer awayId;
	@JsonIgnore
	private Date date;
	private Integer homeScore;
	private Integer awayScore;

    private Boolean playoff;

	public String getHome() {
		return home;
	}

	public void setHome(final String home) {
		this.home = home;
	}

	public String getAway() {
		return away;
	}

	public void setAway(final String away) {
		this.away = away;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(final Date date) {
		this.date = date;
	}

	public String getPlayed() {
		return sdf.format(getDate());
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

	public Integer getHomeScore() {
		if (homeScore == null)
			return 0;
		return homeScore;
	}

	public void setHomeScore(final Integer homeScore) {
		this.homeScore = homeScore;
	}

	public Integer getAwayScore() {
		if (awayScore == null)
			return 0;
		return awayScore;
	}

	public void setAwayScore(final Integer awayScore) {
		this.awayScore = awayScore;
	}

	public Boolean getAvailable() {
		return awayScore != null && homeScore != null;
	}

	public String getScore() {
		return getHomeScore() + " - " + getAwayScore();
	}

    public Integer getSeasonId() {
        return seasonId;
    }

    public void setSeasonId(final Integer seasonId) {
        this.seasonId = seasonId;
    }

    public Boolean getPlayoff() {
        return playoff;
    }

    public void setPlayoff(Boolean playoff) {
        this.playoff = playoff;
    }
}
