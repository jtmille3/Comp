package com.sas.comp.service;

import com.sas.comp.models.Team;
import com.sas.comp.models.Player;
import com.sas.comp.models.TeamPlayer;
import com.sas.comp.mysql.Database;

import java.sql.ResultSet;
import java.sql.SQLException;

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
                return teamFromResultSet(rs);
            } else {
                return null;
            }
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

    public TeamPlayer addPlayer(TeamPlayer player) {
        return Database.doReturnTransaction(TeamPlayer.class, "INSERT INTO team_player VALUES(NULL, ?, ?, ?, ?, ?)", (pstmt) -> {
            pstmt.setInt(1, player.getTeamId());
            pstmt.setInt(2, player.getPlayerId());
            pstmt.setBoolean(3, player.getIsGoalie());
            pstmt.setBoolean(4, player.getIsCaptain());
            pstmt.setBoolean(5, player.getIsCoCaptain());
            pstmt.execute();
            return player;
        });
    }

    public void updatePlayer(TeamPlayer player) {
        Database.doVoidTransaction("UPDATE team_player SET isGoalie = ?, isCaptain = ?, isCoCaptain = ? WHERE player_id = ? AND team_id = ?", (pstmt) -> {
            pstmt.setBoolean(1, player.getIsGoalie());
            pstmt.setBoolean(2, player.getIsCaptain());
            pstmt.setBoolean(3, player.getIsCoCaptain());
            pstmt.setInt(4, player.getPlayerId());
            pstmt.setInt(5, player.getTeamId());
            pstmt.execute();
        });
    }

    public void deletePlayer(TeamPlayer player) {
        Database.doVoidTransaction("DELETE FROM team_player WHERE player_id = ? AND team_id = ?", (pstmt) -> {
            pstmt.setInt(1, player.getPlayerId());
            pstmt.setInt(2, player.getTeamId());
            pstmt.execute();
        });
    }

    public void create(final Team team) {
        Database.doReturnTransaction(Team.class,"INSERT INTO teams VALUES(NULL, ?, ?, ?, ?)", (pstmt) -> {
            pstmt.setInt(1, team.getSeasonId());
            pstmt.setString(2, team.getName());
            pstmt.setBoolean(3, team.getLeagueWinner());
            pstmt.setBoolean(4, team.getPlayoffWinner());
            pstmt.execute();
            return team;
        });
    }

    public Team read(final Integer id) {
        return Database.doReturnTransaction(Team.class, "SELECT * FROM teams WHERE id = ?", (pstmt) -> {
            pstmt.setInt(1,id);
            final ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return teamFromResultSet(rs);
            } else {
                return null;
            }
        });
    }

    public void update(final Team team) {
        Database.doVoidTransaction("UPDATE teams SET name=?, leagueWinner=?, playoffWinner=? WHERE id = ?", (pstmt) -> {
            pstmt.setString(1, team.getName());
            pstmt.setBoolean(2, team.getLeagueWinner());
            pstmt.setBoolean(3, team.getPlayoffWinner());
            pstmt.setInt(4, team.getId());
            pstmt.execute();
        });
    }

    //TODO: Do we want to delete the team members here? -Philippe
    public void delete(final Integer id) {
        Database.doVoidTransaction("DELETE FROM teams where id = ?", (pstmt) -> {
            pstmt.setInt(1,id);
            pstmt.execute();
        });
    }

    private Team teamFromResultSet(final ResultSet rs) throws SQLException{
        final Team team = new Team();
        team.setName(rs.getString("name"));
        team.setId(rs.getInt("id"));
        team.setSeasonId(rs.getInt("season_id"));
        team.setLeagueWinner(rs.getBoolean("leagueWinner"));
        team.setPlayoffWinner(rs.getBoolean("playoffWinner"));
        return team;
    }
}
