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

	public void addGoal(final Goal goal) {
		try {
			final Connection conn = Database.getConnection();
			final PreparedStatement pstmt = conn.prepareStatement("INSERT INTO GOALS(player_id, game_Id) VALUES(?, ?)");
			pstmt.setInt(1, goal.getPlayerId());
			pstmt.setInt(2, goal.getGameId());
			pstmt.execute();
			pstmt.close();
			conn.close();
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}

	public void removeGoal(final Goal goal) {
		try {
			final Connection conn = Database.getConnection();
			final PreparedStatement pstmt = conn
					.prepareStatement("DELETE FROM goals WHERE id = (SELECT g.id FROM (SELECT MAX(id) AS id FROM goals WHERE game_id = ? and player_id = ?) g);");
			pstmt.setInt(1, goal.getGameId());
			pstmt.setInt(2, goal.getPlayerId());
			pstmt.execute();
			pstmt.close();
			conn.close();
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}

}
