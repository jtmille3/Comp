package com.sas.comp.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.sas.comp.models.Player;
import com.sas.comp.mysql.Database;

public class StatisticService {

  public List<Player> getPlayerStatistics(final Integer seasonId) {
    final List<Player> statistics = new ArrayList<Player>();

    try {
      final Connection conn = Database.getConnection();
      final PreparedStatement pstmt = conn
          .prepareStatement("SELECT * FROM player_statistics WHERE season_id = ? ORDER BY season_id, goals DESC, team_id, player_id");
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
        statistic.setPlayerId(rs.getInt("player_id"));
        statistic.setGoalie(rs.getBoolean("goalie"));
        statistic.setCaptain(rs.getBoolean("captain"));
        statistic.setCoCaptain(rs.getBoolean("co_captain"));
        statistics.add(statistic);
      }

      rs.close();
      pstmt.close();
      conn.close();
    } catch (final Exception e) {
      e.printStackTrace();
    }

    return statistics;
  }

  public List<Player> getGoalieStatistics(final Integer seasonId) {
    final List<Player> statistics = new ArrayList<Player>();

    try {
      final Connection conn = Database.getConnection();
      final PreparedStatement pstmt = conn
          .prepareStatement("SELECT * FROM goalie_statistics WHERE season_id = ? ORDER BY shutouts DESC");
      pstmt.setInt(1, seasonId);

      final ResultSet rs = pstmt.executeQuery();
      while (rs.next()) {
        final Player statistic = new Player();
        statistic.setName(rs.getString("player"));
        statistic.setTeam(rs.getString("team"));
        statistic.setGoalsAgainst(rs.getInt("against"));
        statistic.setShutouts(rs.getInt("shutouts"));
        statistics.add(statistic);
      }

      rs.close();
      pstmt.close();
      conn.close();
    } catch (final Exception e) {
      e.printStackTrace();
    }

    return statistics;
  }

  public List<Player> getPlayerStatistics() {
    final List<Player> statistics = new ArrayList<Player>();

    try {
      final Connection conn = Database.getConnection();
      final PreparedStatement pstmt = conn
          .prepareStatement("SELECT * FROM player_alltime_statistics ORDER BY goals DESC");

      final ResultSet rs = pstmt.executeQuery();
      int rank = 1;
      while (rs.next()) {
        final Player statistic = new Player();
        statistic.setRank(rank++);
        statistic.setName(rs.getString("player"));
        statistic.setLeagueWinner(rs.getInt("league"));
        statistic.setPlayoffWinner(rs.getInt("playoff"));
        statistic.setGoals(rs.getInt("goals"));
        statistics.add(statistic);
      }

      rs.close();
      pstmt.close();
      conn.close();
    } catch (final Exception e) {
      e.printStackTrace();
    }

    return statistics;
  }

  public List<Player> getGoalieStatistics() {
    final List<Player> statistics = new ArrayList<Player>();

    try {
      final Connection conn = Database.getConnection();
      final PreparedStatement pstmt = conn
          .prepareStatement("SELECT * FROM goalie_alltime_statistics ORDER BY shutouts desc");

      final ResultSet rs = pstmt.executeQuery();
      while (rs.next()) {
        final Player statistic = new Player();
        statistic.setName(rs.getString("player"));
        statistic.setGoalsAgainst(rs.getInt("against"));
        statistic.setShutouts(rs.getInt("shutouts"));
        statistics.add(statistic);
      }

      rs.close();
      pstmt.close();
      conn.close();
    } catch (final Exception e) {
      e.printStackTrace();
    }

    return statistics;
  }

}
