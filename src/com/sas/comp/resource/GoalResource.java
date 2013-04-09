package com.sas.comp.resource;

import java.util.List;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.sas.comp.hibernate.Hibernate;
import com.sas.comp.hibernate.model.Goal;

@Path("goals")
@Produces(MediaType.APPLICATION_JSON)
public class GoalResource {

	@GET
	public List<Goal> findAll() {
		return Hibernate.getInstance().createQuery("from goals", Goal.class).getResultList();
	}

	@GET
	@Path("{id}")
	public Goal find(@PathParam("id") final Integer id) {
		return Hibernate.getInstance().find(Goal.class, id);
	}

	@POST
	public Integer save(final Goal Goal) {
		Hibernate.getInstance().persist(Goal);
		return Goal.getId();
	}

	@PUT
	@Path("{id}")
	public void update(final Goal Goal) {
		Hibernate.getInstance().persist(Goal);
	}

	@DELETE
	@Path("{id}")
	public void destroy(@PathParam("id") final Integer id) {
		final Goal goal = Hibernate.getInstance().find(Goal.class, id);
		if (goal != null) {
			Hibernate.getInstance().remove(goal);
		}
	}
}
