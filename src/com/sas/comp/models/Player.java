package com.sas.comp.models;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "players")
public class Player {

  @Id
  private Long id;
  private String name;

  public Long getId() {
    return id;
  }

  public void setId(final Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(final String name) {
    this.name = name;
  }
}
