package com.sas.comp.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.sas.comp.hibernate.model.Team;
import com.sas.comp.models.Season;
import com.sas.comp.mysql.Database;

public class SeasonService {

	public List<Season> getSeasons() {
		final List<Season> seasons = new ArrayList<Season>();

		try {
			final Connection conn = Database.getConnection();
			final PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM seasons ORDER BY id DESC");
			final ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				final Season season = new Season();
				season.setId(rs.getInt("id"));
				season.setName(rs.getString("name"));
				seasons.add(season);
			}

			rs.close();
			pstmt.close();
			conn.close();
		} catch (final Exception e) {
			e.printStackTrace();
		}
		return seasons;
	}

    public Season find(final String name) {
        try {
            final Connection conn = Database.getConnection();
            final PreparedStatement pstmt = conn
                    .prepareStatement("SELECT name, id FROM seasons WHERE name = ?");
            pstmt.setString(1, name);

            final ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                final Season season = new Season();
                season.setName(rs.getString("name"));
                season.setId(rs.getInt("id"));

                rs.close();
                pstmt.close();
                conn.close();

                return season;
            } else {
                rs.close();
                pstmt.close();
                conn.close();

                return null;
            }
        } catch (final Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public void save(final Season season) {
        try {
            final Connection conn = Database.getConnection();
            final PreparedStatement pstmt = conn
                    .prepareStatement("INSERT INTO seasons VALUES(NULL, ?)", PreparedStatement.RETURN_GENERATED_KEYS);
            pstmt.setString(2, season.getName());
            pstmt.execute();

            final ResultSet rs = pstmt.getGeneratedKeys();
            if(rs.next()) {
                season.setId(rs.getInt(1));
            }

            rs.close();
            pstmt.close();
            conn.close();
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }

}
