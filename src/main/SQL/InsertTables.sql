/*******************************************************************************
**	Name               01. Insert Tables
**	
**	Objective          The purpose of this script is to insert data into the tables for the
**					   information system to support academic activities.
**
**	Created by         Grupo 2 
**	Create Date        23/03/17
**
******************************************************************************/

use LS
insert into programme (acronym, name, numSemester)
	values	('LEIC', 'Licenciatura em Engenharia Informática e Computadores', 6),
			('LM', 'Licenciatura em Mecanica', 6)

/*****************************************************************************/
insert into academicSemester (academicYear, semesterTime)
	values	(1415, 'winter'),
			(1314, 'winter'),
			(1617, 'summer')

/*****************************************************************************/

insert into curricularSemester (curricularSemester)
	values	(1),
			(2),
			(3),
			(4),
			(5),
			(6)

/*****************************************************************************/

insert into teacher (number, email, name)
	values	(3111, 'david@gmail.com', 'David'),
			(3222, 'marco@gmail.com', 'Marco'),
			(9999, 'Leo@gmail.com', 'Leonardo')

/*****************************************************************************/

insert into student (number, pid, email, name)
	values	(4111, 'LEIC','pedro@gmail.com','Pedro'),
			(4222, 'LM', 'manel@gmail.com', 'Manel')

/*****************************************************************************/

insert into course (name, acronym, tNumber)
	values	('Laboratório de Software', 'LS', 3111),
			('Mecanismos 1', 'M1', 3222),
			('Redes', 'RCp', 9999),
			('Programação', 'PG', 3111)

/*****************************************************************************/
insert into courProgrCurr (pid, cName, curricularSemester, mandatory)
	values	('LEIC', 'Laboratório de Software', 2, 1),
			('LEIC', 'Programação', 1, 1),
			('LM', 'Mecanismos 1', 1, 0),
			('LEIC', 'Redes', 4, 1),
			('LM', 'Programação', 1, 1)

/*****************************************************************************/

insert into semCour (cName, aYear, aSemester)
	values	('Laboratório de Software', 1617, 'summer'),
			('Mecanismos 1', 1314, 'winter'),
			('Mecanismos 1', 1415, 'winter')

/*****************************************************************************/

insert into class (id, cName, aYear, aSemester)
	values	('D1', 'Laboratório de Software', 1617, 'summer'),
			('D1', 'Mecanismos 1', 1314, 'winter'),
			('D2', 'Laboratório de Software', 1617, 'summer'),
			('N1', 'Mecanismos 1', 1415, 'winter')

/*****************************************************************************/

insert into classTeacher (cId, cName, aYear, aSemester, tNumber)
	values	('D1', 'Laboratório de Software', 1617, 'summer', 3111),
			('N1', 'Mecanismos 1', 1415, 'winter', 3222)

/*****************************************************************************/

insert into classStudent (cId, cName, aYear, aSemester, sNumber)
	values	('D1', 'Laboratório de Software', 1617, 'summer', 4111),
			('N1', 'Mecanismos 1', 1415, 'winter', 4222)