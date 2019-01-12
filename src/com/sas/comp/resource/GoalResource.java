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

        if(goal.getPlayerId() < 0) {
        	// Ghost goals.  Used for own goals and games decided by PK.  Maybe forfeits too?
            if (goal.getPlayerId() == -1) {
                game.setHomeScore(game.getHomeScore() + 1);
            } else if (goal.getPlayerId() == -2) {
                game.setAwayScore(game.getAwayScore() + 1);
            }
        } else {
            for (final Player player : players) {
                if (player.getId().equals(goal.getPlayerId())) {
                    if (player.getTeamId().equals(game.getHomeId())) {
                        game.setHomeScore(game.getHomeScore() + 1);
                        break;
                    } else if (player.getTeamId().equals(game.getAwayId())) {
                        game.setAwayScore(game.getAwayScore() + 1);
                        break;
                    }
                }
            }
        }

		gameService.updateScore(game);
		if (goal.getPlayerId() > 0) {
			goalService.addGoal(goal);
		}
	}

	@DELETE
	public void destroy(final Goal goal) {
		final List<Player> players = gameService.getPlayers(goal.getGameId());
		final Game game = gameService.getGame(goal.getGameId());

        if(goal.getPlayerId() < 0) {
            if (goal.getPlayerId() == -1) {
                game.setHomeScore(game.getHomeScore() - 1);
            } else if (goal.getPlayerId() == -2) {
                game.setAwayScore(game.getAwayScore() - 1);
            }
        } else {
            for (final Player player : players) {
                if (player.getId().equals(goal.getPlayerId())) {
                    if (player.getTeamId().equals(game.getHomeId())) {
                        game.setHomeScore(game.getHomeScore() - 1);
                        break;
                    } else if (player.getTeamId().equals(game.getAwayId())) {
                        game.setAwayScore(game.getAwayScore() - 1);
                        break;
                    }
                }
            }
        }

		gameService.updateScore(game);
		if (goal.getPlayerId() > 0) {
			goalService.removeGoal(goal);
		}
	}
}
