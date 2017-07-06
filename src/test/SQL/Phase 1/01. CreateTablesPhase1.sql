/*******************************************************************************
**	Name               01. Create Tables for test tables
**	
**	Objective          The purpose of this script is the creation of tables for the
**					   information system to support academic activities.
**
**	Created by         Grupo 2 
**	Create Date        14/03/17
**
******************************************************************************/
USE LS_phase1_tests

CREATE TABLE programme (
	acronym VARCHAR(5) PRIMARY KEY,
	name VARCHAR(100) NOT NULL CONSTRAINT ak_programme UNIQUE,
	numSemester INT NOT NULL CHECK(numSemester > 0)
)

/*****************************************************************************/
CREATE TABLE academicSemester (
	academicYear INT,
	semesterTime VARCHAR(6) CHECK(semesterTime IN('winter','summer')),
	CONSTRAINT pk_academicSemester PRIMARY KEY (academicYear, semesterTime)
)

/*****************************************************************************/
CREATE TABLE curricularSemester (
	curricularSemester INT CHECK(curricularSemester > 0) PRIMARY KEY
)

/*****************************************************************************/
CREATE TABLE teacher (
	number INT PRIMARY KEY CHECK (number > 0),
	email VARCHAR(50),
	name VARCHAR(50) NOT NULL
)

/*****************************************************************************/
CREATE TABLE student (
	number INT PRIMARY KEY CHECK (number > 0),
	pid varchar(5) FOREIGN KEY REFERENCES programme(acronym),
	email VARCHAR(50),
	name VARCHAR(50) NOT NULL
)

/*****************************************************************************/
CREATE TABLE course (
	name VARCHAR(50) PRIMARY KEY,
	acronym VARCHAR(10) NOT NULL,
	tNumber INT FOREIGN KEY REFERENCES teacher(number)
)

/*****************************************************************************/
CREATE TABLE courProgrCurr (
	pid VARCHAR(5) FOREIGN KEY REFERENCES programme(acronym),
	cName VARCHAR(50) FOREIGN KEY REFERENCES course(name),
	curricularSemester INT FOREIGN KEY REFERENCES curricularSemester(curricularSemester),
	mandatory BIT NOT NULL,
	CONSTRAINT pk_courProgCurr PRIMARY KEY(pid, cName, curricularSemester),
)

/*****************************************************************************/
CREATE TABLE semCour (
	cName VARCHAR(50) FOREIGN KEY REFERENCES course(name),
	aSemester VARCHAR(6),
	aYear INT NOT NULL,
	CONSTRAINT pk_semCour PRIMARY KEY(cName, aYear, aSemester),
	CONSTRAINT fk_academicSemester FOREIGN KEY(aYear, aSemester) REFERENCES academicSemester(academicYear,semesterTime)
)

/*****************************************************************************/
CREATE TABLE class (
	id CHAR(2),
	cName VARCHAR(50),
	aYear INT,
	aSemester VARCHAR(6),
	CONSTRAINT pk_class PRIMARY KEY(id, cName, aYear, aSemester),
	CONSTRAINT fk_class_semCour FOREIGN KEY(cName, aYear, aSemester) REFERENCES semCour(cName, aYear, aSemester)
)

/*****************************************************************************/
CREATE TABLE classTeacher (
	cId CHAR(2),
	cName varchar(50),
	aYear INT,
	aSemester VARCHAR(6),
	tNumber INT FOREIGN KEY REFERENCES teacher(number),
	CONSTRAINT pk_classTeacher PRIMARY KEY(cId, cName, aYear, aSemester, tNumber),
	CONSTRAINT fk_classTeacher_class FOREIGN KEY(cId, cName, aYear, aSemester) REFERENCES class(id, cName, aYear, aSemester)
)

/*****************************************************************************/
CREATE TABLE classStudent (
	cId CHAR(2),
	cName varchar(50),
	aYear INT,
	aSemester VARCHAR(6),
	sNumber INT FOREIGN KEY REFERENCES student(number),
	CONSTRAINT pk_classStudent PRIMARY KEY(cId, cName, aYear, aSemester, sNumber),
	CONSTRAINT fk_classStudent_class FOREIGN KEY(cId, cName, aYear, aSemester) REFERENCES class(id, cName, aYear, aSemester)
)