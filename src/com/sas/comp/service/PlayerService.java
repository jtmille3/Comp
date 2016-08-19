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
        return Database.doReturnTransaction(Player.class,"SELECT name, id FROM players WHERE name = ?", (pstmt) -> {
            pstmt.setString(1, name);
            final ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                final Player player = new Player();
                player.setName(rs.getString("name"));
                player.setId(rs.getInt("id"));
                return player;
            } else {
                return null;
            }
        });
    }

    public void save(final Player player) {
        Database.doVoidTransaction("INSERT INTO PLAYERS VALUES(NULL, ?)", (pstmt) -> {
            pstmt.setString(1, player.getName());
            pstmt.execute();
        });
    }
}
