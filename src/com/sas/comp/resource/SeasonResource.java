package com.sas.comp.resource;

import com.sas.comp.models.Season;
import com.sas.comp.service.SeasonService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 * Resource for creating and updating seasons.
 * Created by phsabo on 8/19/16.
 */
@Path("seasons")
@Produces(MediaType.APPLICATION_JSON)
public class SeasonResource {

    private final SeasonService seasonService = new SeasonService();

    @POST
    private Season create(Season season) {
        seasonService.create(season);
        return season;
    }

    @PUT
    private void update(Season season) {
        seasonService.update(season);
    }
}
