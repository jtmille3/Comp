package com.sas.comp.service;

import com.sas.comp.models.Player;
import com.sas.comp.mysql.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * Created with IntelliJ IDEA.
 * User: jeff
 * Date: 3/15/14
 * Time: 10:00 AM
 * To change this template use File | Settings | File Templates.
 */
public class PlayerService {

    public Player find(final String name) {
        try {
            final Connection conn = Database.getConnection();
            final PreparedStatement pstmt = conn
                    .prepareStatement("SELECT name, id FROM players WHERE name = ?");
            pstmt.setString(1, name);

            final ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                final Player player = new Player();
                player.setName(rs.getString("name"));
                player.setPlayerId(rs.getInt("id"));

                rs.close();
                pstmt.close();
                conn.close();

                return player;
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

    public void save(final Player player) {
        try {
            final Connection conn = Database.getConnection();
            final PreparedStatement pstmt = conn
                    .prepareStatement("INSERT INTO PLAYERS VALUES(NULL, ?)", PreparedStatement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, player.getName());
            pstmt.execute();

            final ResultSet rs = pstmt.getGeneratedKeys();
            if(rs.next()) {
                player.setPlayerId(rs.getInt(1));
            }

            rs.close();
            pstmt.close();
            conn.close();
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }
}
