package com.sas.comp.models;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.codehaus.jackson.annotate.JsonIgnore;

public class Schedule {
	private static SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yy hh:mm");

	private String home;
	private Integer homeId;
	private String away;
	private Integer awayId;
	@JsonIgnore
	private Date date;
	private String result;

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

	public String getResult() {
		return result;
	}

	public void setResult(final String result) {
		this.result = result;
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

	public void setAwayId(Integer awayId) {
		this.awayId = awayId;
	}
}
