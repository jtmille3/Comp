package com.sas.comp.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.sas.comp.models.Season;
import com.sas.comp.mysql.Database;

public class SeasonService {

	public List<Season> getSeasons() {
		final List<Season> seasons = new ArrayList<Season>();

        Database.doVoidTransaction("SELECT * FROM seasons ORDER BY id DESC", (pstmt) -> {
            final ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                final Season season = new Season();
                season.setId(rs.getInt("id"));
                season.setName(rs.getString("name"));
                seasons.add(season);
            }
        });

        return seasons;
	}

    public Season find(final String name) {
        return Database.doReturnTransaction(Season.class, "SELECT name, id FROM seasons WHERE name = ?", (pstmt) -> {
            pstmt.setString(1, name);

            final ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                final Season season = new Season();
                season.setName(rs.getString("name"));
                season.setId(rs.getInt("id"));
                return season;
            } else {
                return null;
            }
        });
    }

    public void save(final Season season) {
        Database.doVoidTransaction("INSERT INTO seasons VALUES(NULL, ?)", (pstmt) -> {
            pstmt.setString(1, season.getName());
            pstmt.execute();
        });
    }

}
