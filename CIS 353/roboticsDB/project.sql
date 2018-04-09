SPOOL project.out
SET ECHO ON
SET DEFINE OFF
/*
CIS 353 - Database Design Project
Jarrod Parr
Kyle Flynn
Brendon Caywoood
Santiago Quirogas
*/
DROP TABLE district_ranking CASCADE CONSTRAINTS;
DROP TABLE team CASCADE CONSTRAINTS;
DROP TABLE event CASCADE CONSTRAINTS;
DROP TABLE "match" CASCADE CONSTRAINTS;
DROP TABLE match_participant CASCADE CONSTRAINTS;
DROP TABLE awards CASCADE CONSTRAINTS;
DROP TABLE years_active CASCADE CONSTRAINTS;
DROP TABLE registration CASCADE CONSTRAINTS;

CREATE TABLE district_ranking (
  rankID           number(3) PRIMARY KEY, 
  teamNumber       number(4),
  districtPoints   number(3),
  advancedToStates number(1)
);

CREATE TABLE event (
  eventID    varchar(15) PRIMARY KEY, 
  weekOfComp varchar2(100),
  eventName  varchar2(100),
  "state"    varchar2(2),
  city       varchar2(25),
  venue      varchar2(50)
);

CREATE TABLE team (
  teamNumber  number(4) PRIMARY KEY, 
  teamName    varchar2(100),
  teamOrigin  varchar2(100),
  "state"     varchar2(2),
  city        varchar2(25)
);

CREATE TABLE years_active (
  teamNumber  number(4) PRIMARY KEY,
  years       number(2)
);

CREATE TABLE registration (
  eventID     varchar2(15) PRIMARY KEY, 
  teamNumber  number(4), 
  didPayFee   number(1)
);

CREATE TABLE awards (
  awardID     varchar2(20) PRIMARY KEY, 
  eventID     varchar2(15), 
  awardName   varchar2(50), 
  teamNumber  number(4), 
  points      number(2)
);

CREATE TABLE "match" (
  matchID     varchar2(20), 
  eventID     varchar2(15), 
  "level"     number(2),
  matchName   varchar2(20),
  redScore    number(3),
  blueScore   number(3)
);

CREATE TABLE match_participant (
  matchID     varchar2(20),
  teamNumber  number(4),
  alliance    varchar2(4),
  didShow     number(1)
);

/* In the DDL, every IC must have a unique name; e.g. IC5, IC10, IC15, etc. */
--
SET FEEDBACK OFF
INSERT INTO team (teamNumber, teamName, teamOrigin, "state", city)VALUES (1, 'The Juggernauts', 'Molex & Oakland Schools', 'MI', 'Pontiac');
INSERT INTO team (teamNumber, teamName, teamOrigin, "state", city) VALUES (33, 'Killer Bees', 'Notre Dame Preparatory', 'MI', 'Auburn Hills');
INSERT INTO team (teamNumber, teamName, teamOrigin, "state", city) VALUES (67, 'The HOT Team', 'Huron Valley Schools', 'MI', 'Highland');
INSERT INTO team (teamNumber, teamName, teamOrigin, "state", city) VALUES (469, 'Las Guerrillas', 'Gormans Gallery & International Academy', 'MI', 'Bloomfield Hills');
INSERT INTO team (teamNumber, teamName, teamOrigin, "state", city) VALUES (1023, 'Bedford Express', 'Bedford Senior High School', 'MI', 'Bedford');
INSERT INTO team (teamNumber, teamName, teamOrigin, "state", city) VALUES (1918, 'NC Gears', 'Newaygo County', 'MI', 'Fremont');
INSERT INTO team (teamNumber, teamName, teamOrigin, "state", city) VALUES (2767, 'Stryke Force', 'Kalamazoo County', 'MI', 'Kalamazoo');
INSERT INTO team (teamNumber, teamName, teamOrigin, "state", city) VALUES (3618, 'Petoskey Paladins', 'Petoskey High School', 'MI', 'Petoskey');
INSERT INTO team (teamNumber, teamName, teamOrigin, "state", city) VALUES (4003, 'TriSonics', 'Allendale & Jenison Schools', 'MI', 'Allendale');
INSERT INTO team (teamNumber, teamName, teamOrigin, "state", city) VALUES (5980, 'East Grand Rapids Robotics', 'East Grand Rapids High School', 'MI', 'Grand Rapids');

