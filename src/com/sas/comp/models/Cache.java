package com.sas.comp.models;

import java.util.List;

public class Cache {

	private static Cache cache;

	private Cache() {

	}

	public static Cache getCache() {
		if (cache == null) {
			cache = new Cache();
		}

		return cache;
	}

	private List<Season> seasons;

	public List<Season> getSeasons() {
		return seasons;
	}

	public void setSeasons(final List<Season> seasons) {
		this.seasons = seasons;
	}
}
