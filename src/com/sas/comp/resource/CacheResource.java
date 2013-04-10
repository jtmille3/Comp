package com.sas.comp.resource;

import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import com.sas.comp.listener.Cache;
import com.sas.comp.models.Competitive;
import com.sas.comp.service.CompetitiveService;

@Path("cache")
public class CacheResource {

	@Path("reset")
	public Response reset() {
		final CompetitiveService competitiveService = new CompetitiveService();
		final Competitive competitive = competitiveService.getCompetition();
		Cache.getCache().setCompetitive(competitive);

		return Response.noContent().status(Response.Status.OK).build();
	}
}
