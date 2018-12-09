-- upgrade.sql
-- upgrades the db schema/views to include more data needed by app

-- Issue #20
CREATE OR REPLACE VIEW `standings`  AS  select `game_summary`.`season_id` AS `season_id`,`game_summary`.`team` AS `team`,sum(`game_summary`.`points`) AS `points`,sum((case when (`game_summary`.`points` = 3) then 1 else 0 end)) AS `wins`,sum((case when (`game_summary`.`points` = 0) then 1 else 0 end)) AS `losses`,sum((case when (`game_summary`.`points` = 1) then 1 else 0 end)) AS `ties`,sum(`game_summary`.`goals_for`) AS `goals_for`,sum(`game_summary`.`goals_against`) AS `goals_against`,(sum(`game_summary`.`goals_for`) - sum(`game_summary`.`goals_against`)) AS `goal_differential`,sum(`game_summary`.`shutouts`) AS `shutouts`,`game_summary`.`team_id` AS `team_id`,`teams`.`leagueWinner` AS `leagueWinner`,`teams`.`playoffWinner` AS `playoffWinner` from `game_summary` JOIN `teams` ON `game_summary`.`team_id` = `teams`.`id` group by `game_summary`.`season_id`,`game_summary`.`team` order by `game_summary`.`season_id`,`points` desc,`goal_differential` desc,`goals_for` desc ;

-- Issue #30
CREATE OR REPLACE VIEW `player_alltime_statistics`  AS  select `player_statistics`.`player` AS `player`,`player_statistics`.`player_id` AS `player_id`,sum(`player_statistics`.`league`) AS `league`,sum(`player_statistics`.`playoff`) AS `playoff`,sum(`player_statistics`.`goals`) AS `goals`,count(player_statistics.season_id) AS seasonsPlayed from `player_statistics` group by `player_statistics`.`player_id` order by sum(`player_statistics`.`goals`) desc ;
