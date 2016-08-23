package com.sas.comp.resource;

import com.sas.comp.models.Player;
import com.sas.comp.models.Team;
import com.sas.comp.models.TeamPlayer;
import com.sas.comp.service.TeamService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * Resource for creating and updating teams
 * Created by Philippe on 8/19/16.
 */
@Path("teams")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TeamResource {

    private final TeamService service = new TeamService();

    @POST
    public Team create(Team team) {
        service.create(team);
        return team;
    }

    @PUT
    public void update(Team team) {
        service.update(team);
    }

    @GET
    @Path("{id}")
    public Team read(@PathParam("id") Integer teamID) {
        return service.read(teamID);
    }

    @GET
    @Path("{id}/players")
    public List<Player> readPlayers(@PathParam("id") Integer teamID) {
        return service.readPlayers(teamID);
    }

    @DELETE
    @Path("{id}")
    public void delete(@PathParam("id") Integer teamID) {
        service.delete(teamID);
    }

    @POST
    @Path("player")
    public TeamPlayer addTeamPlayer(TeamPlayer player) {
        service.addPlayer(player);
        return player;
    }

    @PUT
    @Path("player")
    public void updateTeamPlayer(TeamPlayer player) {
        service.updatePlayer(player);
    }

    @DELETE
    @Path("player")
    public void deleteTeamPlayer(TeamPlayer player) {
        service.deletePlayer(player);
    }

}
