package com.sas.comp.resource;

import java.util.Collection;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import com.sas.comp.models.Cache;
import com.sas.comp.models.Season;

@Path("seasons")
@Produces("application/json")
public class SeasonResource {

	@GET
	public Collection<Season> findAll() {
		return Cache.getCache().getSeasons();
	}
}
