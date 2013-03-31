package com.sas.comp.resource;

import java.util.Collection;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import com.sas.comp.hibernate.Hibernate;
import com.sas.comp.models.Season;

@Path("seasons")
@Produces("application/json")
public class SeasonResource {

	@GET
	public Collection<Season> findAll() {
		return Hibernate
				.getInstance()
				.createQuery("SELECT DISTINCT s FROM seasons s JOIN FETCH s.teams t JOIN FETCH s.games g ORDER BY s.name DESC",
						Season.class).getResultList();
	}

	@GET
	@Path("last")
	public Season findLast() {
		return Hibernate.getInstance()
				.createQuery("SELECT DISTINCT s FROM seasons s JOIN FETCH s.teams t JOIN FETCH s.games g ORDER BY s.id DESC", Season.class)
				.setMaxResults(1).getSingleResult();
	}

	@GET
	@Path("{id}")
	public Season find(@PathParam("id") final Integer id) {
		return Hibernate.getInstance().find(Season.class, id);
	}
}
