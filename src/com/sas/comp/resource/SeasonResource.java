package com.sas.comp.resource;

import com.sas.comp.models.Season;
import com.sas.comp.models.Team;
import com.sas.comp.models.Game;
import com.sas.comp.service.SeasonService;
import com.sas.comp.service.TeamService;
import com.sas.comp.service.GameService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * Resource for creating and updating seasons.
 * Created by phsabo on 8/19/16.
 */
@Path("seasons")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SeasonResource {

    private final SeasonService seasonService = new SeasonService();

    private final TeamService teamService = new TeamService();

    private final GameService gameService = new GameService();

    @POST
    public Season create(Season season) {
        seasonService.create(season);
        return season;
    }

    @PUT
    public void update(Season season) {
        seasonService.update(season);
    }

    @GET
    @Path("{id}")
    public Season read(@PathParam("id") Integer seasonID) {
        return seasonService.read(seasonID);
    }

    @GET
    @Path("{id}/teams")
    public List<Team> readBySeasonId(@PathParam("id") Integer seasonID) {
        return teamService.readBySeasonId(seasonID);
    }

    @GET
    public List<Season> read() {
        return seasonService.getSeasons();
    }

    @GET
    @Path("{id}/schedule")
    public List<Game> readScheduleBySeasonId(@PathParam("id") Integer seasonID) {
        List<Game> seasonGames = gameService.getLeagueSchedule(seasonID);
        List<Game> playoffGames = gameService.getPlayoffSchedule(seasonID);
        playoffGames.addAll(seasonGames);
        return playoffGames;
    }

}
