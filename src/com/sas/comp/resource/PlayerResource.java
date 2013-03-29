package com.sas.comp.resource;

import java.util.Collection;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import com.sas.comp.hibernate.Hibernate;
import com.sas.comp.models.Player;

@Path("players")
@Produces("application/json")
public class PlayerResource {

  @GET
  public Collection<Player> findAll() {
    return Hibernate.getInstance().createQuery("FROM players", Player.class).getResultList();
  }

  @GET
  @Path("{id}")
  public Player find(@PathParam("id") final Long id) {
    return Hibernate.getInstance().find(Player.class, id);
  }
}
