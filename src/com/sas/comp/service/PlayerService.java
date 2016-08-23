package com.sas.comp.service;

import com.sas.comp.models.Player;
import com.sas.comp.models.Season;
import com.sas.comp.mysql.Database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
                return playerFromResultSet(rs);
            } else {
                return null;
            }
        });
    }

    public void create(final Player player) {
        Database.doReturnTransaction(Player.class,"INSERT INTO players VALUES(NULL, ?)", (pstmt) -> {
            pstmt.setString(1, player.getName());
            pstmt.execute();
            return player;
        });
    }

    public List<Player> read() {
        final List<Player> players = new ArrayList<>();

        Database.doVoidTransaction("SELECT * FROM players ORDER BY id DESC", (pstmt) -> {
            final ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                players.add(this.playerFromResultSet(rs));
            }
        });

        return players;
    }

    public Player read(final Integer id) {
        return Database.doReturnTransaction(Player.class, "SELECT * FROM players WHERE id = ?", (pstmt) -> {
            pstmt.setInt(1,id);
            final ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return playerFromResultSet(rs);
            } else {
                return null;
            }
        });
    }

    public void update(final Player player) {
        Database.doVoidTransaction("UPDATE players SET name=? WHERE id = ?", (pstmt) -> {
            pstmt.setString(1, player.getName());
            pstmt.setInt(2, player.getId());
            pstmt.execute();
        });
    }

    public void delete(final Integer id) {
        Database.doVoidTransaction("DELETE FROM players where id = ?", (pstmt) -> {
            pstmt.setInt(1,id);
            pstmt.execute();
        });
    }

    private Player playerFromResultSet(ResultSet rs) throws SQLException{
        final Player player = new Player();
        player.setName(rs.getString("name"));
        player.setId(rs.getInt("id"));
        return player;
    }
}
