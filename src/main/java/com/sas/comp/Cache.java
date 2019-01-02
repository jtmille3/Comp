package com.sas.comp;

import com.sas.comp.models.Competitive;

public class Cache {

	private static Cache cache;
	private Competitive competitive;

	private Cache() {

	}

	public static Cache getCache() {
		if (cache == null) {
			cache = new Cache();
		}
		return cache;
	}

	public Competitive getCompetitive() {
		return competitive;
	}

	public void setCompetitive(final Competitive competitive) {
		this.competitive = competitive;
	}
	
}
