-- Property FieldTrip Database

DROP SCHEMA IF EXISTS FieldTrip;
CREATE SCHEMA FieldTrip;


USE FieldTrip;

-- Create the tables

-- Trip: Is the main entity in diagram, here we record values 
--       for trip date, return and departure times, and fieldtrip ID.
CREATE TABLE Trip(
    FieldtripID      CHAR(4)     NOT NULL,
    tripdate         DATE,
    departuretime    VARCHAR(20), 
    returntime       VARCHAR(20),
    PRIMARY KEY(FieldtripID)
);

-- Destination: Here is the physical address, decription, and
--              cost for fieldtrip event (movies, shows,etc).
CREATE TABLE Destination (
	destinationid   CHAR(4)		NOT NULL,
    address         CHAR(100),
    description     CHAR(50),
    cost            INTEGER,
    PRIMARY KEY(address,destinationid)
);
-- Proceeds: Is the relationship between "trip" and "destination".
--           FildTrip "proceeds" to destination.
CREATE TABLE Proceeds(
    FieldtripID CHAR(4)     	NOT NULL,
    address     CHAR(100),
    destinationid CHAR(4)   	NOT NULL,
    PRIMARY KEY(FieldtripID,address,destinationid), 
    FOREIGN KEY(FieldtripID) REFERENCES Trip(FieldtripID),
    FOREIGN KEY (address,destinationid) REFERENCES Destination(address,destinationid)
);

-- Vehicle: Is the physical vehicle that will move the students and teachers
--          to and from their destination. Recorded values are vehicle 
--          type,cost, ID(primary), and number of vehicles needed.
CREATE TABLE  Vehicle(
    vehicleid   CHAR(4) NOT NULL,
    type        CHAR(10),
    vehicles    INTEGER,
    cost        INTEGER,
    PRIMARY KEY(vehicleid)
);

-- Transports: Is the relationship between "vehicle" and "trip".
--             The vehicle will "transport" the fieldtrip.
CREATE TABLE  Transports(
    vehicleid   CHAR(4) NOT NULL,
    FieldtripID CHAR(4) NOT NULL,
    PRIMARY KEY(vehicleid,FieldtripID),
    FOREIGN KEY(FieldtripID) REFERENCES Trip(FieldtripID),
    FOREIGN KEY (vehicleid) REFERENCES Vehicle(vehicleid)
);

-- Meal: Values recored for Meal are vendor name(primary), address,
--       type of food, and cost for food.
CREATE TABLE Meal (
    vname     CHAR(20),
    cost      INTEGER,
    address   CHAR(100),
    type      CHAR(20),
    PRIMARY KEY (vname)      
);

-- Feeds: This is the relationship between "meal" and "trip".
--        Meal "feeds" the fieldtrip.
CREATE TABLE Feeds (
    vname             CHAR(15),
    FieldtripID       CHAR(4) NOT NULL,
    PRIMARY KEY(vname,FieldtripID),
    FOREIGN KEY(FieldtripID) REFERENCES Trip(FieldtripID),
    FOREIGN KEY(vname) REFERENCES Meal(vname)  
);

-- Teacher: This entity records the teacher's first and last name, date of birth,
--          position, teaching grade level, and teacher ID(primary).
CREATE TABLE Teacher(
    teacherid   CHAR(4) NOT NULL,
    fname       CHAR(15),
    lname       CHAR(15),
    DOB         DATE,
    position    CHAR(25),
    grade       INTEGER,
    PRIMARY KEY(teacherid)
);

-- Creates: Is the relationship between "teacher" and "trip".
--          Teacher "creates" the fieldtrip.
CREATE TABLE Creates(
    teacherid   CHAR(4) NOT NULL,
    FieldtripID CHAR (4) NOT NULL,
    PRIMARY KEY(teacherid,FieldtripID),
    FOREIGN KEY(FieldtripID) REFERENCES Trip(FieldtripID),
    FOREIGN KEY (teacherid) REFERENCES Teacher(teacherid)
);

-- Student: This entity records studet's first and last name, 
--          current grade, and the student ID(primary).
CREATE TABLE Student(
    studentid   CHAR(4) NOT NULL,
    fname       CHAR(15),
    lname       CHAR(15),
    grade       INTEGER,
    PRIMARY KEY (studentid)
);

-- Supervises: Is the relationship between "teacher" and "student".
--             Teacher "supervises" the behavior of the students.
CREATE TABLE Supervises(
    teacherid CHAR(4) NOT NULL,
    studentid  CHAR(4) NOT NULL,
    FieldtripID CHAR(4) NOT NULL,
    PRIMARY KEY(teacherid,studentid,FieldtripID),
    FOREIGN KEY(FieldtripID) REFERENCES Trip(FieldtripID),
    FOREIGN KEY(studentid) REFERENCES Student(studentid),
    FOREIGN KEY(teacherid) REFERENCES Teacher(teacherid)
);

-- Attends: Is the relationship between the "trip" and "student" or "teacher".
--          Teacher/student "attends" the fieldtrip; may store count as well.
CREATE TABLE Attends(
    studentid CHAR(4) NOT NULL,
    FieldtripID CHAR(4) NOT NULL,
    PRIMARY KEY(studentid,FieldtripID),
    FOREIGN KEY(studentid) REFERENCES Student(studentid),
    FOREIGN KEY (FieldtripID) REFERENCES Trip(FieldtripID)
);

-- Insert the data

INSERT INTO Trip(FieldtripID,tripdate,departuretime,returntime) VALUES
    ('0001','2015-05-03','23:50:26','04:35:15'),
    ('0002','2015-07-23','10:35:26','5:05:32')
;

INSERT INTO Destination(destinationid, address,description,cost) VALUES
    ('0001','1101 East Eagle','Movie Theatre',1500),
    ('0002','701 Benson Drive','Mr.Gattis',1500)
;

INSERT INTO Proceeds(destinationid,FieldtripID,address) VALUES
    ('0001','0001','1101 East Eagle'),
    ('0002','0002','701 Benson Drive')
;

INSERT INTO Vehicle(type,vehicles,cost,vehicleid) VALUES
    ('Bus',5,500,'B001'),
    ('Bus',5,500,'B002')
;

INSERT INTO Transports(vehicleid,FieldtripID) VALUES
    ('B001','0001'),
    ('B002','0002')
;

INSERT INTO Meal(vname,cost,address,type) VALUES
    ('Mr.Gattis',10,'701 Benson Drive','Buffet'),
    ('Whataburger',8,'565 Klosner Blvd','Burger')
;

INSERT INTO Feeds(FieldtripID,vname) VALUES
    ('0001','Mr.Gattis'),
    ('0002','Whataburger')
;

INSERT INTO Teacher(teacherid,fname,lname,DOB,position,grade) VALUES
    ('MG07','Miguel','Garcia','1991-12-23','Math Teacher',8),
    ('JT07','Juan','Tellez','1993-06-23','Science Teacher',8)
;

INSERT INTO Creates(teacherid,FieldtripID) VALUES
    ('MG07','0001'),
    ('JT07','0002')
;

INSERT INTO Student(studentid,fname,lname,grade) VALUES
    ('BM08','Billy','Madison',8),
    ('TJ08','Tyrone','Jones',8)
;

INSERT INTO Supervises(teacherid,studentid,FieldtripID) VALUES
    ('MG07','BM08','0001'),
    ('JT07','TJ08','0002')
;

INSERT INTO Attends(studentid,FieldtripID) VALUES
    ('BM08','0001'),
    ('TJ08','0002')
;
