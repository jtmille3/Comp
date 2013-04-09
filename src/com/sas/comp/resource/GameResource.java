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
import com.sas.comp.hibernate.model.Game;

@Path("games")
@Produces(MediaType.APPLICATION_JSON)
public class GameResource {

	@GET
	public List<Game> findAll() {
		return Hibernate.getInstance().createQuery("from games", Game.class).getResultList();
	}

	@GET
	@Path("{id}")
	public Game find(@PathParam("id") final Integer id) {
		return Hibernate.getInstance().find(Game.class, id);
	}

	@POST
	public Integer save(final Game game) {
		Hibernate.getInstance().persist(game);
		return game.getId();
	}

	@PUT
	@Path("{id}")
	public void update(final Game game) {
		Hibernate.getInstance().persist(game);
	}

	@DELETE
	@Path("{id}")
	public void destroy(@PathParam("id") final Integer id) {
		final Game game = Hibernate.getInstance().find(Game.class, id);
		if (game != null) {
			Hibernate.getInstance().remove(game);
		}
	}
}
