package com.sas.comp.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.sas.comp.models.Standing;
import com.sas.comp.mysql.Database;

public class StandingService {

	public List<Standing> getStandings(final Integer seasonId) {
		final List<Standing> standings = new ArrayList<Standing>();

		try {
			final Connection conn = Database.getConnection();
			final PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM standings WHERE season_id = ?");
			pstmt.setInt(1, seasonId);

			final ResultSet rs = pstmt.executeQuery();
			int rank = 1;
			while (rs.next()) {
				final Standing standing = new Standing();
				standing.setRank(rank++);
				standing.setTeam(rs.getString("team"));
				standing.setPoints(rs.getInt("points"));
				standing.setWins(rs.getInt("wins"));
				standing.setLosses(rs.getInt("losses"));
				standing.setTies(rs.getInt("ties"));
				standing.setGoalsFor(rs.getInt("goals_for"));
				standing.setGoalsAgainst(rs.getInt("goals_against"));
				standing.setGoalDifference(rs.getInt("goal_differential"));
				standing.setShutouts(rs.getInt("shutouts"));
				standing.setTeamId(rs.getInt("team_id"));
				standings.add(standing);
			}
		} catch (final Exception e) {
			e.printStackTrace();
		}

		return standings;
	}

}
