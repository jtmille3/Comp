package com.sas.comp.models;

import org.junit.Assert;
import org.junit.Test;

import com.sas.comp.hibernate.Hibernate;

public class TeamPlayerTest {

  @Test
  public void testTeamPlayer() {
    final TeamPlayerId id = new TeamPlayerId();
    id.setPlayerId(1);
    id.setTeamId(1);

    final TeamPlayer teamPlayer = Hibernate.getInstance().find(TeamPlayer.class, id);
    Assert.assertNotNull(teamPlayer.getId());
    Assert.assertFalse(teamPlayer.getIsCaptain());
    Assert.assertFalse(teamPlayer.getIsCoCaptain());
    Assert.assertFalse(teamPlayer.getIsGoalie());
  }
}
