package com.sas.comp.resource;

import com.sas.comp.hibernate.Hibernate;
import com.sas.comp.models.Season;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import java.util.Collection;

@Path("seasons")
@Produces("application/json")
public class SeasonResource {

	@GET
	public Collection<Season> findAll() {
		return Hibernate.getInstance().createQuery("FROM seasons", Season.class).getResultList();
	}

	@GET
	@Path("{id}")
	public Season find(@PathParam("id") final Long id) {
		return Hibernate.getInstance().find(Season.class, id);
	}
}
