package com.sas.comp.resource;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.sas.comp.models.Game;
import com.sas.comp.models.Goal;
import com.sas.comp.models.Player;
import com.sas.comp.service.GameService;
import com.sas.comp.service.GoalService;

@Path("goals")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class GoalResource {

	private final GameService gameService = new GameService();
	private final GoalService goalService = new GoalService();

	@POST
	public void save(final Goal goal) {
		final List<Player> players = gameService.getPlayers(goal.getGameId());
		final Game game = gameService.getGame(goal.getGameId());

		for (final Player player : players) {
			if (player.getPlayerId().intValue() == goal.getPlayerId().intValue() || goal.getPlayerId().intValue() < 0) {
				if (player.getTeamId().intValue() == game.getHomeId().intValue() || goal.getPlayerId().intValue() == -1) {
					game.setHomeScore(game.getHomeScore().intValue() + 1);
					break;
				} else if (player.getTeamId().intValue() == game.getAwayId().intValue() || goal.getPlayerId().intValue() == -2) {
					game.setAwayScore(game.getAwayScore().intValue() + 1);
					break;
				}
			}
		}

		gameService.updateScore(game);
		if (goal.getPlayerId().intValue() > 0) {
			goalService.addGoal(goal);
		}
	}

	@DELETE
	public void destroy(final Goal goal) {
		final List<Player> players = gameService.getPlayers(goal.getGameId());
		final Game game = gameService.getGame(goal.getGameId());

		for (final Player player : players) {
			if (player.getPlayerId().intValue() == goal.getPlayerId().intValue() || goal.getPlayerId().intValue() < 0) {
				if (player.getTeamId().intValue() == game.getHomeId().intValue() || goal.getPlayerId().intValue() == -1) {
					game.setHomeScore(game.getHomeScore().intValue() - 1);
					break;
				} else if (player.getTeamId().intValue() == game.getAwayId().intValue() || goal.getPlayerId().intValue() == -2) {
					game.setAwayScore(game.getAwayScore().intValue() - 1);
					break;
				}
			}
		}

		gameService.updateScore(game);
		if (goal.getPlayerId().intValue() > 0) {
			goalService.removeGoal(goal);
		}
	}
}
