package com.sas.comp.resource;

import com.sas.comp.models.Season;
import com.sas.comp.models.Team;
import com.sas.comp.service.SeasonService;
import com.sas.comp.service.TeamService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * Resource for creating and updating seasons.
 * Created by phsabo on 8/19/16.
 */
@Path("seasons")
@Produces(MediaType.APPLICATION_JSON)
public class SeasonResource {

    private final SeasonService seasonService = new SeasonService();

    private final TeamService teamService = new TeamService();

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

}
