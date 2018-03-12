-- File: companyDB.sql 
--
/* This command file creates and populates the EMPLOYEES database in Elmasri and Navathe's book. 
*/
--
-- Drop the tables (in case they already exist)
--
DROP TABLE employee CASCADE CONSTRAINTS;
DROP TABLE department CASCADE CONSTRAINTS;
DROP TABLE dept_locations CASCADE CONSTRAINTS;
DROP TABLE project CASCADE CONSTRAINTS;
DROP TABLE works_on CASCADE CONSTRAINTS;
DROP TABLE dependent CASCADE CONSTRAINTS;
--
-- Create the tables
--
CREATE TABLE employee (
  fname     varchar2(15), 
  minit     char,
  lname     varchar2(15),
  ssn       char(9) PRIMARY KEY,
  bdate     date,
  address   varchar2(30),
  sex       char,
  salary    number(10,2),
  super_ssn char(9),
  dno       number(4)
);
--
CREATE TABLE department (
  dnumber      number(4) PRIMARY KEY,
  dname        varchar2(15),
  mgrssn       char(9), 
  mgrstartdate date
);
--
CREATE TABLE dept_locations (
  dnumber   number(4),
  dlocation varchar2(15), 
  primary key (dnumber,dlocation)
);
--
CREATE TABLE project (
  pname      varchar2(15),
  pnumber    number(4) PRIMARY KEY,
  plocation  varchar2(15),
  dnum       number(4)
);
--
CREATE TABLE works_on (
  essn   char(9),
  pno    number(4),
  hours  number(4,1),
  primary key (essn,pno)
);
--
CREATE TABLE dependent (
  essn           char(9),
  dependent_name varchar2(15),
  sex            char,
  bdate          date,
  relationship   varchar2(8),
  primary key (essn,dependent_name)
);
--
-- Add the foreign keys:
ALTER TABLE employee
ADD FOREIGN KEY (super_ssn) references employee(ssn)
Deferrable initially deferred;
ALTER TABLE employee
ADD FOREIGN KEY (dno) references department(dnumber)
Deferrable initially deferred;
ALTER TABLE department
ADD FOREIGN KEY (mgrssn) references employee(ssn)
Deferrable initially deferred;
ALTER TABLE dept_locations
ADD FOREIGN KEY (dnumber) references department(dnumber)
Deferrable initially deferred;
ALTER TABLE project
ADD FOREIGN KEY (dnum) references department(dnumber)
Deferrable initially deferred;
ALTER TABLE works_on
ADD FOREIGN KEY (essn) references employee(ssn)
Deferrable initially deferred;
ALTER TABLE works_on
ADD FOREIGN KEY (pno) references project(pnumber)
Deferrable initially deferred;
ALTER TABLE dependent
ADD FOREIGN KEY (essn) references employee(ssn)
Deferrable initially deferred;
--
-- ----------------------------------------------------------
-- Populate the database
-- ----------------------------------------------------------
--
alter session set  NLS_DATE_FORMAT = 'YYYY-MM-DD';
--
--
insert into employee values ('John', 'B', 'Smith', 123456789, '1965-01-09', '731 Fondren, Houston, TX', 'M', 30000, 333445555, 5 );
insert into employee values ('Franklin', 'T', 'Wong', 333445555,  '1955-12-08', '638 Voss, Houston, TX', 'M', 40000, 888665555, 5);
insert into employee values('Alicia', 'J', 'Zelaya', 999887777,  '1968-07-19', '3321 Castle, Spring, TX', 'F', 25000, 987654321, 4);
insert into employee values('Jennifer', 'S', 'Wallace', 987654321,  '1941-06-20', '291 Berry, Bellair, TX', 'F', 43000, 888665555, 4 );
insert into employee values('Ramesh', 'K', 'Narayan', 666884444,  '1962-09-15', '975 Fire Oak, Humble, TX', 'M', 38000, 333445555, 5);
insert into employee values('Joyce', 'A', 'English', 453453453,  '1972-07-31', '5631 Rice, Houston, TX', 'F', 25000, 333445555, 5);
insert into employee values('Ahmad', 'V', 'Jabbar', 987987987, '1969-03-29', '980 Dallas, Houston, TX', 'M', 25000, 987654321, 4);
insert into employee values ('James', 'E', 'Borg', 888665555, '1937-11-10', '450 Stone, Houston, TX', 'M', 55000, null, 1);
--
--
insert into department values (5, 'Research', 333445555, '1988-05-22' );
insert into department values (4, 'Administration', 987654321, '1995-01-01' );
insert into department values (1, 'Headquarters', 888665555, '1981-06-19' );
--
--
insert into dept_locations values ( 1, 'Houston' );
insert into dept_locations values ( 4, 'Stafford' );
insert into dept_locations values ( 5, 'Bellaire' );
insert into dept_locations values ( 5, 'Sugarland' );
insert into dept_locations values ( 5, 'Houston' );
--
--
insert into project values ( 'ProductX', 1, 'Bellaire', 5 );
insert into project values ( 'ProductY', 2, 'Sugarland', 5 );
insert into project values ( 'ProductZ', 3, 'Houston', 5 );
insert into project values ( 'Computerization', 10, 'Stafford', 4 );
insert into project values ( 'Reorganization', 20, 'Houston', 1 );
insert into project values ( 'Newbenefits', 30, 'Stafford', 4 );
--
--
insert into works_on values ( 123456789, 1, 32.5 );
insert into works_on values ( 123456789, 2, 7.5 );
insert into works_on values ( 666884444, 3, 40.0 );
insert into works_on values ( 453453453, 1, 20.0 );
insert into works_on values ( 453453453, 2, 20.0 );
insert into works_on values ( 333445555, 2, 10.0 );
insert into works_on values ( 333445555, 3, 10.0 );
insert into works_on values ( 333445555, 10, 10.0 );
insert into works_on values ( 333445555, 20, 10.0 );
insert into works_on values ( 999887777, 30, 30.0 );
insert into works_on values ( 999887777, 10, 10.0 );
insert into works_on values ( 987987987, 10, 35.0 );
insert into works_on values ( 987987987, 30, 5.0 );
insert into works_on values ( 987654321, 30, 20.0 );
insert into works_on values ( 987654321, 20, 15.0 );
insert into works_on values ( 888665555, 20, null );
--
--
insert into dependent values ( 333445555, 'Alice', 'F', '1986-04-05', 'Daughter' );
insert into dependent values ( 333445555, 'Theodore', 'M', '1983-10-25', 'Son' );
insert into dependent values ( 333445555, 'Joy', 'F', '1958-05-03', 'Spouse' );
insert into dependent values ( 987654321, 'Abner', 'M', '1942-02-28', 'Spouse' );
insert into dependent values ( 123456789, 'Michael', 'M', '1988-01-04', 'Son' );
insert into dependent values ( 123456789, 'Alice', 'F', '1988-12-30', 'Daughter' );
insert into dependent values ( 123456789, 'Elizabeth', 'F', '1967-05-05', 'Spouse' );
COMMIT;
