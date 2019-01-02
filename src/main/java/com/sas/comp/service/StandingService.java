package com.sas.comp.service;

import com.sas.comp.models.Standing;
import com.sas.comp.mysql.Database;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

public class StandingService {
    
    private TreeMap<Integer, List<Standing>> allStandings = new TreeMap<Integer, List<Standing>>();
    
    public StandingService() {
        this.populateAllStandings();
    }
    
    private void populateAllStandings() {

        Database.doVoidTransaction("SELECT * FROM standings order by season_id desc ,points desc, goal_differential desc, goals_for desc", (pstmt) -> {
            final ResultSet rs = pstmt.executeQuery();
            int rank = 0;
            Integer prevSeasonId = null;
            while (rs.next()) {
                Integer seasonId = rs.getInt("season_id");
                List<Standing>standings = allStandings.get(seasonId);
                if( standings == null ) standings = new ArrayList<>();
                if( seasonId != prevSeasonId ) {
                    rank = 1;
                } else {
                    rank++;
                }
                Standing standing = new Standing();
                standing.setRank(rank);
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
                standing.setLeagueWinner(rs.getInt("leagueWinner"));
                standing.setPlayoffWinner(rs.getInt("playoffWinner"));
                standings.add(standing);
                allStandings.put(seasonId, standings);
                prevSeasonId = seasonId;
            }
        });
        
    }

    public List<Standing> getStandings(final Integer seasonId) {
        final List<Standing> standings = getSeasonStandings(seasonId);
        if(standings.size() == 0) {
            return getEmptyStandings(seasonId);
        } else {
            return standings;
        }
    }

    private List<Standing> getEmptyStandings(final Integer seasonId) {
        final List<Standing> standings = new ArrayList<>();

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
                standing.setLeagueWinner(0);
                standing.setPlayoffWinner(0);
                standings.add(standing);
            }
        });

        return standings;
    }

    private List<Standing> getSeasonStandings(final Integer seasonId) {
        final List<Standing> standings = allStandings.get(seasonId);
        return standings;
    }

}
