package com.sas.comp.service;

import com.sas.comp.models.Player;
import com.sas.comp.mysql.Database;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class StatisticService {

    public List<Player> getPlayerStatistics(final Integer seasonId) {
        final List<Player> statistics = new ArrayList<>();

        Database.doVoidTransaction("SELECT * FROM player_statistics WHERE season_id = ? ORDER BY season_id, goals DESC, team_id, player_id", (pstmt) -> {
            pstmt.setInt(1, seasonId);

            final ResultSet rs = pstmt.executeQuery();
            int rank = 1;
            while (rs.next()) {
                final Player statistic = new Player();
                statistic.setRank(rank++);
                statistic.setTeam(rs.getString("team"));
                statistic.setName(rs.getString("player"));
                statistic.setGoals(rs.getInt("goals"));
                statistic.setTeamId(rs.getInt("team_id"));
                statistic.setId(rs.getInt("player_id"));
                statistic.setGoalie(rs.getBoolean("goalie"));
                statistic.setCaptain(rs.getBoolean("captain"));
                statistic.setCoCaptain(rs.getBoolean("co_captain"));
                statistics.add(statistic);
            }
        });

        return statistics;
    }

    public List<Player> getGoalieStatistics(final Integer seasonId) {
        final List<Player> statistics = new ArrayList<>();

        Database.doVoidTransaction("SELECT * FROM shutout_statistics WHERE season_id = ? and goalie = 1 ORDER BY shutouts DESC", (pstmt) -> {
            pstmt.setInt(1, seasonId);

            final ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                final Player statistic = new Player();
                statistic.setName(rs.getString("player"));
                statistic.setId(rs.getInt("player_id"));
                statistic.setTeam(rs.getString("team"));
                statistic.setGoalsAgainst(rs.getInt("against"));
                statistic.setShutouts(rs.getInt("shutouts"));
                statistics.add(statistic);
            }
        });
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

}
