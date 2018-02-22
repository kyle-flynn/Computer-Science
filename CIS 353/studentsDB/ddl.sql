SPOOL ddl.out
SET ECHO ON
/*
Homework: SQL/DDL
Author: Kyle Flynn
*/
-- --------------------------------------------------------------------
DROP TABLE Students;
--
CREATE TABLE Students
(
id INTEGER,
name CHAR(10) NOT NULL,
classification CHAR(10) NOT NULL,
hours INTEGER,
gpa NUMBER(3,2) NOT NULL,
mentor INTEGER,
--
-- IMPORTANT: use the names IC1, IC2, IC3, IC4, and IC5 as given below.
-- IC1:
-- Student Id is the primary key
CONSTRAINT IC1 PRIMARY KEY (id),
-- IC2:
-- Every student must be classified as 'freshman', 'sophomore',
-- 'junior', or 'senior'
CONSTRAINT IC2 CHECK (classification IN ('freshman', 'sophomore', 'junior', 'senior')),
-- IC3: The gpa must be between 0 and 4.0 (inclusive).
CONSTRAINT IC3 CHECK (gpa >= 0 AND gpa <= 4.0),
-- IC4:
-- To be classified as a 'junior',a student must have
-- completed between 55 and 84 hours (inclusive).
CONSTRAINT IC4 CHECK (NOT (classification = 'junior' AND (hours < 55 OR hours > 84))),
-- IC5:
-- Every mentor must be a student, and
-- A student may or may not have a mentor, and
-- If a mentor's row is deleted, then his/her students are
-- left without a mentor, and
-- Defer the spec of this IC so that a student’s record can be
-- inserted before his/her mentor’s record is inserted.
CONSTRAINT IC5 FOREIGN KEY (mentor) REFERENCES Students(id) ON DELETE SET NULL  DEFERRABLE INITIALLY DEFERRED
);
-- ------------------------------------------------------------------
-- TESTING THE SCHEMA
-- ------------------------------------------------------------------
-- Beginning of Transaction-1 (consisting of the next 5 INSERTs)
SET AUTOCOMMIT OFF
INSERT INTO Students VALUES (10, 'Joe', 'freshman', 15,2.8, 20);
INSERT INTO Students VALUES (20, 'Joyce', 'sophomore', 35, 3.7, 30);
INSERT INTO Students VALUES (30, 'Lisa', 'junior', 63, 3.5, 40);
INSERT INTO Students VALUES (40, 'George', 'senior', 82, 3.7, null);
INSERT INTO Students VALUES (50, 'Kim', 'junior', 54, 3.5, 40);
COMMIT;
-- End of Transaction-1
SELECT * FROM Students;
-- -------------------------------------------------------------------
-- Now treat every one of the following INSERTs as a transaction by itself. 
SET AUTOCOMMIT ON
INSERT INTO Students VALUES (20, 'John', 'freshman', 10, 3.5, 30);
INSERT INTO Students VALUES (null, 'nobody', 'freshman', 10, 3.5, 30);
INSERT INTO Students VALUES (60, null, 'freshman', 10, 3.5, 30);
INSERT INTO Students VALUES (62, 'Bob', 'Senior', 82, 3.7, null);
INSERT INTO Students VALUES (63, 'Allen', 'freshman', 10, 4.2, 30);
INSERT INTO Students VALUES (64, 'May', 'junior', 43, 3.7, 40);
INSERT INTO Students VALUES (74, 'Drew', 'junior', 85, 3.7, 40);
INSERT INTO Students VALUES (75, 'Jane', 'sophomore', 39, 3.9, 70);
INSERT INTO Students VALUES (41, 'David', 'senior', 93, 3.9, 40);
-- --------------------------------------------------------------------
COMMIT;
-- Inspect the table
SELECT * From Students;
--
DELETE FROM Students WHERE id = 40;
SELECT * From Students;
--
SET ECHO OFF
SPOOL OFF 