INSERT INTO district_ranking (rankID, teamNumber, districtPoints, advancedToStates) VALUES (1, 4003, 147, 1);
INSERT INTO district_ranking (rankID, teamNumber, districtPoints, advancedToStates) VALUES (2, 2767, 146, 0);
INSERT INTO district_ranking (rankID, teamNumber, districtPoints, advancedToStates) VALUES (3, 1918, 142, 0);
INSERT INTO district_ranking (rankID, teamNumber, districtPoints, advancedToStates) VALUES (4, 33, 140, 0);
INSERT INTO district_ranking (rankID, teamNumber, districtPoints, advancedToStates) VALUES (5, 67, 139, 0);
INSERT INTO district_ranking (rankID, teamNumber, districtPoints, advancedToStates) VALUES (6, 1023, 137, 0);
INSERT INTO district_ranking (rankID, teamNumber, districtPoints, advancedToStates) VALUES (7, 5980, 130, 0);
INSERT INTO district_ranking (rankID, teamNumber, districtPoints, advancedToStates) VALUES (8, 3618, 100, 1);
INSERT INTO district_ranking (rankID, teamNumber, districtPoints, advancedToStates) VALUES (9, 1, 82, 0);
INSERT INTO district_ranking (rankID, teamNumber, districtPoints, advancedToStates) VALUES (10, 469, 79, 0);

INSERT INTO event (eventID, weekOfComp, eventName, "state", city, venue) VALUES ('18-FIM-TC', 1, 'Traverse City District Event', 'MI', 'Traverse City', 'Traverse City Central High School');
INSERT INTO event (eventID, weekOfComp, eventName, "state", city, venue) VALUES ('18-FIM-KU2', 2, 'Kettering University District Event #2', 'MI', 'Flint', 'Kettering University');
INSERT INTO event (eventID, weekOfComp, eventName, "state", city, venue) VALUES ('18-FIM-GULL', 3, 'Gull Lake District Event', 'MI', 'Richland', 'Gull Lake High School');
INSERT INTO event (eventID, weekOfComp, eventName, "state", city, venue) VALUES ('18-FIM-WMI', 4, 'West Michigan District Event', 'MI', 'Allendale', 'Grand Valley State University');
INSERT INTO event (eventID, weekOfComp, eventName, "state", city, venue) VALUES ('18-FIM-EK', 5, 'East Kentwood District Event', 'MI', 'East Kentwood', 'East Kentwood High School');
INSERT INTO event (eventID, weekOfComp, eventName, "state", city, venue) VALUES ('18-FIM-FH', 6, 'Forest Hills District Event', 'MI', 'Forest Hills', 'Forest Hills Central High School');
INSERT INTO event (eventID, weekOfComp, eventName, "state", city, venue) VALUES ('18-FIM-CMP', 7, 'Michigan State Championship', 'MI', 'Saginaw', 'Saginaw Valley State University');

INSERT INTO years_active (teamNumber, years) VALUES (1, 22);
INSERT INTO years_active (teamNumber, years) VALUES (33, 23);
INSERT INTO years_active (teamNumber, years) VALUES (67, 22);
INSERT INTO years_active (teamNumber, years) VALUES (469, 19);
INSERT INTO years_active (teamNumber, years) VALUES (1023, 16);
INSERT INTO years_active (teamNumber, years) VALUES (1918, 13);
INSERT INTO years_active (teamNumber, years) VALUES (2767, 10);
INSERT INTO years_active (teamNumber, years) VALUES (3618, 9);
INSERT INTO years_active (teamNumber, years) VALUES (4003, 8);
INSERT INTO years_active (teamNumber, years) VALUES (5980, 3);

INSERT INTO awards (awardID, eventID, awardName, teamNumber, points) VALUES ('18-FIM-WMI-CHM', '18-FIM-WMI', 'Chairmans Award', 4003, 10);
INSERT INTO awards (awardID, eventID, awardName, teamNumber, points) VALUES ('18-FIM-TC-EI', '18-FIM-EI', 'Engineering Inspiration', 3618, 8);
INSERT INTO awards (awardID, eventID, awardName, teamNumber, points) VALUES ('18-FIM-KU2-IC', '18-FIM-KU2', 'Innovation in Control', 4003, 5);
INSERT INTO awards (awardID, eventID, awardName, teamNumber, points) VALUES ('18-FIM-GULL-CHM', '18-FIM-CHM', 'Chairmans Award', 469, 10);
INSERT INTO awards (awardID, eventID, awardName, teamNumber, points) VALUES ('18-FIM-EK-IC', '18-FIM-EK', 'Innovation in Control', 2767, 5);
INSERT INTO awards (awardID, eventID, awardName, teamNumber, points) VALUES ('18-FIM-FH-WIN', '18-FIM-FH', 'Event Winner', 1023, 20);
INSERT INTO awards (awardID, eventID, awardName, teamNumber, points) VALUES ('18-FIM-CMP-WIN', '18-FIM-CMP', 'Event Winner', 4003, 60);

