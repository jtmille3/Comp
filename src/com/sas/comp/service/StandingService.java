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

        // need empty standings for all teams or standings display will be empty when some teams have games played but others do not.
        // the empty standings will be overwritten below for teams who have had games played
        this.setAllEmptyStandings();

        Database.doVoidTransaction("SELECT * FROM standings order by season_id desc ,points desc, goal_differential desc, goals_for desc", (pstmt) -> {
            final ResultSet rs = pstmt.executeQuery();
            int rank = 0;
            Integer prevSeasonId = null;
            while (rs.next()) {
                Integer seasonId = rs.getInt("season_id");
                List<Standing>standings = allStandings.get(seasonId);
                if( seasonId != prevSeasonId ) {
                    rank = 1;
                } else {
                    rank++;
                }
                String team = rs.getString("team");
                // find the empty standing for team and overwrite its values with non-empty data
                Standing standing = standings.stream().filter(s -> s.getTeam().equals(team)).findFirst().get();
                standing.setRank(rank);
                standing.setTeam(team);
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
                prevSeasonId = seasonId;
            }
        });
        
    }
    
    public List<Standing> getStandings(final Integer seasonId) {
        final List<Standing> standings = allStandings.get(seasonId);
        return standings;
    }

    private void setAllEmptyStandings() {
        Database.doVoidTransaction("SELECT id, name, season_id FROM teams ORDER BY season_id, name", (pstmt) -> {
            final ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Integer seasonId = rs.getInt("season_id");
                List<Standing>standings = allStandings.get(seasonId);
                if( standings == null ) standings = new ArrayList<>();
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
                allStandings.put(seasonId, standings);
            }
        });
    }

}
