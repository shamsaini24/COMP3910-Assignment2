CREATE DATABASE employeeTimesheet;
CREATE USER 'stock'@'localhost' IDENTIFIED BY 'check';
CREATE USER 'stock'@'%' IDENTIFIED BY 'check';
GRANT ALL PRIVILEGES ON employeeTimesheet.* TO 'stock'@'localhost' WITH GRANT OPTION;
GRANT ALL PRIVILEGES ON employeeTimesheet.* TO 'stock'@'%' WITH GRANT OPTION;
USE employeeTimesheet;

DROP TABLE IF EXISTS Employees;
CREATE TABLE Employees(EmpNum int, EmpUsername TINYTEXT, EmpName TINYTEXT);

INSERT INTO Employees VALUES( 0, "admin", "Admin");
INSERT INTO Employees VALUES( 1, "sham", "Sham");


DROP TABLE IF EXISTS Credentials;
CREATE TABLE Credentials(id int, EmpUsername TINYTEXT, EmpPassword TINYTEXT);

INSERT INTO Credentials VALUES (0, "admin", "admin");
INSERT INTO Credentials VALUES (1, "sham", "saini");


DROP TABLE IF EXISTS Timesheets;
CREATE TABLE Timesheets (id int, EmpUsername TINYTEXT, EndWeek DATE);

INSERT INTO Timesheets VALUES (0, "admin", "2019-10-04");
INSERT INTO Timesheets VALUES (1, "admin", "2019-10-11");
INSERT INTO Timesheets VALUES (2, "sham", "2019-10-04");
INSERT INTO Timesheets VALUES (3, "sham", "2019-10-11");


DROP TABLE IF EXISTS TimesheetRows;
CREATE TABLE TimesheetRows (id int, EmpUsername TINYTEXT, EndWeek Date, ProjectID int, WorkPackage TINYTEXT, SatHours int, SunHours int, MonHours int, TueHours int, WedHours int, ThursHours int, FriHours int, Notes TINYTEXT);

INSERT INTO TimesheetRows VALUES (0, "admin", "2019-10-04", "132", "AA123", 5, 5, 5, 5, 5, 5, 5, "admin test notes");
INSERT INTO TimesheetRows VALUES (1, "admin", "2019-10-04", "125", "AA122", 2, 10, 2, 2, 6, 1, 7, "admin test notes");
INSERT INTO TimesheetRows VALUES (2, "sham", "2019-10-04", "132", "AA123", 5, 5, 5, 5, 5, 5, 5, "sham test notes");
INSERT INTO TimesheetRows VALUES (3, "sham", "2019-10-11", "125", "AA122", 2, 10, 2, 2, 6, 1, 7, "sham test notes");

