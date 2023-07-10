package com.sas.comp.service;

import com.sas.comp.models.GoalieDetailedStats;
import com.sas.comp.models.Player;
import com.sas.comp.models.PlayerDetailedStats;
import com.sas.comp.mysql.Database;
import com.sas.comp.models.TeamStats;
import com.sas.comp.models.CaptainStats;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class StatisticService {

	/* the sql is getting big and hard to maintain inline
	 * so let's make some multi-line constants to help
	 */

	// note, there is a %s embedded for where clause injection
	// testStatSql.format("where clause or empty")
	private String teamStatSql = "select season, " +
			"team, " +
			"captain_name, " + 
			"count(*) as games_played, " + 
			"avg(goals_for) as gfa, " + 
			"avg(goals_against) as gaa, " + 
			"avg(goal_diff) as gda, " + 
			"avg(shutout) as soa, " + 
			"max(leagueWinner) as lw, " + 
			"max(playoffWinner) as pw, " + 
			"sum((case when (result = 1) then 1 else 0 end))/count(*)*100 AS winpct, " + 
			"sum((case when (result = -1) then 1 else 0 end))/count(*)*100 AS losspct, " + 
			"sum((case when (result = 0) then 1 else 0 end))/count(*)*100 AS tiepct " + 
			"from team_summary_full " + 
			"%s " +
			"group by team_id " + 
			"order by pw desc, lw desc, winpct desc, tiepct desc, losspct, gda desc, soa desc, team " + 
			"";

	// note, there is a %s embedded for where clause injection
	// captainStatSql.format("where clause or empty")
	private String captainStatSql = "select " + 
			"sq.captain_name, " + 
			"count(*) as seasons, " + 
			"avg(sq.gfa) as gfa, " + 
			"avg(sq.gaa) as gaa, " + 
			"avg(sq.gda) as gda, " + 
			"avg(sq.soa) as soa, " + 
			"sum(sq.lw) as lw, " + 
			"sum(sq.pw) as pw, " + 
			"sum(sq.lw)/count(*)*100 as lwpct, " + 
			"sum(sq.pw)/count(*)*100 as pwpct, " + 
			"avg(sq.winpct) as winpct, " + 
			"avg(sq.losspct) as losspct, " + 
			"avg(sq.tiepct) as tiepct " + 
			"from (" + 
			"   select season, team, captain_name, " + 
			"   count(*) as games_played, " + 
			"   avg(goals_for) as gfa, " + 
			"   avg(goals_against) as gaa, " + 
			"   avg(goal_diff) as gda, " + 
			"   avg(shutout) as soa, " + 
			"   max(leagueWinner) as lw, " + 
			"   max(playoffWinner) as pw, " + 
			"   sum((case when (result = 1) then 1 else 0 end))/count(*)*100 AS winpct, " + 
			"   sum((case when (result = -1) then 1 else 0 end))/count(*)*100 AS losspct, " + 
			"   sum((case when (result = 0) then 1 else 0 end))/count(*)*100 AS tiepct " + 
			"   from team_summary_full " + 
			"   %s " +
			"   group by team_id " + 
			") sq " + 
			"group by captain_name " + 
			"order by pw desc, lw desc, winpct desc, tiepct desc, losspct, gda desc, soa desc, captain_name " + 
			"";
    
    private TreeMap<Integer, Map<String,Map<String,List<Player>>>> allSeasonStatistics = new TreeMap<Integer, Map<String, Map<String,List<Player>>>>();
    
    public void initializePlayerGoalieStatistics() {
        this.populateAllSeasonPlayerStatistics();
        this.populateAllSeasonGoalieStatistics();
    }
    
    private Map<String,Map<String,List<Player>>> getEmptyStatMap() {
        // handlebars requires a fleshed out object for the season statistics map or it will fail silently so we 
        // populate the bare minimum object so the structure is present even for seasons not yet having any games
        Map<String, Map<String, List<Player>>> seasonStatistics = new HashMap<String, Map<String, List<Player>>>();
        Map<String, List<Player>> playerStatMap = new HashMap<String, List<Player>>();
        List<Player>players = new ArrayList<Player>();
        List<Player>goalies = new ArrayList<Player>();
        playerStatMap.put("player", players);
        playerStatMap.put("goalie", goalies);
        seasonStatistics.put("overall", playerStatMap);
        playerStatMap = new HashMap<String, List<Player>>();
        players = new ArrayList<Player>();
        goalies = new ArrayList<Player>();
        playerStatMap.put("player", players);
        playerStatMap.put("goalie", goalies);
        seasonStatistics.put("season", playerStatMap);
        playerStatMap = new HashMap<String, List<Player>>();
        players = new ArrayList<Player>();
        goalies = new ArrayList<Player>();
        playerStatMap.put("player", players);
        playerStatMap.put("goalie", goalies);
        seasonStatistics.put("playoff", playerStatMap);
        return seasonStatistics;
    }
    
    private void populateAllSeasonPlayerStatistics() {
        Database.doVoidTransaction("SELECT * FROM season_goal_totals ORDER BY season_id, total_goals DESC, team_name, player", (pstmt) -> {
            final ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                this.populatePlayerStatisticFromRs(rs, "overall");
            }
        });
       
        Database.doVoidTransaction("SELECT * FROM regular_season_goal_totals ORDER BY season_id, total_goals DESC, team_name, player", (pstmt) -> {
            final ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                this.populatePlayerStatisticFromRs(rs, "season");
            }
        });

        Database.doVoidTransaction("SELECT * FROM playoff_goal_totals ORDER BY season_id, total_goals DESC, team_name, player", (pstmt) -> {
            final ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                this.populatePlayerStatisticFromRs(rs, "playoff");
            }
        });
       
    }
    private void populatePlayerStatisticFromRs(final ResultSet rs, String type) throws SQLException {
        Integer seasonId = rs.getInt("season_id");
        Map<String, Map<String,List<Player>>>statMap = allSeasonStatistics.get(seasonId);
        if( statMap == null ) statMap = this.getEmptyStatMap();
        allSeasonStatistics.put(seasonId, statMap);
        Map<String, List<Player>> playerStatMap = statMap.get(type);
        if( playerStatMap == null ) playerStatMap = new HashMap<String, List<Player>>();
        statMap.put(type, playerStatMap);
        List<Player> statistics = playerStatMap.get("player");
        if( statistics == null) statistics = new ArrayList<Player>();
        playerStatMap.put("player", statistics);
        final Player statistic = new Player();
        int rank = 1;
        if( allSeasonStatistics.containsKey(seasonId) ) {
            List<Player> seasonPlayers = allSeasonStatistics.get(seasonId).get(type).get("player");
            if( seasonPlayers.size() > 0 ) {
                Player prevPlayer = seasonPlayers.get(seasonPlayers.size() - 1);
                rank = prevPlayer.getRank();
                if( rs.getInt("total_goals") < prevPlayer.getGoals() ) {
                    rank++;
                }
            }
        }
        statistic.setRank(rank);
        statistic.setTeam(rs.getString("team_name"));
        statistic.setTeamId(rs.getInt("team_id"));
        statistic.setName(rs.getString("player"));
        statistic.setGoalie(rs.getBoolean("goalie"));
        statistic.setCaptain(rs.getBoolean("captain"));
        statistic.setCoCaptain(rs.getBoolean("cocaptain"));
        statistic.setGoals(rs.getInt("total_goals"));
        statistics.add(statistic);
    }
    
    private void populateAllSeasonGoalieStatistics() {
        String baseSql = "SELECT season_id, player_id, player, team, sum(against) as total_against, sum(shutouts) as total_shutouts FROM goalie_summary ";
        String groupOrder = "group by season_id, player_id ORDER BY season_id DESC, total_shutouts desc";
        
        Database.doVoidTransaction(baseSql + groupOrder, (pstmt) -> {
            final ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                this.populateGoalieStatisticFromRs(rs, "overall");
            }
        });

        Database.doVoidTransaction(baseSql + " where playoff = 0 " + groupOrder, (pstmt) -> {
            final ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                this.populateGoalieStatisticFromRs(rs, "season");
            }
        });
        
        Database.doVoidTransaction(baseSql + " where playoff = 1 " + groupOrder, (pstmt) -> {
            final ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                this.populateGoalieStatisticFromRs(rs, "playoff");
            }
        });

    }
    
    private void populateGoalieStatisticFromRs(final ResultSet rs, String type) throws SQLException {
        Integer seasonId = rs.getInt("season_id");
        Map<String, Map<String,List<Player>>>statMap = allSeasonStatistics.get(seasonId);
        if( statMap == null ) statMap = this.getEmptyStatMap();
        allSeasonStatistics.put(seasonId, statMap);
        Map<String, List<Player>> playerStatMap = statMap.get(type);
        if( playerStatMap == null ) playerStatMap = new HashMap<String, List<Player>>();
        statMap.put(type, playerStatMap);
        List<Player> statistics = playerStatMap.get("goalie");
        if( statistics == null) statistics = new ArrayList<Player>();
        playerStatMap.put("goalie", statistics);
        final Player statistic = new Player();
        statistic.setId(rs.getInt("player_id"));
        statistic.setName(rs.getString("player"));
        statistic.setTeam(rs.getString("team"));
        statistic.setGoalsAgainst(rs.getInt("total_against"));
        statistic.setShutouts(rs.getInt("total_shutouts"));
        statistics.add(statistic);
    }

    public Map<String, Map<String, List<Player>>> getSeasonStatistics(final Integer seasonId) {
        final Map<String, Map<String,List<Player>>> statistics = this.allSeasonStatistics.get(seasonId);
        return statistics;
    }

    public List<Player> getShutoutStatistics() {
        final List<Player> statistics = new ArrayList<>();

        Database.doVoidTransaction("SELECT * FROM shutout_alltime_statistics ORDER BY shutouts desc", (pstmt) -> {

            final ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                final Player statistic = new Player();
                statistic.setName(rs.getString("player"));
                statistic.setId(rs.getInt("player_id"));
                statistic.setGoalie(rs.getBoolean("goalie"));
                statistic.setGoalsAgainst(rs.getInt("against"));
                statistic.setShutouts(rs.getInt("shutouts"));
                statistics.add(statistic);
            }

        });

        return statistics;
    }
    
    public Map<String, List<PlayerDetailedStats>> getPlayerDetailedStatsMap() {
        // player detailed stats has two query components.  The goal oriented and the winlosstie oriented
        // we first get the goal oriented and pass that map structure to the winlosstie oriented which
        // will not only get the winlosstie but tie in the appropriate goal data for each player.
        // basically, cannot (or would be difficult and expensive) to do in one sql
        HashMap<String, HashMap<String, PlayerDetailedStats>> playerDetailedGoalStatsMap = this.getPlayerDetailedGoalStats();
        Map<String, List<PlayerDetailedStats>> playerWinLossTie = this.getPlayerWinLossTie(playerDetailedGoalStatsMap);
        return playerWinLossTie;
    }
    
    private HashMap<String, HashMap<String, PlayerDetailedStats>> getPlayerDetailedGoalStats() {
        final HashMap<String, HashMap<String, PlayerDetailedStats>> playerDetailedGoalStatsMap = new HashMap<String, HashMap<String,PlayerDetailedStats>>();
        String baseSql = "select player_name, sum(totalGoals) as totalGoals, max(totalGoals) as maxGoalInSeason, max(maxGoalInGame) as maxGoalInGame, playoff from ( "
                       + "select season_id, player_name, sum(goals) as totalGoals, max(goals) as maxGoalInGame, playoff from ( "
                       + "select p.name as player_name, g.*, count(g.id) as goals, gm.playoff, gm.season_id "
                       + "from players p left join goals g on p.id = g.player_id left join games gm on g.game_id = gm.id ";
        String internalWhere = "";
        String subqueryClosure = "group by gm.season_id, gm.id, p.name) sq1 " 
                               + "group by season_id, player_name) sq2 ";

        Database.doVoidTransaction(baseSql + internalWhere + subqueryClosure + " group by player_name order by totalGoals DESC", (pstmt) -> {
            HashMap<String, PlayerDetailedStats> detailedStats = new HashMap<String, PlayerDetailedStats>();
            playerDetailedGoalStatsMap.put("overall", detailedStats);
            final ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                final PlayerDetailedStats detailedStat = this.extractPlayerDetailedStatsFromResultSet(rs);
                detailedStats.put(rs.getString("player_name"), detailedStat);
            }
        });

        internalWhere = "where playoff = 0 ";
        Database.doVoidTransaction(baseSql + internalWhere + subqueryClosure + " group by player_name order by totalGoals DESC", (pstmt) -> {
            HashMap<String, PlayerDetailedStats> detailedStats = new HashMap<String, PlayerDetailedStats>();
            playerDetailedGoalStatsMap.put("season", detailedStats);
            final ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                final PlayerDetailedStats detailedStat = this.extractPlayerDetailedStatsFromResultSet(rs);
                detailedStats.put(rs.getString("player_name"), detailedStat);
            }
        });

        internalWhere = "where playoff = 1 ";
        Database.doVoidTransaction(baseSql + internalWhere + subqueryClosure + " group by player_name order by totalGoals DESC", (pstmt) -> {
            HashMap<String, PlayerDetailedStats> detailedStats = new HashMap<String, PlayerDetailedStats>();
            playerDetailedGoalStatsMap.put("playoff", detailedStats);
            final ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                final PlayerDetailedStats detailedStat = this.extractPlayerDetailedStatsFromResultSet(rs);
                detailedStats.put(rs.getString("player_name"), detailedStat);
            }
        });

        return playerDetailedGoalStatsMap;
    }

    private PlayerDetailedStats extractPlayerDetailedStatsFromResultSet(final ResultSet rs) throws SQLException {
        final PlayerDetailedStats detailedStat = new PlayerDetailedStats();
        detailedStat.setName(rs.getString("player_name"));
        detailedStat.setTotalGoals(rs.getInt("totalGoals"));
        detailedStat.setMaxGoalsInSeason(rs.getInt("maxGoalInSeason"));
        detailedStat.setMaxGoalsInGame(rs.getInt("maxGoalInGame"));
        return detailedStat;
    }
    
    public Map<String, List<PlayerDetailedStats>> getPlayerWinLossTie(HashMap<String, HashMap<String, PlayerDetailedStats>> playerDetailedGoalStatsMap) {
        final Map<String, List<PlayerDetailedStats>> playerDetailedStatsMap = new HashMap<String,List<PlayerDetailedStats>>();
        
        String subquery = "select players.id as player_id, players.name as player_name, games.playoff, games.season_id, "
                        + "(CASE WHEN ((team_player.team_id = games.home_team_id and games.home_score > games.away_score) or "
                        + "(team_player.team_id = games.away_team_id and games.away_score > games.home_score) ) THEN 1 ELSE 0 END) AS win, "
                        + "(CASE WHEN ((team_player.team_id = games.home_team_id and games.home_score < games.away_score) or "
                        + "(team_player.team_id = games.away_team_id and games.away_score < games.home_score)) THEN 1 ELSE 0 END) AS loss, "
                        + "(CASE WHEN games.home_score = games.away_score THEN 1 ELSE 0 END) AS tie "
                        + "from players join team_player on players.id = team_player.player_id join games on (games.home_team_id = team_player.team_id or games.away_team_id = team_player.team_id) ";
        String baseSql = "select sq.player_id, player_name, count(distinct season_id) as num_seasons, "
                       + "sq.playoff, sum(win+loss+tie) as num_games, sum(win) as wins, sum(loss) as losses, sum(tie) as ties, "
                       + "sum(win)/sum(win+loss+tie)*100 as winpct, sum(loss)/sum(win+loss+tie)*100 as losspct, sum(tie)/sum(win+loss+tie)*100 as tiepct, "
                       + "pat.league as leagueWinner, pat.playoff as playoffWinner "
                       + "from (" + subquery + ") sq left join player_alltime_statistics pat on sq.player_id = pat.player_id";
        
        Database.doVoidTransaction(baseSql + " group by sq.player_id order by wins DESC", (pstmt) -> {
            List<PlayerDetailedStats> detailedStats = new ArrayList<>();
            playerDetailedStatsMap.put("overall", detailedStats);
            final ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                PlayerDetailedStats detailedStat = extractPlayerDetailedStatsFromRs(rs, "overall", playerDetailedGoalStatsMap);
                detailedStats.add(detailedStat);
            }
        });

        Database.doVoidTransaction(baseSql + " where sq.playoff = 0 group by sq.player_id order by wins DESC", (pstmt) -> {
            List<PlayerDetailedStats> detailedStats = new ArrayList<>();
            playerDetailedStatsMap.put("season", detailedStats);
            final ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                PlayerDetailedStats detailedStat = extractPlayerDetailedStatsFromRs(rs, "season", playerDetailedGoalStatsMap);
                detailedStats.add(detailedStat);
            }
        });

        Database.doVoidTransaction(baseSql + " where sq.playoff = 1 group by sq.player_id order by wins DESC", (pstmt) -> {
            List<PlayerDetailedStats> detailedStats = new ArrayList<>();
            playerDetailedStatsMap.put("playoff", detailedStats);
            final ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                PlayerDetailedStats detailedStat = extractPlayerDetailedStatsFromRs(rs, "playoff", playerDetailedGoalStatsMap);
                detailedStats.add(detailedStat);
            }
        });

        return playerDetailedStatsMap;
    }
    
    private PlayerDetailedStats extractPlayerDetailedStatsFromRs(final ResultSet rs, String type, HashMap<String, HashMap<String, PlayerDetailedStats>> playerDetailedGoalStatsMap) throws SQLException {
        final PlayerDetailedStats detailedStat;
        String playerName = rs.getString("player_name");
        if( playerDetailedGoalStatsMap.get(type).containsKey(playerName) ) {
            detailedStat = playerDetailedGoalStatsMap.get(type).get(playerName);
        } else {
            detailedStat = new PlayerDetailedStats();
        }
        detailedStat.setId(rs.getInt("player_id"));
        detailedStat.setName(rs.getString("player_name"));
        detailedStat.setNumSeasons(rs.getInt("num_seasons"));
        Integer totalGoals = detailedStat.getTotalGoals();
        String goalsPerSeason = "0.0";
        if( totalGoals != null ) {
            goalsPerSeason = String.format("%.1f", (float)(totalGoals.floatValue() / detailedStat.getNumSeasons().floatValue()));
        }
        detailedStat.setAvgGoalsSeason(goalsPerSeason);
        detailedStat.setNumGames(rs.getInt("num_games"));
        detailedStat.setLeagueWinner(rs.getInt("leagueWinner"));
        detailedStat.setPlayoffWinner(rs.getInt("playoffWinner"));
        detailedStat.setTrophies(rs.getInt("leagueWinner") + rs.getInt("playoffWinner"));
        detailedStat.setWin(rs.getInt("wins"));
        detailedStat.setLoss(rs.getInt("losses"));
        detailedStat.setTie(rs.getInt("ties"));
        String winpct = String.format("%.1f", rs.getFloat("winpct"));
        String losspct = String.format("%.1f", rs.getFloat("losspct"));
        String tiepct = String.format("%.1f", rs.getFloat("tiepct"));
        detailedStat.setWinpct(winpct);
        detailedStat.setLosspct(losspct);
        detailedStat.setTiepct(tiepct);
        return detailedStat;
    }

    public Map<String, List<GoalieDetailedStats>> getGoalieDetailedStatsMap() {
        final Map<String, List<GoalieDetailedStats>> goalieDetailedStatsMap = new HashMap<String,List<GoalieDetailedStats>>();
        
        String baseSql = "SELECT gs.player, count(distinct season_id) as num_seasons, count(game_id) as num_games, "
                       + "pat.league as leagueWinner, pat.playoff as playoffWinner, sum(against) as against, sum(shutouts) as shutouts, "
                       + "sum(against)/count(distinct season_id) as againstperseason, sum(against)/count(game_id) as againstpergame, "
                       + "sum(shutouts)/count(distinct season_id) as shutoutsperseason, sum(shutouts)/count(game_id) as shutoutspergame "
                       + "from goalie_summary gs join player_alltime_statistics pat on gs.player = pat.player ";

        Database.doVoidTransaction(baseSql + " group by gs.player order by shutouts DESC", (pstmt) -> {
            List<GoalieDetailedStats> detailedStats = new ArrayList<>();
            goalieDetailedStatsMap.put("overall", detailedStats);
            final ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                final GoalieDetailedStats detailedStat = this.extractGoalieDetailedStatsFromResultSet(rs);
                detailedStats.add(detailedStat);
            }
        });

        Database.doVoidTransaction(baseSql + " where gs.playoff = 0 group by gs.player order by shutouts DESC", (pstmt) -> {
            List<GoalieDetailedStats> detailedStats = new ArrayList<>();
            goalieDetailedStatsMap.put("season", detailedStats);
            final ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                final GoalieDetailedStats detailedStat = this.extractGoalieDetailedStatsFromResultSet(rs);
                detailedStats.add(detailedStat);
            }
        });

        Database.doVoidTransaction(baseSql + " where gs.playoff = 1 group by gs.player order by shutouts DESC", (pstmt) -> {
            List<GoalieDetailedStats> detailedStats = new ArrayList<>();
            goalieDetailedStatsMap.put("playoff", detailedStats);
            final ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                final GoalieDetailedStats detailedStat = this.extractGoalieDetailedStatsFromResultSet(rs);
                detailedStats.add(detailedStat);
            }
        });

        return goalieDetailedStatsMap;
    }
    
    private GoalieDetailedStats extractGoalieDetailedStatsFromResultSet(final ResultSet rs) throws SQLException {
        final GoalieDetailedStats detailedStat = new GoalieDetailedStats();
        String goalieName = rs.getString("player");
        detailedStat.setName(goalieName);
        detailedStat.setNumSeasons(rs.getInt("num_seasons"));
        detailedStat.setNumGames(rs.getInt("num_games"));
        detailedStat.setLeagueWinner(rs.getInt("leagueWinner"));
        detailedStat.setPlayoffWinner(rs.getInt("playoffWinner"));
        detailedStat.setTrophies(rs.getInt("leagueWinner") + rs.getInt("playoffWinner"));
        detailedStat.setTotalGoalsAgainst(rs.getInt("against"));
        detailedStat.setTotalShutouts(rs.getInt("shutouts"));
        detailedStat.setAgainstPerSeason(String.format("%.1f", rs.getFloat("againstperseason")));
        detailedStat.setAgainstPerGame(String.format("%.1f", rs.getFloat("againstpergame")));
        detailedStat.setShutoutsPerSeason(String.format("%.1f", rs.getFloat("shutoutsperseason")));
        detailedStat.setShutoutsPerGame(String.format("%.1f", rs.getFloat("shutoutspergame")));
        return detailedStat;
    }

    public Map<String, List<TeamStats>> getTeamStatsMap() {
        final Map<String, List<TeamStats>> teamStatsMap = new HashMap<String,List<TeamStats>>();
        
        String sql = String.format(teamStatSql, "");
        Database.doVoidTransaction(sql, (pstmt) -> {
            List<TeamStats> teamStats = new ArrayList<>();
            teamStatsMap.put("overall", teamStats);
            final ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                final TeamStats teamStat = this.extractTeamStatFromResultSet(rs);
                teamStats.add(teamStat);
            }
        });

        sql = String.format(teamStatSql, "where playoff = 0");
        Database.doVoidTransaction(sql, (pstmt) -> {
            List<TeamStats> teamStats = new ArrayList<>();
            teamStatsMap.put("season", teamStats);
            final ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                final TeamStats teamStat = this.extractTeamStatFromResultSet(rs);
                teamStats.add(teamStat);
            }
        });

        sql = String.format(teamStatSql, "where playoff = 1");
        Database.doVoidTransaction(sql, (pstmt) -> {
            List<TeamStats> teamStats = new ArrayList<>();
            teamStatsMap.put("playoff", teamStats);
            final ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                final TeamStats teamStat = this.extractTeamStatFromResultSet(rs);
                teamStats.add(teamStat);
            }
        });

        return teamStatsMap;
    }

    private TeamStats extractTeamStatFromResultSet(final ResultSet rs) throws SQLException {
        final TeamStats detailedStat = new TeamStats();
        String season = rs.getString("season");
        String name = rs.getString("team");
        String captain = rs.getString("captain_name");
        detailedStat.setSeason(season);
        detailedStat.setName(name);
        detailedStat.setCaptain(captain);
        detailedStat.setGamesPlayed(rs.getInt("games_played"));
        detailedStat.setGoalsFor(String.format("%.1f", rs.getFloat("gfa")));
        detailedStat.setGoalsAgainst(String.format("%.1f", rs.getFloat("gaa")));
        detailedStat.setGoalDifferential(String.format("%.1f", rs.getFloat("gda")));
        detailedStat.setShutouts(String.format("%.1f", rs.getFloat("soa")));
        detailedStat.setLeagueWinner(rs.getInt("lw"));
        detailedStat.setPlayoffWinner(rs.getInt("pw"));
        detailedStat.setWinpct(String.format("%.1f", rs.getFloat("winpct")));
        detailedStat.setLosspct(String.format("%.1f", rs.getFloat("losspct")));
        detailedStat.setTiepct(String.format("%.1f", rs.getFloat("tiepct")));
        return detailedStat;
    }

    public Map<String, List<CaptainStats>> getCaptainStatsMap() {
        final Map<String, List<CaptainStats>> captainStatsMap = new HashMap<String,List<CaptainStats>>();
        
        String sql = String.format(captainStatSql, "");
        Database.doVoidTransaction(sql, (pstmt) -> {
            List<CaptainStats> captainStats = new ArrayList<>();
            captainStatsMap.put("overall", captainStats);
            final ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                final CaptainStats captainStat = this.extractCaptainStatFromResultSet(rs);
                captainStats.add(captainStat);
            }
        });

        sql = String.format(captainStatSql, "where playoff = 0");
        Database.doVoidTransaction(sql, (pstmt) -> {
            List<CaptainStats> captainStats = new ArrayList<>();
            captainStatsMap.put("season", captainStats);
            final ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                final CaptainStats captainStat = this.extractCaptainStatFromResultSet(rs);
                captainStats.add(captainStat);
            }
        });

        sql = String.format(captainStatSql, "where playoff = 1");
        Database.doVoidTransaction(sql, (pstmt) -> {
            List<CaptainStats> captainStats = new ArrayList<>();
            captainStatsMap.put("playoff", captainStats);
            final ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                final CaptainStats captainStat = this.extractCaptainStatFromResultSet(rs);
                captainStats.add(captainStat);
            }
        });

        return captainStatsMap;
    }

    private CaptainStats extractCaptainStatFromResultSet(final ResultSet rs) throws SQLException {
        final CaptainStats detailedStat = new CaptainStats();
        String captain = rs.getString("captain_name");
        detailedStat.setName(captain);
        detailedStat.setSeasons(rs.getInt("seasons"));
        detailedStat.setGoalsFor(String.format("%.1f", rs.getFloat("gfa")));
        detailedStat.setGoalsAgainst(String.format("%.1f", rs.getFloat("gaa")));
        detailedStat.setGoalDifferential(String.format("%.1f", rs.getFloat("gda")));
        detailedStat.setShutouts(String.format("%.1f", rs.getFloat("soa")));
        detailedStat.setLeagueWinner(rs.getInt("lw"));
        detailedStat.setPlayoffWinner(rs.getInt("pw"));
        detailedStat.setLwpct(String.format("%.1f", rs.getFloat("lwpct")));
        detailedStat.setPwpct(String.format("%.1f", rs.getFloat("pwpct")));
        detailedStat.setWinpct(String.format("%.1f", rs.getFloat("winpct")));
        detailedStat.setLosspct(String.format("%.1f", rs.getFloat("losspct")));
        detailedStat.setTiepct(String.format("%.1f", rs.getFloat("tiepct")));
        return detailedStat;
    }
}
