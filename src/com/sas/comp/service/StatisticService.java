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
			final PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM player_statistics WHERE season_id = ?");
			pstmt.setInt(1, seasonId);

			final ResultSet rs = pstmt.executeQuery();
			int rank = 1;
			while (rs.next()) {
				final Statistic statistic = new Statistic();
				statistic.setRank(rank++);
				statistic.setTeam(rs.getString(2));
				statistic.setPlayer(rs.getString(3));
				statistic.setGoals(rs.getInt(4));
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
				statistic.setPlayer(rs.getString(2));
				statistic.setTeam(rs.getString(3));
				statistic.setGoalsAgainst(rs.getInt(4));
				statistic.setShutouts(rs.getInt(5));
				statistics.add(statistic);
			}
		} catch (final Exception e) {
			e.printStackTrace();
		}

		return statistics;
	}

}
