package com.sas.comp.models;

import org.junit.Assert;
import org.junit.Test;

import com.sas.comp.hibernate.Hibernate;

public class ScorerTest {

  @Test
  public void testScorer() {
    final ScorerId id = new ScorerId();
    id.setGameId(6);
    id.setPlayerId(1);

    final Scorer scorer = Hibernate.getInstance().find(Scorer.class, id);
    Assert.assertNotNull(scorer.getId());
    Assert.assertNotNull(scorer.getGame());
    Assert.assertNotNull(scorer.getPlayer());
  }
}
