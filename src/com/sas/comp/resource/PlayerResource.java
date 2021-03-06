package com.sas.comp.resource;

import com.sas.comp.models.Player;
import com.sas.comp.service.PlayerService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * CRUD for players
 * Created by Philippe on 8/19/16.
 */
@Path("players")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PlayerResource {
    final PlayerService service = new PlayerService();

    @GET
    public List<Player> read() {
        return service.read();
    }

    @POST
    public Player create(Player team) {
        service.create(team);
        return team;
    }

    @PUT
    public void update(Player team) {
        service.update(team);
    }

    @GET
    @Path("{id}")
    public Player read(@PathParam("id") Integer teamID) {
        return service.read(teamID);
    }

    @DELETE
    @Path("{id}")
    public void delete(@PathParam("id") Integer teamID) {
        service.delete(teamID);
    }
}
