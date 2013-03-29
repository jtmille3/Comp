package com.sas.comp.hibernate;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Hibernate {

  private static EntityManager em;

  private Hibernate() {
  }

  public static EntityManager getInstance() {
    if (em == null) {
      final EntityManagerFactory emf = Persistence.createEntityManagerFactory("comp");
      Hibernate.em = emf.createEntityManager();
    }

    return Hibernate.em;
  }
}
