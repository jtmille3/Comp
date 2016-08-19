package com.sas.comp.service;

import com.sas.comp.models.Standing;
import com.sas.comp.mysql.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class StandingService {

    public List<Standing> getStandings(final Integer seasonId) {
        final List<Standing> standings = getSeasonStandings(seasonId);
        if(standings.size() == 0) {
            return getEmptyStandings(seasonId);
        } else {
            return standings;
        }
    }

    private List<Standing> getEmptyStandings(final Integer seasonId) {
        final List<Standing> standings = new ArrayList<Standing>();

        Database.doVoidTransaction("SELECT id, name FROM teams WHERE season_id = ? ORDER BY name", (pstmt) -> {
            pstmt.setInt(1, seasonId);

            final ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                final Standing standing = new Standing();
                standing.setTeam(rs.getString("name"));
                standing.setTeamId(rs.getInt("id"));
                standing.setRank(0);
                standing.setPoints(0);
                standing.setWins(0);
                standing.setLosses(0);
                standing.setTies(0);
                standing.setGoalsFor(0);
                standing.setGoalsAgainst(0);
                standing.setGoalDifferential(0);
                standing.setShutouts(0);
                standings.add(standing);
            }
        });

        return standings;
    }

    private List<Standing> getSeasonStandings(final Integer seasonId) {
        final List<Standing> standings = new ArrayList<Standing>();

        Database.doVoidTransaction("SELECT * FROM standings WHERE season_id = ? order by points desc, goal_differential desc, goals_for desc", (pstmt) -> {
            pstmt.setInt(1, seasonId);

            final ResultSet rs = pstmt.executeQuery();
            int rank = 1;
            while (rs.next()) {
                final Standing standing = new Standing();
                standing.setRank(rank++);
                standing.setTeam(rs.getString("team"));
                standing.setPoints(rs.getInt("points"));
                standing.setWins(rs.getInt("wins"));
                standing.setLosses(rs.getInt("losses"));
                standing.setTies(rs.getInt("ties"));
                standing.setGoalsFor(rs.getInt("goals_for"));
                standing.setGoalsAgainst(rs.getInt("goals_against"));
                standing.setGoalDifferential(rs.getInt("goal_differential"));
                standing.setShutouts(rs.getInt("shutouts"));
                standing.setTeamId(rs.getInt("team_id"));
                standings.add(standing);
            }
        });

        return standings;
    }

}
