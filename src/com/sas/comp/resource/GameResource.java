package com.sas.comp.resource;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import com.sas.comp.models.Game;
import com.sas.comp.models.Player;
import com.sas.comp.service.GameService;

@Path("games")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
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
	public void update(final Game game) {
		gameService.updateScore(game);
	}

	@GET
	@Path("{id}/players")
	public List<Player> getPlayers(@PathParam("id") final Integer id) {
		return gameService.getPlayers(id);
	}

	@POST
	@Path("/new")
	public Game newGame(final Game game) {
		return gameService.newGame(game);
	}

	@PUT
	@Path("{id}/save")
	public void save(@PathParam("id") final Integer id, final Game game) {
		gameService.update(game);
	}

	@DELETE
	@Path("{id}/delete")
	public void delete(@PathParam("id") final Integer id) {
		gameService.deleteGame(id);
	}

}
