package com.sas.comp.controllers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sas.comp.Cache;
import com.sas.comp.models.Competitive;
import com.sas.comp.models.Game;
import com.sas.comp.models.Goal;
import com.sas.comp.models.Player;
import com.sas.comp.service.CompetitiveService;
import com.sas.comp.service.GameService;
import com.sas.comp.service.GoalService;

@RestController
public class AdministrativeController {
	
	private final GameService gameService = new GameService();
	private final GoalService goalService = new GoalService();

	private final SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
	
    @RequestMapping(
    		value = "/service/cache/reset", 
    		method = RequestMethod.GET, 
    		produces = "application/json"
    )
	public  HttpEntity<?>  reset() {
		final CompetitiveService competitiveService = new CompetitiveService();
		final Competitive competitive = competitiveService.getCompetition();
		Cache.getCache().setCompetitive(competitive);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
    @RequestMapping(
    		value = "/service/games", 
    		method = RequestMethod.GET, 
    		produces = "application/json"
    )
	public List<Game> getSchedules(@RequestParam("date") final String date) throws ParseException  {
		if (date == null) {
			return gameService.getSchedules();
		} else {
			return gameService.getSchedules(sdf.parse(date));
		}
	}

    @RequestMapping(
    		value = "/service/games/{id}/score", 
    		method = RequestMethod.PUT, 
    		produces = "application/json"
    )
	public void update(@RequestBody final Game game) {
		gameService.updateScore(game);
	}
    
    @RequestMapping(
    		value = "/service/games/{id}/players", 
    		method = RequestMethod.GET, 
    		produces = "application/json"
    )
	public List<Player> getPlayers(@PathVariable("id") final Integer id) {
		return gameService.getPlayers(id);
	}

    @RequestMapping(
    		value = "/service/goals", 
    		method = RequestMethod.POST, 
    		produces = "application/json"
    )
	public void save(@RequestBody final Goal goal) {
		final List<Player> players = gameService.getPlayers(goal.getGameId());
		final Game game = gameService.getGame(goal.getGameId());

        if(goal.getPlayerId() < 0) {
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

    @RequestMapping(
    		value = "/service/goals", 
    		method = RequestMethod.DELETE, 
    		produces = "application/json"
    )
	public void destroy(@RequestBody final Goal goal) {
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
