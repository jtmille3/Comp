package com.sas.comp.resource;

import com.sas.comp.hibernate.Hibernate;
import com.sas.comp.models.Team;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import java.util.Collection;

@Path("teams")
@Produces("application/json")
public class TeamResource {

	@GET
	public Collection<Team> findAll() {
		return Hibernate.getInstance().createQuery("FROM teams", Team.class).getResultList();
	}

	@GET
	@Path("{id}")
	public Team find(@PathParam("id") final Long id) {
		return Hibernate.getInstance().find(Team.class, id);
	}
}
