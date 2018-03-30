--
-- SQL/DML SELF-LEARNING HOMEWORK (using the COMPANY database)
-- File: company-IndependentLearning.sql
/*
The following queries are intended to promote independent learning and research (which is one of the objectives of this course.) More specifically, the topics of the queries in this assignment are not covered in typical introductory textbooks or courses on database. However, we have by now acquired sufficient knowledge in SQL to independently study the SQL manual. The topics covered here are:
--
-- Using ROWNUM (oracle's way of implementint the LIMIT clause in SQL)
-- Top-N reporting.
-- RANK and DENSE_RANK" functions.
-- Hierarchical queries.
--
In writing the queries, you MUST NOT hard-code the values of the database in your queries (e.g. a 25000 salary) since a database is subject to change after one writes a query.
--
IMPORTANT SPECIFICATIONS
--
(A)
Download the script file company.sql and use it to create your COMPANY database.
-- Downlnoad the file company.doc; it is provided for your convenience in order to check the results of your queries.
--
(B)
Implement the queries below by ***editing this file*** to include
your name and your SQL code in the indicated places.
--
(C)
After you have written the SQL code in the appropriate places, do the following:
--
-- Run this file (from the command line in sqlplus).
-- Edit the resulting spooled file (company-IndependentLearning.out)in order to change the font to size 9.
-- Print the .out file and submit the printout in class.
--
**** Note: you can use Apex to develop the individual queries. However, you ***MUST*** run this .sql file from the command line as explained above and then submit a printout of the spooled file. Submitting a printout of the webpage resulting from Apex will *NOT* be accepted.
--
*/
-- Please don't remove the SET ECHO command below.
SPOOL company-IndependentLearning.out
SET ECHO ON
-- ---------------------------------------------------------------
--
-- Name: Jarred Parr
--
/*(110) Using ROWNUM to limit the size of the result. (Notice that SQL and some systems use the LIMIT or TOP clauses. Oracle uses ROENUM to accomplish similar tasks.)
Find the ssn, lname, and salary of only four employees.
*/
SELECT ssn, lname, salary
FROM employee
WHERE ROWNUM <= 4;
--
/*(115) TOP-N query.
Find the ssn, lname, and salary of the four highest paid employees.
*/
SELECT ssn, lname, salary FROM (
  SELECT ssn, lname, salary, rank() over (ORDER BY salary desc) as rank
  FROM employee)
WHERE rank <= 4;
--
/*(120) TOP-N query.
Find the ssn, lname, and salary of the four lowest paid employees
*/
SELECT ssn, lname, salary  FROM (
  SELECT ssn, lname, salary, rank() over (ORDER BY salary) as rank
  FROM employee)
WHERE rank <= 4;
--
/*(125) TOP-N query.
Find the lowest two salaries in the company.(Notice that in our database, the two lowest salaries are 25K and 30K.)
*/
SELECT salary
FROM(
      SELECT DISTINCT salary 
      FROM employee
      ORDER BY salary ASC
    )
WHERE rownum <= 2
--
/*(130) TOP-N query.
For every employee whose salary is equal to one of the two lowest salaries, Find the ssn, lname, and salary.
*/
SELECT ssn, lname, salary
FROM employee
WHERE salary IN(
                SELECT *
                FROM(
                    SELECT DISTINCT salary
                    FROM employee
                    ORDER BY salary ASC
                    )
                WHERE ROWNUM <= 2
        )
--
/*(135) RANK query
Find the rank of the salary 30000 among all salaries. (HINT: The ranks in our database are 1 for 25000, 4 for 30000, 5 for 38000, and so on.)
*/
SELECT rank FROM(
    SELECT ssn, lname, salary, rank() over (ORDER BY salary) as rank
    FROM employee)
WHERE rank = 4 
--
/*(140) RANK query ... compare with the previous query.
Find the rank of the salary 31000 among all salaries.
*/
-- << write your sql code here >>>
--
/*(145) DENSE RANK query
Find the dense rank of the salary 30000 among all salaries. Hint: The dense ranks in our database are 1 for 25000, 2 for 30000, 3 for 38000, and so on.
*/
-- << write your sql code here >>>
--
/*(150) DENSE RANK query ... compare with the previous query.
Find the dense rank of the salary 31000 among all salaries. Hint: The dense ranks in our database are 1 for 25000, 2 for 30000, 3 for 38000, and so on.
*/
-- << write your sql code here >>>
--
/*(155)HIERARCHICAL query (uses START WITH and CONNECT BY PRIOR)
Find pairs of SSN's such that the first SSN in the pair is that of an employee while the second SSN in the pair is that of his/her supervisor. Start with SSN 453453453.
Hint: The output of your query should be:

453453453	333445555
333445555	888665555
888665555	-
*/
-- << write your sql code here >>>
---------------------------------------------------------------
SET ECHO OFF
SPOOL OFF
