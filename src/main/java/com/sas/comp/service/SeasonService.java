package com.sas.comp.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.sas.comp.models.Season;
import com.sas.comp.mysql.Database;

public class SeasonService {

	public List<Season> getSeasons() {
		final List<Season> seasons = new ArrayList<>();

        Database.doVoidTransaction("SELECT * FROM seasons ORDER BY id DESC", (pstmt) -> {
            final ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                seasons.add(this.seasonFromResultSet(rs));
            }
        });

        return seasons;
	}

    public Season find(final String name) {
        return Database.doReturnTransaction(Season.class, "SELECT name, id FROM seasons WHERE name = ?", (pstmt) -> {
            pstmt.setString(1, name);

            final ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return seasonFromResultSet(rs);
            } else {
                return null;
            }
        });
    }

    public void create(final Season season) {
        Database.doReturnTransaction(Season.class, "INSERT INTO seasons VALUES(NULL, ?)", (pstmt) -> {
            pstmt.setString(1, season.getName());
            pstmt.execute();
            return season;
        });
    }

    public Season read(final Integer id) {
        return Database.doReturnTransaction(Season.class, "SELECT * FROM seasons WHERE id = ?", (pstmt) -> {
            pstmt.setInt(1,id);
            final ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return seasonFromResultSet(rs);
            } else {
                return null;
            }
        });
    }

    public void update(final Season season) {
        Database.doVoidTransaction("UPDATE seasons SET name=? WHERE id = ?", (pstmt) -> {
            pstmt.setString(1, season.getName());
            pstmt.setInt(2, season.getId());
            pstmt.execute();
        });
    }

    public void delete(final Integer id) {
        Database.doVoidTransaction("DELETE FROM seasons where id = ?", (pstmt) -> {
           pstmt.setInt(1,id);
           pstmt.execute();
        });
    }

    private Season seasonFromResultSet(final ResultSet rs) throws SQLException {
        final Season season = new Season();
        season.setId(rs.getInt("id"));
        season.setName(rs.getString("name"));
        return season;
    }
}
