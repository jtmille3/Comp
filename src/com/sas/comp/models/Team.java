package com.sas.comp.models;

// Generated Mar 30, 2013 1:36:42 PM by Hibernate Tools 3.4.0.CR1

import org.codehaus.jackson.annotate.JsonIgnore;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

/**
 * Teams generated by hbm2java
 */
@SuppressWarnings("serial")
@Entity(name = "teams")
public class Team implements Serializable {


	@Id
	private Integer id;
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "season_id")
	private Season season;
	private String name;
	@JsonIgnore
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "awayTeam")
	private Set<Game> awayGames = new HashSet<Game>(0);
	@JsonIgnore
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "homeTeam")
	private Set<Game> homeGames = new HashSet<Game>(0);
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "team")
	private Set<TeamPlayer> teamPlayers = new HashSet<TeamPlayer>(0);

	public Integer getId() {
		return this.id;
	}

	public void setId(final Integer id) {
		this.id = id;
	}

	public Season getSeason() {
		return this.season;
	}

	public void setSeason(final Season season) {
		this.season = season;
	}

	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public Set<Game> getAwayGames() {
		return this.awayGames;
	}

	public void setAwayGames(final Set<Game> awayGames) {
		this.awayGames = awayGames;
	}

	public Set<Game> getHomeGames() {
		return this.homeGames;
	}

	public void setHomeGames(final Set<Game> homeGames) {
		this.homeGames = homeGames;
	}

	public Set<TeamPlayer> getTeamPlayers() {
		return this.teamPlayers;
	}

	public void setTeamPlayers(final Set<TeamPlayer> teamPlayers) {
		this.teamPlayers = teamPlayers;
	}

}