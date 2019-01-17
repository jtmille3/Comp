package com.sas.comp.service;

import com.sas.comp.models.Player;
import com.sas.comp.models.PlayerDetailedStats;
import com.sas.comp.mysql.Database;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class StatisticService {
    
    private TreeMap<Integer, List<Player>> allSeasonPlayerStatistics = new TreeMap<Integer, List<Player>>();
    private TreeMap<Integer, List<Player>> allSeasonGoalieStatistics = new TreeMap<Integer, List<Player>>();
    
    public StatisticService() {
        this.populateAllSeasonPlayerStatistics();
        this.populateAllSeasonGoalieStatistics();
    }

    private void populateAllSeasonPlayerStatistics() {
        Database.doVoidTransaction("SELECT * FROM player_statistics ORDER BY season_id, goals DESC, team_id, player_id", (pstmt) -> {
            final ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Integer seasonId = rs.getInt("season_id");
                List<Player>statistics = allSeasonPlayerStatistics.get(seasonId);
                if( statistics == null ) statistics = new ArrayList<>();
                final Player statistic = new Player();
                int rank = 1;
                if( allSeasonPlayerStatistics.containsKey(seasonId) ) {
                    List<Player> seasonPlayers = allSeasonPlayerStatistics.get(seasonId);
                    if( seasonPlayers.size() > 0 ) {
                        Player prevPlayer = seasonPlayers.get(seasonPlayers.size() - 1);
                        rank = prevPlayer.getRank();
                        if( rs.getInt("goals") < prevPlayer.getGoals() ) {
                            rank++;
                        }
                    }
                }
                statistic.setRank(rank);
                statistic.setTeam(rs.getString("team"));
                statistic.setName(rs.getString("player"));
                statistic.setGoals(rs.getInt("goals"));
                statistic.setTeamId(rs.getInt("team_id"));
                statistic.setId(rs.getInt("player_id"));
                statistic.setGoalie(rs.getBoolean("goalie"));
                statistic.setCaptain(rs.getBoolean("captain"));
                statistic.setCoCaptain(rs.getBoolean("co_captain"));
                statistics.add(statistic);
                allSeasonPlayerStatistics.put(seasonId, statistics);
            }
        });
       
    }

    private void populateAllSeasonGoalieStatistics() {
        Database.doVoidTransaction("SELECT * FROM shutout_statistics WHERE goalie = 1 ORDER BY season_id, shutouts DESC", (pstmt) -> {
            final ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Integer seasonId = rs.getInt("season_id");
                List<Player>statistics = allSeasonGoalieStatistics.get(seasonId);
                if( statistics == null ) statistics = new ArrayList<>();
                final Player statistic = new Player();
                statistic.setName(rs.getString("player"));
                statistic.setId(rs.getInt("player_id"));
                statistic.setTeam(rs.getString("team"));
                statistic.setGoalsAgainst(rs.getInt("against"));
                statistic.setShutouts(rs.getInt("shutouts"));
                statistics.add(statistic);
                allSeasonGoalieStatistics.put(seasonId, statistics);
            }
        });
    }

    public List<Player> getPlayerStatistics(final Integer seasonId) {
        final List<Player> statistics = this.allSeasonPlayerStatistics.get(seasonId);
        return statistics;
    }

    public List<Player> getGoalieStatistics(final Integer seasonId) {
        final List<Player> statistics = this.allSeasonGoalieStatistics.get(seasonId);
        return statistics;
    }

    public List<Player> getPlayerStatistics() {
        final List<Player> statistics = new ArrayList<>();

        Database.doVoidTransaction("SELECT * FROM player_alltime_statistics ORDER BY goals DESC", (pstmt) -> {

            final ResultSet rs = pstmt.executeQuery();
            int rank = 1;
            while (rs.next()) {
                final Player statistic = new Player();
                statistic.setRank(rank++);
                statistic.setName(rs.getString("player"));
                statistic.setId(rs.getInt("player_id"));
                statistic.setLeagueWinner(rs.getInt("league"));
                statistic.setPlayoffWinner(rs.getInt("playoff"));
                statistic.setGoals(rs.getInt("goals"));
                statistic.setSeasonsPlayed(rs.getInt("seasonsPlayed"));
                String goalsPerSeason = String.format("%.1f", (float)(statistic.getGoals().floatValue() / statistic.getSeasonsPlayed().floatValue()));
                statistic.setGoalsPerSeason(goalsPerSeason);
                statistics.add(statistic);
            }
        });

        return statistics;
    }

    public List<Player> getGoalieStatistics() {
        final List<Player> statistics = new ArrayList<>();

        Database.doVoidTransaction("SELECT * FROM goalie_alltime_statistics ORDER BY shutouts desc", (pstmt) -> {

            final ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                final Player statistic = new Player();
                statistic.setName(rs.getString("player"));
                statistic.setGoalsAgainst(rs.getInt("against"));
                statistic.setShutouts(rs.getInt("shutouts"));
                statistics.add(statistic);
            }
        });

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
                final PlayerDetailedStats detailedStat = new PlayerDetailedStats();
                detailedStat.setName(rs.getString("player_name"));
                detailedStat.setTotalGoals(rs.getInt("totalGoals"));
                detailedStat.setMaxGoalsInSeason(rs.getInt("maxGoalInSeason"));
                detailedStat.setMaxGoalsInGame(rs.getInt("maxGoalInGame"));
                detailedStats.put(rs.getString("player_name"), detailedStat);
            }
        });

        internalWhere = "where playoff = 0 ";
        Database.doVoidTransaction(baseSql + internalWhere + subqueryClosure + " group by player_name order by totalGoals DESC", (pstmt) -> {
            HashMap<String, PlayerDetailedStats> detailedStats = new HashMap<String, PlayerDetailedStats>();
            playerDetailedGoalStatsMap.put("season", detailedStats);
            final ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                final PlayerDetailedStats detailedStat = new PlayerDetailedStats();
                detailedStat.setName(rs.getString("player_name"));
                detailedStat.setTotalGoals(rs.getInt("totalGoals"));
                detailedStat.setMaxGoalsInSeason(rs.getInt("maxGoalInSeason"));
                detailedStat.setMaxGoalsInGame(rs.getInt("maxGoalInGame"));
                detailedStats.put(rs.getString("player_name"), detailedStat);
            }
        });

        internalWhere = "where playoff = 1 ";
        Database.doVoidTransaction(baseSql + internalWhere + subqueryClosure + " group by player_name order by totalGoals DESC", (pstmt) -> {
            HashMap<String, PlayerDetailedStats> detailedStats = new HashMap<String, PlayerDetailedStats>();
            playerDetailedGoalStatsMap.put("playoff", detailedStats);
            final ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                final PlayerDetailedStats detailedStat = new PlayerDetailedStats();
                detailedStat.setName(rs.getString("player_name"));
                detailedStat.setTotalGoals(rs.getInt("totalGoals"));
                detailedStat.setMaxGoalsInSeason(rs.getInt("maxGoalInSeason"));
                detailedStat.setMaxGoalsInGame(rs.getInt("maxGoalInGame"));
                detailedStats.put(rs.getString("player_name"), detailedStat);
            }
        });

        return playerDetailedGoalStatsMap;
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
                       + "sum(win)/count(*)*100 as winpct, sum(loss)/count(*)*100 as losspct, sum(tie)/count(*)*100 as tiepct, "
                       + "pat.league as leagueWinner, pat.playoff as playoffWinner "
                       + "from (" + subquery + ") sq left join player_alltime_statistics pat on sq.player_id = pat.player_id";

        Database.doVoidTransaction(baseSql + " group by sq.player_id order by wins DESC", (pstmt) -> {
            List<PlayerDetailedStats> detailedStats = new ArrayList<>();
            playerDetailedStatsMap.put("overall", detailedStats);
            final ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                String playerName = rs.getString("player_name");
                final PlayerDetailedStats detailedStat;
                if( playerDetailedGoalStatsMap.get("overall").containsKey(playerName) ) {
                    detailedStat = playerDetailedGoalStatsMap.get("overall").get(playerName);
                } else {
                    detailedStat = new PlayerDetailedStats();
                }
                detailedStat.setId(rs.getInt("player_id"));
                detailedStat.setName(rs.getString("player_name"));
                detailedStat.setNumSeasons(rs.getInt("num_seasons"));
                Integer totalGoals = detailedStat.getTotalGoals();
                String goalsPerSeason = "0";
                if( totalGoals != null ) {
                    goalsPerSeason = String.format("%.1f", (float)(totalGoals.floatValue() / detailedStat.getNumSeasons().floatValue()));
                }
                detailedStat.setAvgGoalsSeason(goalsPerSeason);
                detailedStat.setNumGames(rs.getInt("num_games"));
                detailedStat.setLeagueWinner(rs.getInt("leagueWinner"));
                detailedStat.setPlayoffWinner(rs.getInt("playoffWinner"));
                detailedStat.setWin(rs.getInt("wins"));
                detailedStat.setLoss(rs.getInt("losses"));
                detailedStat.setTie(rs.getInt("ties"));
                String winpct = String.format("%.1f", rs.getFloat("winpct"));
                String losspct = String.format("%.1f", rs.getFloat("losspct"));
                String tiepct = String.format("%.1f", rs.getFloat("tiepct"));
                detailedStat.setWinpct(winpct);
                detailedStat.setLosspct(losspct);
                detailedStat.setTiepct(tiepct);
                detailedStats.add(detailedStat);
            }
        });

        Database.doVoidTransaction(baseSql + " where sq.playoff = 0 group by sq.player_id order by wins DESC", (pstmt) -> {
            List<PlayerDetailedStats> detailedStats = new ArrayList<>();
            playerDetailedStatsMap.put("season", detailedStats);
            final ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                String playerName = rs.getString("player_name");
                final PlayerDetailedStats detailedStat;
                if( playerDetailedGoalStatsMap.get("season").containsKey(playerName) ) {
                    detailedStat = playerDetailedGoalStatsMap.get("season").get(playerName);
                } else {
                    detailedStat = new PlayerDetailedStats();
                }
                detailedStat.setId(rs.getInt("player_id"));
                detailedStat.setName(rs.getString("player_name"));
                detailedStat.setNumSeasons(rs.getInt("num_seasons"));
                Integer totalGoals = detailedStat.getTotalGoals();
                String goalsPerSeason = "0";
                if( totalGoals != null ) {
                    goalsPerSeason = String.format("%.1f", (float)(totalGoals.floatValue() / detailedStat.getNumSeasons().floatValue()));
                }
                detailedStat.setAvgGoalsSeason(goalsPerSeason);
                detailedStat.setNumGames(rs.getInt("num_games"));
                detailedStat.setLeagueWinner(rs.getInt("leagueWinner"));
                detailedStat.setPlayoffWinner(rs.getInt("playoffWinner"));
                detailedStat.setWin(rs.getInt("wins"));
                detailedStat.setLoss(rs.getInt("losses"));
                detailedStat.setTie(rs.getInt("ties"));
                String winpct = String.format("%.1f", rs.getFloat("winpct"));
                String losspct = String.format("%.1f", rs.getFloat("losspct"));
                String tiepct = String.format("%.1f", rs.getFloat("tiepct"));
                detailedStat.setWinpct(winpct);
                detailedStat.setLosspct(losspct);
                detailedStat.setTiepct(tiepct);
                detailedStats.add(detailedStat);
            }
        });

        Database.doVoidTransaction(baseSql + " where sq.playoff = 1 group by sq.player_id order by wins DESC", (pstmt) -> {
            List<PlayerDetailedStats> detailedStats = new ArrayList<>();
            playerDetailedStatsMap.put("playoff", detailedStats);
            final ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                String playerName = rs.getString("player_name");
                final PlayerDetailedStats detailedStat;
                if( playerDetailedGoalStatsMap.get("playoff").containsKey(playerName) ) {
                    detailedStat = playerDetailedGoalStatsMap.get("playoff").get(playerName);
                } else {
                    detailedStat = new PlayerDetailedStats();
                }
                detailedStat.setId(rs.getInt("player_id"));
                detailedStat.setName(rs.getString("player_name"));
                detailedStat.setNumSeasons(rs.getInt("num_seasons"));
                Integer totalGoals = detailedStat.getTotalGoals();
                String goalsPerSeason = "0";
                if( totalGoals != null ) {
                    goalsPerSeason = String.format("%.1f", (float)(totalGoals.floatValue() / detailedStat.getNumSeasons().floatValue()));
                }
                detailedStat.setAvgGoalsSeason(goalsPerSeason);
                detailedStat.setNumGames(rs.getInt("num_games"));
                detailedStat.setLeagueWinner(rs.getInt("leagueWinner"));
                detailedStat.setPlayoffWinner(rs.getInt("playoffWinner"));
                detailedStat.setWin(rs.getInt("wins"));
                detailedStat.setLoss(rs.getInt("losses"));
                detailedStat.setTie(rs.getInt("ties"));
                String winpct = String.format("%.1f", rs.getFloat("winpct"));
                String losspct = String.format("%.1f", rs.getFloat("losspct"));
                String tiepct = String.format("%.1f", rs.getFloat("tiepct"));
                detailedStat.setWinpct(winpct);
                detailedStat.setLosspct(losspct);
                detailedStat.setTiepct(tiepct);
                detailedStats.add(detailedStat);
            }
        });

        return playerDetailedStatsMap;
    }

}
