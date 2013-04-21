package com.sas.comp.resource;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.sas.comp.models.Game;
import com.sas.comp.models.Player;
import com.sas.comp.service.GameService;

@Path("games")
@Produces(MediaType.APPLICATION_JSON)
public class GameResource {

	final GameService gameService = new GameService();

	private final SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");

	@GET
	public List<Game> getSchedules(@QueryParam("date") final String date) throws ParseException {
		if (date == null) {
			return gameService.getSchedules();
		} else {
			return gameService.getSchedules(sdf.parse(date));
		}
	}

	@PUT
	@Path("{id}/score")
	public void update(final Game schedule) {
		gameService.updateScore(schedule);
	}

	@GET
	@Path("{id}/players")
	public List<Player> getPlayers(@PathParam("id") final Integer id) {
		return gameService.getPlayers(id);
	}
}
