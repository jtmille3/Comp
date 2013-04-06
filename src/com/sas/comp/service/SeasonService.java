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

		try {
			final Connection conn = Database.getConnection();
			final PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM seasons");
			final ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				final Season season = new Season();
				season.setId(rs.getInt(1));
				season.setName(rs.getString(2));
				seasons.add(season);
			}
		} catch (final Exception e) {
			e.printStackTrace();
		}
		return seasons;
	}

}
