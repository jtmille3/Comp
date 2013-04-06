package com.sas.comp.models;

import java.util.List;

public class Season {

	private Integer id;
	private String name;
	private List<Schedule> playoffSchedule;
	private List<Schedule> leagueSchedule;

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

	public void setLeagueSchedule(final List<Schedule> leagueSchedule) {
		this.leagueSchedule = leagueSchedule;
	}

	public List<Schedule> getLeagueSchedule() {
		return leagueSchedule;
	}

	public List<Schedule> getPlayoffSchedule() {
		return playoffSchedule;
	}

	public void setPlayoffSchedule(final List<Schedule> playoffSchedule) {
		this.playoffSchedule = playoffSchedule;
	}
}
