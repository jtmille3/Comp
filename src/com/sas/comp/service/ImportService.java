package com.sas.comp.service;

import com.sas.comp.models.*;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: jeff
 * Date: 3/14/14
 * Time: 10:38 PM
 * To change this template use File | Settings | File Templates.
 */
public class ImportService {

    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static void main(final String[] args) throws Exception {
        final SeasonService seasonService = new SeasonService();
        final TeamService teamService = new TeamService();
        final PlayerService playerService = new PlayerService();
        final GameService gameService = new GameService();

        final File file = new File("./import/spring2019.json");
        final ObjectMapper mapper = new ObjectMapper(); // can reuse, share globally
        final Map root = mapper.readValue(file, Map.class);

        final String seasonName = (String) root.get("season");
        Season season = seasonService.find(seasonName);
        if(season == null) {
            System.out.println("Season not found.");
            season = new Season();
            season.setName(seasonName);
            seasonService.create(season);
        } else {
            System.out.println("Season: " + season.getName());
            System.out.println();
        }

        final List teams = (List) root.get("teams");

        for(final Object object : teams) {
            final Map teamMap  = (Map) object;
            final String teamName = (String) teamMap.get("name");
            Team team = teamService.find(teamName, season.getId());
            if(team == null) {
                System.out.println("Team " + teamName + " not found");
                team = new Team();
                team.setName(teamName);
                team.setSeasonId(season.getId());
                teamService.create(team);
            } else {
                System.out.println("Team: " + team.getName());
            }

            final Object captain = teamMap.get("captain");
            final Object cocaptain = teamMap.get("cocaptain");
            final Object goalie = teamMap.get("goalie");

            final List<String> playersList = (List<String>) teamMap.get("players");
            for(final String playerName : playersList) {
                Player player = playerService.find(playerName);
                if(player == null) {
                    System.out.println("Player " + playerName + " not found");
                    player = new Player();
                    player.setName(playerName);
                    playerService.create(player);
                } else {
                    System.out.println(player.getName());
                }

                if(!teamService.isPlaying(team, player)) {
                    TeamPlayer teamPlayer = new TeamPlayer(team,player,playerName.equals(captain),playerName.equals(cocaptain),playerName.equals(goalie));
                    teamService.addPlayer(teamPlayer);
                }
            }

            System.out.println();
        }

        System.out.println("Games: ");
        final List games = (List) root.get("games");
        for(final Object object : games) {
            final Map gameMap = (Map) object;
            final String away = gameMap.get("away").toString();
            final String home = gameMap.get("home").toString();
            final String datetime = gameMap.get("datetime").toString();
            final Boolean playoff = gameMap.get("playoff") != null;

            final Team homeGame = teamService.find(home, season.getId());
            final Team awayGame = teamService.find(away, season.getId());

            final Game game = new Game();
            game.setSeasonId(season.getId());
            game.setAway(away);
            game.setAwayId(awayGame.getId());
            game.setAwayScore(null);
            game.setHome(home);
            game.setHomeId(homeGame.getId());
            game.setHomeScore(null);
            game.setDate(sdf.parse(datetime));
            game.setPlayoff(playoff);

            if(!gameService.isScheduled(game)) {
                gameService.save(game);
            }

            System.out.println(game.getAway() + " vs " + game.getHome() + " @ " + datetime);
        }
    }
}
