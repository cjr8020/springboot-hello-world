DROP TABLE actor IF EXISTS;

--CREATE schema testdb;

CREATE TABLE actor
(
	id        INTEGER PRIMARY KEY,
	username  VARCHAR2(50),
	email     VARCHAR2(50)

);