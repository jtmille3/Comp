package com.sas.comp.resource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.sas.comp.listener.Cache;
import com.sas.comp.models.Competitive;

@Path("competitive")
@Produces(MediaType.APPLICATION_JSON)
public class CompetitiveResource {

	@GET
	public Competitive find() {
		return Cache.getCache().getCompetitive();
	}
}
