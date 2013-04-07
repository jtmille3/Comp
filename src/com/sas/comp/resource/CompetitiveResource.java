package com.sas.comp.resource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import com.sas.comp.listener.Cache;
import com.sas.comp.models.Competitive;

@Path("competitive")
@Produces("application/json")
public class CompetitiveResource {

	@GET
	public Competitive find() {
		return Cache.getCache().getCompetitive();
	}
}
