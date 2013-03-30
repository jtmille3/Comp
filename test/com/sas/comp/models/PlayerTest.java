package com.sas.comp.models;

import org.junit.Assert;
import org.junit.Test;

import com.sas.comp.hibernate.Hibernate;

public class PlayerTest {

  @Test
  public void testPlayer() {
    final Player player = Hibernate.getInstance().find(Player.class, 1);
    Assert.assertTrue(player != null);
    Assert.assertNotNull(player.getId());
    Assert.assertNotNull(player.getName());
    Assert.assertTrue(player.getScorers().size() > 0);
    Assert.assertTrue(player.getTeamPlayers().size() > 0);
  }
}
