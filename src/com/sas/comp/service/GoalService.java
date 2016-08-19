package com.sas.comp.service;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.sas.comp.models.Goal;
import com.sas.comp.mysql.Database;

public class GoalService {

	public List<Goal> getGoals() {
		final List<Goal> goals = new ArrayList<>();
        Database.doVoidTransaction("SELECT * FROM goal_summary", (pstmt) -> {
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
		});

		return goals;
	}

	public void addGoal(final Goal goal) {
        Database.doVoidTransaction("INSERT INTO GOALS(player_id, game_Id) VALUES(?, ?)", (pstmt) -> {
			pstmt.setInt(1, goal.getPlayerId());
			pstmt.setInt(2, goal.getGameId());
			pstmt.execute();
		});
	}

	public void removeGoal(final Goal goal) {
        Database.doVoidTransaction("DELETE FROM goals WHERE id = (SELECT g.id FROM (SELECT MAX(id) AS id FROM goals WHERE game_id = ? and player_id = ?) g);", (pstmt) -> {
			pstmt.setInt(1, goal.getGameId());
			pstmt.setInt(2, goal.getPlayerId());
			pstmt.execute();
		});
	}

}
