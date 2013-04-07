package com.sas.comp.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.sas.comp.models.Statistic;
import com.sas.comp.mysql.Database;

public class StatisticService {

	public List<Statistic> getPlayerStatistics(final Integer seasonId) {
		final List<Statistic> statistics = new ArrayList<Statistic>();

		try {
			final Connection conn = Database.getConnection();
			final PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM player_summary WHERE season_id = ?");
			pstmt.setInt(1, seasonId);

			final ResultSet rs = pstmt.executeQuery();
			int rank = 1;
			while (rs.next()) {
				final Statistic statistic = new Statistic();
				statistic.setRank(rank++);
				statistic.setTeam(rs.getString("team"));
				statistic.setPlayer(rs.getString("player"));
				statistic.setGoals(rs.getInt("goals"));
				statistic.setTeamId(rs.getInt("team_id"));
				statistic.setPlayerId(rs.getInt("player_id"));
				statistics.add(statistic);
			}
		} catch (final Exception e) {
			e.printStackTrace();
		}

		return statistics;
	}

	public List<Statistic> getGoalieStatistics(final Integer seasonId) {
		final List<Statistic> statistics = new ArrayList<Statistic>();

		try {
			final Connection conn = Database.getConnection();
			final PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM goalie_statistics WHERE season_id = ?");
			pstmt.setInt(1, seasonId);

			final ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				final Statistic statistic = new Statistic();
				statistic.setPlayer(rs.getString("player"));
				statistic.setTeam(rs.getString("team"));
				statistic.setGoalsAgainst(rs.getInt("against"));
				statistic.setShutouts(rs.getInt("shutouts"));
				statistics.add(statistic);
			}
		} catch (final Exception e) {
			e.printStackTrace();
		}

		return statistics;
	}

	public List<Statistic> getPlayerStatistics() {
		final List<Statistic> statistics = new ArrayList<Statistic>();

		try {
			final Connection conn = Database.getConnection();
			final PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM player_alltime_statistics");

			final ResultSet rs = pstmt.executeQuery();
			int rank = 1;
			while (rs.next()) {
				final Statistic statistic = new Statistic();
				statistic.setRank(rank++);
				statistic.setPlayer(rs.getString("player"));
				statistic.setLeagueWinner(rs.getInt("league"));
				statistic.setPlayoffWinner(rs.getInt("playoff"));
				statistic.setGoals(rs.getInt("goals"));
				statistics.add(statistic);
			}
		} catch (final Exception e) {
			e.printStackTrace();
		}

		return statistics;
	}

	public List<Statistic> getGoalieStatistics() {
		final List<Statistic> statistics = new ArrayList<Statistic>();

		try {
			final Connection conn = Database.getConnection();
			final PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM goalie_alltime_statistics");

			final ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				final Statistic statistic = new Statistic();
				statistic.setPlayer(rs.getString("player"));
				statistic.setGoalsAgainst(rs.getInt("against"));
				statistic.setShutouts(rs.getInt("shutouts"));
				statistics.add(statistic);
			}
		} catch (final Exception e) {
			e.printStackTrace();
		}

		return statistics;
	}

}
