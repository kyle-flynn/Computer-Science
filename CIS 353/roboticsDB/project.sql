SPOOL project.out
SET ECHO ON
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
DROP TABLE 'match' CASCADE CONSTRAINTS;
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
  'state'    varchar2(2),
  city       varchar2(25),
  venue      varchar2(50)
);

CREATE TABLE team (
  teamNumber  number(4) PRIMARY KEY, 
  teamName    varchar2(100),
  teamOrigin  varchar2(100),
  'state'     varchar2(2),
  city        varchar2(25)
);

CREATE TABLE years_active (
  teamNumber  number(4) PRIMARY KEY,
  years       number(2)
);

CREATE TABLE registration (
  eventID     varchar(15) PRIMARY KEY, 
  teamNumber  number(4), 
  didPayFee   number(1)
);

CREATE TABLE awards (
  awardID     varchar(20) PRIMARY KEY, 
  eventID     varchar(15), 
  awardName   varchar(50), 
  teamNumber  number(4), 
  points      (2)
);

CREATE TABLE 'match' (
  matchID     varchar(20), 
  eventID     varchar(15), 
  teamNumber  number(4), 
  'level'     number(2)
);

CREATE TABLE match_participant (
  matchID     varchar(20),
  teamNumber  number(4),
  alliance    varchar(4),
  didShow     number(1)
);

/* In the DDL, every IC must have a unique name; e.g. IC5, IC10, IC15, etc. */
--
SET FEEDBACK OFF
< The INSERT statements that populate the tables>
/* Important: Keep the number of rows in each table small enough so that the results of your
queries can be verified by hand. See the Sailors database as an example. */
SET FEEDBACK ON
COMMIT;
--
< One query (per table) of the form: SELECT * FROM table; in order to print out your
database >
--
< The SQL queries>. Include the following for each query:
/* 1. A comment line stating the query number and the feature(s) it demonstrates
(e.g. – Q25 – correlated subquery).
2. A comment line stating the query in English.
3. The SQL code for the query. */
--

< The insert/delete/update statements to test the enforcement of ICs >
/* Include the following items for every IC that you test (Important: see the next section titled
“Submit a final report” regarding which ICs to test).
 A comment line stating: Testing: < IC name>
 A SQL INSERT, DELETE, or UPDATE that will test the IC. */
COMMIT;
--
SPOOL OFF