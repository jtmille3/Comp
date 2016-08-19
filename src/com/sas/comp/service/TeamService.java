package com.sas.comp.service;

import com.sas.comp.models.Team;
import com.sas.comp.models.Player;
import com.sas.comp.mysql.Database;

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
        return Database.doReturnTransaction(Team.class,"SELECT name, id, season_id FROM teams WHERE name = ? and season_id=?", (pstmt) -> {
            pstmt.setString(1, name);
            pstmt.setInt(2, seasonId);

            final ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                final Team team = new Team();
                team.setName(rs.getString("name"));
                team.setId(rs.getInt("id"));
                team.setSeasonId(rs.getInt("season_id"));
                return team;
            } else {
                return null;
            }
        });
    }

    public void save(final Team team) {
        Database.doReturnTransaction(Team.class,"INSERT INTO TEAMS VALUES(NULL, ?, ?, ?, ?)", (pstmt) -> {
            pstmt.setInt(1, team.getSeasonId());
            pstmt.setString(2, team.getName());
            pstmt.setInt(3, 0);
            pstmt.setInt(4, 0);
            pstmt.execute();
            return team;
        });
    }

    public Boolean isPlaying(final Team team, final Player player) {
        return Database.doReturnTransaction(Boolean.class,"SELECT * FROM team_player where team_id = ? and player_id = ?", (pstmt) -> {
            pstmt.setInt(1, team.getId());
            pstmt.setInt(2, player.getId());

            final ResultSet rs = pstmt.executeQuery();
            return rs.next();
        });
    }

    public void addPlayer(final Team team, final Player player) {
        Database.doVoidTransaction("INSERT INTO team_player VALUES(NULL, ?, ?, ?, ?, ?)", (pstmt) -> {
            pstmt.setInt(1, team.getId());
            pstmt.setInt(2, player.getId());
            pstmt.setBoolean(3, player.getGoalie());
            pstmt.setBoolean(4, player.getCaptain());
            pstmt.setBoolean(5, player.getCoCaptain());

            pstmt.execute();
        });
    }
}
