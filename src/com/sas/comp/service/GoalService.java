package com.sas.comp.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.sas.comp.models.Goal;
import com.sas.comp.mysql.Database;

public class GoalService {

	public List<Goal> getGoals() {
		final List<Goal> goals = new ArrayList<Goal>();

		try {
			final Connection conn = Database.getConnection();
			final PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM goal_summary");

			final ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				final Goal goal = new Goal();
				goal.setGameId(rs.getInt("game_id"));
				goal.setPlayer(rs.getString("player"));
				goal.setPlayerId(rs.getInt("player_id"));
				goal.setTeam(rs.getString("team"));
				goal.setTeamId(rs.getInt("team_id"));
				goals.add(goal);
			}

			rs.close();
			pstmt.close();
			conn.close();
		} catch (final Exception e) {
			e.printStackTrace();
		}

		return goals;
	}

}
