package com.sas.comp.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.sas.comp.models.Player;
import com.sas.comp.mysql.Database;

public class GameService {
	public List<Player> getPlayers(final Integer gameId) {
		final List<Player> players = new ArrayList<Player>();

		try {
			final Connection conn = Database.getConnection();
			final PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM game_players WHERE game_id = ? ORDER BY player");
			pstmt.setInt(1, gameId);

			final ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				final Player player = new Player();
				player.setName(rs.getString("player"));
				player.setPlayerId(rs.getInt("player_id"));
				player.setTeamId(rs.getInt("team_id"));
				player.setGoals(rs.getInt("goals"));
				players.add(player);
			}

			rs.close();
			pstmt.close();
			conn.close();
		} catch (final Exception e) {
			e.printStackTrace();
		}

		return players;
	}
}
