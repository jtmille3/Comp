package com.sas.comp.resource;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.sas.comp.models.Goal;
import com.sas.comp.service.GoalService;

@Path("goals")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class GoalResource {

	private final GoalService goalService = new GoalService();

	@POST
	public void save(final Goal goal) {
		goalService.addGoal(goal);
	}

	@DELETE
	public void destroy(final Goal goal) {
		goalService.removeGoal(goal);
	}
}