INSERT INTO registration (eventID, teamNumber, didPayFee) VALUES ('18-FIM-TC', 3618, true);
INSERT INTO registration (eventID, teamNumber, didPayFee) VALUES ('18-FIM-TC', 1918, true);

INSERT INTO registration (eventID, teamNumber, didPayFee) VALUES ('18-FIM-KU2', 4003, true);
INSERT INTO registration (eventID, teamNumber, didPayFee) VALUES ('18-FIM-KU2', 469, true);

INSERT INTO registration (eventID, teamNumber, didPayFee) VALUES ('18-FIM-GULL', 3618, true);
INSERT INTO registration (eventID, teamNumber, didPayFee) VALUES ('18-FIM-GULL', 469, true);

INSERT INTO registration (eventID, teamNumber, didPayFee) VALUES ('18-FIM-WMI', 4003, true);
INSERT INTO registration (eventID, teamNumber, didPayFee) VALUES ('18-FIM-WMI', 5980, true);

INSERT INTO registration (eventID, teamNumber, didPayFee) VALUES ('18-FIM-EK', 2767, true);
INSERT INTO registration (eventID, teamNumber, didPayFee) VALUES ('18-FIM-EK', 1, true);

INSERT INTO registration (eventID, teamNumber, didPayFee) VALUES ('18-FIM-FH', 33, true);
INSERT INTO registration (eventID, teamNumber, didPayFee) VALUES ('18-FIM-FH', 67, true);
INSERT INTO registration (eventID, teamNumber, didPayFee) VALUES ('18-FIM-FH', 1023, true);
INSERT INTO registration (eventID, teamNumber, didPayFee) VALUES ('18-FIM-FH', 503, true);

INSERT INTO "match" (matchID, eventID, "level", matchName, redScore, blueScore) VALUES ('18-FIM-FH-E01', '18-FIM-FH', 10, 'Quarterfinals 1 Match 1', 387, 398);
INSERT INTO "match" (matchID, eventID, "level", matchName, redScore, blueScore) VALUES ('18-FIM-FH-E02', '18-FIM-FH', 11, 'Quarterfinals 2 Match 1', 124, 50);
INSERT INTO "match" (matchID, eventID, "level", matchName, redScore, blueScore) VALUES ('18-FIM-FH-E03', '18-FIM-FH', 12, 'Quarterfinals 3 Match 1', 356, 354);
INSERT INTO "match" (matchID, eventID, "level", matchName, redScore, blueScore) VALUES ('18-FIM-FH-E04', '18-FIM-FH', 13, 'Quarterfinals 4 Match 1', 247, 247);

INSERT INTO match_participant (matchID, teamNumber, alliance, didShow) VALUES ('18-FIM-FH-E01', 1023, 'red', 1);
INSERT INTO match_participant (matchID, teamNumber, alliance, didShow) VALUES ('18-FIM-FH-E01', 67, 'blue', 1);

INSERT INTO match_participant (matchID, teamNumber, alliance, didShow) VALUES ('18-FIM-FH-E02', 33, 'red', 1);
INSERT INTO match_participant (matchID, teamNumber, alliance, didShow) VALUES ('18-FIM-FH-E02', 503, 'blue', 1);
/* Important: Keep the number of rows in each table small enough so that the results of your
queries can be verified by hand. See the Sailors database as an example. */
SET FEEDBACK ON
COMMIT;
--
SELECT * FROM team;
SELECT * FROM years_active;
SELECT * FROM event;
SELECT * FROM district_ranking;
SELECT * FROM registration;
SELECT * FROM awards;
SELECT * FROM "match";
SELECT * FROM match_participant;
--
--< The SQL queries>. Include the following for each query:
/* 1. A comment line stating the query number and the feature(s) it demonstrates
(e.g. – Q25 – correlated subquery).
2. A comment line stating the query in English.
3. The SQL code for the query. */
--

--< The insert/delete/update statements to test the enforcement of ICs >
/* Include the following items for every IC that you test (Important: see the next section titled
“Submit a final report” regarding which ICs to test).
 A comment line stating: Testing: < IC name>
 A SQL INSERT, DELETE, or UPDATE that will test the IC. */
COMMIT;
--
SPOOL OFF