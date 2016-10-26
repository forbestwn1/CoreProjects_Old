CREATE TABLE nosliw.DATATYPEDEF(
	ID   				VARCHAR(20)			NOT NULL,
	NAME				VARCHAR(20)			NOT NULL,
	
	VERSION				VARCHAR(20),
	VERSION_MAJOR		INT,
	VERSION_MINOR		INT,
	VERSION_REVISION	VARCHAR(20),
	
	DESCRIPTION			VARCHAR(20),
	
	PARENTINFO			VARCHAR(20),
	
	LINKEDVERSION		VARCHAR(20),

	PRIMARY KEY (ID)	
);

