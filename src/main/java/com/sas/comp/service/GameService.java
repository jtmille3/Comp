package com.sas.comp.service;

import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TreeMap;

import com.sas.comp.models.Game;
import com.sas.comp.models.Player;
import com.sas.comp.mysql.Database;

public class GameService {
    
    private TreeMap<Integer, List<Game>>allRegularSeason = new TreeMap<Integer, List<Game>>();
    private TreeMap<Integer, List<Game>>allPlayoff = new TreeMap<Integer, List<Game>>();
    
    public GameService() {
        this.populateAllSeasons();
    }
    
    public List<Game> getLeagueSchedule(final Integer seasonId) {
        return getSchedules(seasonId, false);
    }

    public List<Game> getPlayoffSchedule(final Integer seasonId) {
        return getSchedules(seasonId, true);
    }
    
    private void populateAllSeasons() {
        Database.doVoidTransaction("SELECT * FROM schedule order by season_id desc, playoff", (pstmt) -> {
            final ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Integer seasonId = rs.getInt("season_id");
                Boolean playoff = rs.getBoolean("playoff");
                List<Game> schedules = null;
                TreeMap<Integer, List<Game>> gameMap = null;
                if( playoff.booleanValue() ) {
                    gameMap = allPlayoff;
                } else {
                    gameMap = allRegularSeason;
                }
                schedules = gameMap.get(seasonId);
                if( schedules == null ) schedules = new ArrayList<Game>();
                schedules.add(processSingleGame(rs));
                gameMap.put(seasonId, schedules);
            }
        });
    }

    private List<Game> getSchedules(final Integer seasonId, final Boolean playoff) {
        TreeMap<Integer, List<Game>> gameMap = null;
        if( playoff.booleanValue() ) {
            gameMap = allPlayoff;
        } else {
            gameMap = allRegularSeason;
        }
        final List<Game>schedules = gameMap.get(seasonId);
        return schedules;
    }

    public List<Game> getSchedules() {
        final List<Game> schedules = new ArrayList<>();

        Database.doVoidTransaction("SELECT * FROM schedule ORDER BY date", (pstmt) -> {
            final ResultSet rs = pstmt.executeQuery();
            processGameResultSet(rs,schedules);
        });

        return schedules;
    }

    public List<Game> getSchedules(final Date date) {
        final List<Game> schedules = new ArrayList<>();
        Database.doVoidTransaction("SELECT * FROM schedule WHERE date between ? AND ? ORDER BY date", (pstmt) -> {
            pstmt.setTimestamp(1, new java.sql.Timestamp(date.getTime()));
            final Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            cal.add(Calendar.DATE, 1); // minus number would decrement the days

            pstmt.setTimestamp(2, new java.sql.Timestamp(cal.getTime().getTime()));
            final ResultSet rs = pstmt.executeQuery();
            processGameResultSet(rs,schedules);
        });

        return schedules;
    }

    public Game getGame(final Integer gameId) {
        return Database.doReturnTransaction(Game.class, "SELECT * FROM schedule WHERE game_id = ?", (pstmt) -> {
            pstmt.setInt(1, gameId);
            Game returnGame = null;
            final ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                returnGame = processSingleGame(rs);
            }
            return returnGame;
        });
    }

    public void updateScore(final Game game) {
        Database.doVoidTransaction("UPDATE games SET home_score = ?, away_score = ? WHERE id = ?", (pstmt) -> {
            pstmt.setInt(1, game.getHomeScore());
            pstmt.setInt(2, game.getAwayScore());
            pstmt.setInt(3, game.getId());
            pstmt.execute();
        });
    }

    public List<Player> getPlayers(final Integer gameId) {
        final List<Player> players = new ArrayList<>();

        Database.doVoidTransaction("SELECT * FROM game_players WHERE game_id = ? ORDER BY player", (pstmt) -> {
            pstmt.setInt(1, gameId);

            final ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                final Player player = new Player();
                player.setName(rs.getString("player"));
                player.setId(rs.getInt("player_id"));
                player.setTeamId(rs.getInt("team_id"));
                player.setGoals(rs.getInt("goals"));
                players.add(player);
            }
        });

        return players;
    }

    public Boolean isScheduled(final Game game) {
        return Database.doReturnTransaction(Boolean.class,"SELECT * FROM games WHERE season_id=? AND home_team_id=? AND away_team_id=? AND date=?", (pstmt) -> {
            pstmt.setInt(1, game.getSeasonId());
            pstmt.setInt(2, game.getHomeId());
            pstmt.setInt(3, game.getAwayId());
            pstmt.setTimestamp(4, new java.sql.Timestamp(game.getDate().getTime()));
            final ResultSet rs = pstmt.executeQuery();
            return rs.next();
        });
    }


    private void processGameResultSet(ResultSet rs, List<Game> games) throws SQLException {
        while (rs.next()) {
            games.add(this.processSingleGame(rs));
        }
    }

    private Game processSingleGame(ResultSet rs) throws SQLException {
        final Game schedule = new Game();
        schedule.setHome(rs.getString("home"));
        schedule.setHomeId(rs.getInt("home_id"));
        schedule.setAway(rs.getString("away"));
        schedule.setAwayId(rs.getInt("away_id"));
        schedule.setDate(rs.getTimestamp("date"));
        schedule.setId(rs.getInt("game_id"));

        schedule.setHomeScore(rs.getInt("home_score"));
        if (rs.wasNull()) {
            schedule.setHomeScore(null);
        }

        schedule.setAwayScore(rs.getInt("away_score"));
        if (rs.wasNull()) {
            schedule.setAwayScore(null);
        }
        return schedule;
    }

    public void save(final Game game) {
        Database.doReturnTransaction(Game.class, "INSERT INTO games VALUES(null, ?, ?, ?, ?, ?, ?, ?)", (pstmt) -> {
            pstmt.setInt(1, game.getSeasonId());
            pstmt.setInt(2, game.getHomeId());
            pstmt.setInt(3, game.getAwayId());
            pstmt.setTimestamp(4, new java.sql.Timestamp(game.getDate().getTime()));
            pstmt.setNull(5, Types.NULL);
            pstmt.setNull(6, Types.NULL);
            pstmt.setInt(7, 0);
            pstmt.execute();
            return game;
        });
    }
}
