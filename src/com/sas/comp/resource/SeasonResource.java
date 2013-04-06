package com.sas.comp.resource;

import java.util.Collection;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import com.sas.comp.models.Season;
import com.sas.comp.service.ScheduleService;
import com.sas.comp.service.SeasonService;

@Path("seasons")
@Produces("application/json")
public class SeasonResource {

	private final SeasonService seasonService;
	private final ScheduleService scheduleService;

	public SeasonResource() {
		this.seasonService = new SeasonService();
		this.scheduleService = new ScheduleService();
	}

	@GET
	public Collection<Season> findAll() {
		final List<Season> seasons = this.seasonService.getSeasons();
		for (final Season season : seasons) {
			season.setLeagueSchedule(this.scheduleService.getLeagueSchedule(season.getId()));
			season.setPlayoffSchedule(this.scheduleService.getPlayoffSchedule(season.getId()));
		}
		return seasons;
	}
}
