package com.sas.comp.service;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

import com.sas.comp.mysql.Database;
import org.junit.Assert;
import org.junit.Test;

import com.sas.comp.models.Season;

public class SeasonServiceTest {

    @Test
    public void testSeasons() {
        final SeasonService service = new SeasonService();
        final List<Season> seasons = service.getSeasons();
        Assert.assertNotNull(seasons);
        Assert.assertTrue(seasons.size() > 0);
    }

    @Test
    public void testSeasonEditing() {
        final SeasonService service = new SeasonService();

        //Get the max ID so we can compare the auto increment and then reset it after we're done so the test doesn't
        //cause that value to go up to weird numbers
        int maxID = Database.doReturnTransaction(Integer.class, "SELECT id FROM seasons ORDER BY id DESC LIMIT 1", (pstmt) -> {
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
            return -1;
        });
        //Ensure we got an id
        Assert.assertNotEquals(-1, maxID);
        try {
            //Create a season and make sure it's generated ID is our maxID + 1
            final Season season = new Season();
            season.setName("Test Season");
            service.create(season);
            Assert.assertEquals(maxID + 1, season.getId().intValue());

            //Change it's name and ensure it gets saved to the DB by checking a copy
            season.setName("Test Season 2");
            service.update(season);
            final Season seasonCopy = service.read(season.getId());
            Assert.assertEquals("Test Season 2", seasonCopy.getName());

            //Delete the season and make sure it's no longer there
            service.delete(season.getId());
            final Season seasonCopy2 = service.read(season.getId());
            Assert.assertNull(seasonCopy2);
        } finally {
            //Reset the auto increment number to maxID + 1
            //For some reason the regular alter table for increment was not working but this seems to.
            Database.doVoidTransaction("ALTER TABLE seasons MODIFY COLUMN id INT;", PreparedStatement::execute);
            Database.doVoidTransaction("ALTER TABLE seasons MODIFY COLUMN id INT auto_increment;", PreparedStatement::execute);
        }


    }
}
