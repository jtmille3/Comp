-- upgrade.sql
-- upgrades the db schema/views to include more data needed by app

-- Issue #20
CREATE OR REPLACE VIEW `standings`  AS  select `game_summary`.`season_id` AS `season_id`,`game_summary`.`team` AS `team`,sum(`game_summary`.`points`) AS `points`,sum((case when (`game_summary`.`points` = 3) then 1 else 0 end)) AS `wins`,sum((case when (`game_summary`.`points` = 0) then 1 else 0 end)) AS `losses`,sum((case when (`game_summary`.`points` = 1) then 1 else 0 end)) AS `ties`,sum(`game_summary`.`goals_for`) AS `goals_for`,sum(`game_summary`.`goals_against`) AS `goals_against`,(sum(`game_summary`.`goals_for`) - sum(`game_summary`.`goals_against`)) AS `goal_differential`,sum(`game_summary`.`shutouts`) AS `shutouts`,`game_summary`.`team_id` AS `team_id`,`teams`.`leagueWinner` AS `leagueWinner`,`teams`.`playoffWinner` AS `playoffWinner` from `game_summary` JOIN `teams` ON `game_summary`.`team_id` = `teams`.`id` group by `game_summary`.`season_id`,`game_summary`.`team` order by `game_summary`.`season_id`,`points` desc,`goal_differential` desc,`goals_for` desc ;

-- Issue #30
CREATE OR REPLACE VIEW `player_alltime_statistics`  AS  select `player_statistics`.`player` AS `player`,`player_statistics`.`player_id` AS `player_id`,sum(`player_statistics`.`league`) AS `league`,sum(`player_statistics`.`playoff`) AS `playoff`,sum(`player_statistics`.`goals`) AS `goals`,count(player_statistics.season_id) AS seasonsPlayed from `player_statistics` group by `player_statistics`.`player_id` order by sum(`player_statistics`.`goals`) desc ;

-- Issue #22 and #38
CREATE or REPLACE VIEW 
player_statistics AS select s.id AS season_id,
t.id AS team_id,
p.id AS player_id,
s.name AS season,
t.name AS team,
p.name AS player,
t.leagueWinner AS league,
t.playoffWinner AS playoff,
count(gs.id) AS goals,
tp.isCaptain AS captain,
tp.isCoCaptain AS co_captain,
tp.isGoalie AS goalie 
from players p 
join team_player tp on (tp.player_id = p.id)
join teams t on (t.id = tp.team_id)
join seasons s on (s.id = t.season_id)
left join games g on (g.season_id = s.id)
left join goals gs on ((gs.game_id = g.id) and (gs.player_id = p.id))
group by s.id,
t.id,
p.id 
order by s.id,
count(gs.id) desc,
t.id,
p.id;

-- Issue #15
create or replace view
goalie_summary AS select s.id AS season_id,
p.id AS player_id,
p.name AS player,
t.name AS team,
g.id as game_id,
g.playoff as playoff,
g.away_score AS against,
(case when (g.away_score = 0) then 1 else 0 end) AS shutouts 
from players p 
join team_player pt on ((p.id = pt.player_id) and (pt.isGoalie = 1))
join teams t on (t.id = pt.team_id)
join games g on (g.home_team_id = t.id)
join seasons s on ((g.season_id = s.id) and (t.season_id = s.id)) 
union all 
select s.id AS season_id,
p.id AS player_id,
p.name AS player,
t.name AS team,
g.id as game_id,
g.playoff as playoff,
g.home_score AS against,
(case when (g.home_score = 0) then 1 else 0 end) AS shutouts 
from players p 
join team_player pt on ((p.id = pt.player_id) and (pt.isGoalie = 1))
join teams t on (t.id = pt.team_id)
join games g on (g.away_team_id = t.id)
join seasons s on ((g.season_id = s.id) and (t.season_id = s.id))
;

-- Issue #19
CREATE or REPLACE VIEW 
season_goal_totals AS 
select s.id AS season_id,
s.name AS season_name,
t.id AS team_id,
t.name AS team_name,
p.id AS player_id,
p.name AS player,
tp.isGoalie as goalie,
tp.isCaptain as captain,
tp.isCoCaptain as cocaptain,
count(gs.id) AS total_goals
from players p 
join team_player tp on (tp.player_id = p.id)
join teams t on (t.id = tp.team_id)
join seasons s on (s.id = t.season_id)
left join games g on (g.season_id = s.id)
left join goals gs on ((gs.game_id = g.id) and (gs.player_id = p.id))
group by s.id,
t.id,
p.id 
order by s.id,
count(gs.id) desc,
t.id,
p.id;
CREATE or REPLACE VIEW 
playoff_goal_totals AS 
select s.id AS season_id,
s.name AS season_name,
t.id AS team_id,
t.name AS team_name,
p.id AS player_id,
p.name AS player,
tp.isGoalie as goalie,
tp.isCaptain as captain,
tp.isCoCaptain as cocaptain,
g.playoff as playoff,
count(gs.id) AS total_goals
from players p 
join team_player tp on (tp.player_id = p.id)
join teams t on (t.id = tp.team_id)
join seasons s on (s.id = t.season_id)
left join games g on (g.season_id = s.id)
left join goals gs on ((gs.game_id = g.id) and (gs.player_id = p.id))
where g.playoff = 1
group by s.id,
t.id,
g.playoff,
p.id 
order by s.id,
count(gs.id) desc,
t.id,
p.id;
CREATE or REPLACE VIEW 
regular_season_goal_totals as
select s.id AS season_id,
s.name AS season_name,
t.id AS team_id,
t.name AS team_name,
p.id AS player_id,
p.name AS player,
tp.isGoalie as goalie,
tp.isCaptain as captain,
tp.isCoCaptain as cocaptain,
g.playoff as playoff,
count(gs.id) AS total_goals
from players p 
join team_player tp on (tp.player_id = p.id)
join teams t on (t.id = tp.team_id)
join seasons s on (s.id = t.season_id)
left join games g on (g.season_id = s.id)
left join goals gs on ((gs.game_id = g.id) and (gs.player_id = p.id))
where g.playoff = 0
group by s.id,
t.id,
g.playoff,
p.id 
order by s.id,
count(gs.id) desc,
t.id,
p.id;

