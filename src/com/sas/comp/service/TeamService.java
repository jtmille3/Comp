package com.sas.comp.service;

import com.sas.comp.models.Team;
import com.sas.comp.models.Player;
import com.sas.comp.mysql.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * Created with IntelliJ IDEA.
 * User: jeff
 * Date: 3/15/14
 * Time: 9:59 AM
 * To change this template use File | Settings | File Templates.
 */
public class TeamService {

    public Team find(final String name, final Integer seasonId) {
        try {
            final Connection conn = Database.getConnection();
            final PreparedStatement pstmt = conn
                    .prepareStatement("SELECT name, id, season_id FROM teams WHERE name = ? and season_id=?");
            pstmt.setString(1, name);
            pstmt.setInt(2, seasonId);

            final ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                final Team team = new Team();
                team.setName(rs.getString("name"));
                team.setId(rs.getInt("id"));
                team.setSeasonId(rs.getInt("season_id"));

                rs.close();
                pstmt.close();
                conn.close();

                return team;
            } else {
                rs.close();
                pstmt.close();
                conn.close();

                return null;
            }
        } catch (final Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public void save(final Team team) {
        try {
            final Connection conn = Database.getConnection();
            final PreparedStatement pstmt = conn
                    .prepareStatement("INSERT INTO TEAMS VALUES(NULL, ?, ?, ?, ?)", PreparedStatement.RETURN_GENERATED_KEYS);
            pstmt.setInt(1, team.getSeasonId());
            pstmt.setString(2, team.getName());
            pstmt.setInt(3, 0);
            pstmt.setInt(4, 0);
            pstmt.execute();

            final ResultSet rs = pstmt.getGeneratedKeys();
            if(rs.next()) {
                team.setId(rs.getInt(1));
            }

            rs.close();
            pstmt.close();
            conn.close();
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }

    public Boolean isPlaying(final Team team, final Player player) {
        try {
            final Connection conn = Database.getConnection();
            final PreparedStatement pstmt = conn
                    .prepareStatement("SELECT * FROM team_player where team_id = ? and player_id = ?");
            pstmt.setInt(1, team.getId());
            pstmt.setInt(2, player.getPlayerId());

            final ResultSet rs = pstmt.executeQuery();

            if(rs.next()) {
                rs.close();
                pstmt.close();
                conn.close();

                return true;
            } else {
                rs.close();
                pstmt.close();
                conn.close();
            }
        } catch (final Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public void addPlayer(final Team team, final Player player) {
        try {
            final Connection conn = Database.getConnection();
            final PreparedStatement pstmt = conn
                    .prepareStatement("INSERT INTO team_player VALUES(NULL, ?, ?, ?, ?, ?)");
            pstmt.setInt(1, team.getId());
            pstmt.setInt(2, player.getPlayerId());
            pstmt.setBoolean(3, player.getGoalie());
            pstmt.setBoolean(4, player.getCaptain());
            pstmt.setBoolean(5, player.getCoCaptain());

            pstmt.execute();
            pstmt.close();
            conn.close();
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }
}
