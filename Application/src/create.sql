DROP TABLE IF EXISTS nosliw.MINIAPP_USER;
DROP TABLE IF EXISTS nosliw.MINIAPP_GROUP;
DROP TABLE IF EXISTS nosliw.MINIAPP_MINIAPP;
DROP TABLE IF EXISTS nosliw.MINIAPP_USERGROUP;
DROP TABLE IF EXISTS nosliw.MINIAPP_USERAPP;
DROP TABLE IF EXISTS nosliw.MINIAPP_SETTING;

CREATE TABLE nosliw.MINIAPP_USER(
	ID   				VARCHAR(200)			NOT NULL ,
	NAME				VARCHAR(200)			NOT NULL,
	
	PRIMARY KEY (ID)	
);

CREATE TABLE nosliw.MINIAPP_GROUP(
	ID   				VARCHAR(200)			NOT NULL ,
	NAME				VARCHAR(200)			NOT NULL,
	DESCRIPTION			VARCHAR(500)		    NOT NULL,
	
	PRIMARY KEY (ID)	
);

CREATE TABLE nosliw.MINIAPP_USERGROUP(
	ID   				VARCHAR(200)			NOT NULL ,
	USERID				VARCHAR(200)			NOT NULL,
	GROUPID				VARCHAR(200)			NOT NULL,
	
	PRIMARY KEY (ID)	
);

CREATE TABLE nosliw.MINIAPP_MINIAPP(
	ID   				VARCHAR(200)			NOT NULL ,
	NAME				VARCHAR(200)			NOT NULL ,
	DATAOWNERTYPE		VARCHAR(200),			
	PRIMARY KEY (ID)	
);

CREATE TABLE nosliw.MINIAPP_USERAPP(
	ID   				VARCHAR(200)			NOT NULL ,
	USERID 				VARCHAR(200)			NOT NULL ,
	APPID 				VARCHAR(200)			NOT NULL ,
	APPNAME				VARCHAR(200)			NOT NULL ,
	GROUPID 			VARCHAR(200)			,
	
	PRIMARY KEY (USERID, APPID)	
);

CREATE TABLE nosliw.MINIAPP_SETTING(
	ID   				VARCHAR(200)			NOT NULL ,
	USERID 				VARCHAR(200)			NOT NULL ,
	COMPONENTTYPE		VARCHAR(200)			NOT NULL ,
	COMPONENTID			VARCHAR(200)			NOT NULL ,
	NAME				VARCHAR(200)			NOT NULL ,
	DATA 				TEXT				NOT NULL ,
	
	PRIMARY KEY (ID)	
);

INSERT INTO nosliw.MINIAPP_GROUP (ID, NAME, DESCRIPTION) VALUES ('SoccerGroup', 'SoccerGroup', 'no description');
INSERT INTO nosliw.MINIAPP_GROUP (ID, NAME, DESCRIPTION) VALUES ('SchoolGroup', 'SchoolGroup', 'no description');
INSERT INTO nosliw.MINIAPP_GROUP (ID, NAME, DESCRIPTION) VALUES ('SoccerForFun', 'Soccer For Fun', 'Soccer for fun group in Toronto');
INSERT INTO nosliw.MINIAPP_MINIAPP (ID, NAME, DATAOWNERTYPE) VALUES ('App_SoccerForFun_PlayerInformation', 'Player Information', 'group');
INSERT INTO nosliw.MINIAPP_MINIAPP (ID, NAME) VALUES ('Register', 'Register');
INSERT INTO nosliw.MINIAPP_MINIAPP (ID, NAME) VALUES ('Position', 'Position');
INSERT INTO nosliw.MINIAPP_MINIAPP (ID, NAME) VALUES ('SoccerApp1', 'SoccerApp1');
INSERT INTO nosliw.MINIAPP_MINIAPP (ID, NAME) VALUES ('SoccerApp2', 'SoccerApp2');
INSERT INTO nosliw.MINIAPP_MINIAPP (ID, NAME) VALUES ('SchoolApp1', 'SchoolApp1');
INSERT INTO nosliw.MINIAPP_MINIAPP (ID, NAME) VALUES ('SchoolApp2', 'SchoolApp2');
INSERT INTO nosliw.MINIAPP_MINIAPP (ID, NAME) VALUES ('MyRealtor', 'MyRealtor');
INSERT INTO nosliw.MINIAPP_MINIAPP (ID, NAME) VALUES ('Airplane Arrival', 'Airplane Arrival');
INSERT INTO nosliw.MINIAPP_MINIAPP (ID, NAME) VALUES ('AppMySchool', 'MySchool');
