-- upgrade.sql
-- upgrades the db schema/views to include more data needed by app

-- team_summary_full for all team and captain stats
create or replace view team_summary_full as
select
sch.season_id,
seasons.name as season, 
sch.date,
sch.playoff,
sch.home_id as team_id,
sch.home as team,
p.id as captain_id, 
p.name as captain_name, 
sch.home_score as goals_for,
sch.away_score as goals_against,
sch.home_score - sch.away_score as goal_diff,
case 
  when (sch.home_score > sch.away_score) then 1 
  when (sch.home_score < sch.away_score) then -1 
  else 0 
end AS result,
case
  when (sch.away_score = 0) then 1
  else 0
end as shutout,
t.leagueWinner, t.playoffWinner
from schedule sch inner join seasons on sch.season_id = seasons.id
inner join team_player tp on tp.team_id = sch.home_id
inner join players p on p.id = tp.player_id
inner join teams t on sch.home_id = t.id
where (sch.home_score is not null and sch.away_score is not null)
and tp.isCaptain
union all
select 
sch.season_id,
seasons.name as season, 
sch.date,
sch.playoff,
sch.away_id as team_id,
sch.away as team,
p.id as captain_id, 
p.name as captain_name, 
sch.away_score as goals_for,
sch.home_score as goals_against,
sch.away_score - sch.home_score as goal_diff,
case 
  when (sch.away_score > sch.home_score) then 1 
  when (sch.away_score < sch.home_score) then -1 
  else 0 
end AS result,
case
  when (sch.home_score = 0) then 1
  else 0
end as shutout,
t.leagueWinner, t.playoffWinner
from schedule sch inner join seasons on sch.season_id = seasons.id
inner join team_player tp on tp.team_id = sch.away_id
inner join players p on p.id = tp.player_id
inner join teams t on sch.away_id = t.id
where (sch.home_score is not null and sch.away_score is not null)
and tp.isCaptain
order by date desc, team
