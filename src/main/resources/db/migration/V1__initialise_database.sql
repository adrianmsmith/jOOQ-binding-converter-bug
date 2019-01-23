DROP SCHEMA mcve IF EXISTS CASCADE;

CREATE SCHEMA mcve;

DROP TABLE IF EXISTS mcve.test;

CREATE TABLE mcve.test (
  id    INT NOT NULL AUTO_INCREMENT,
  my_column VARCHAR(100),
  
  CONSTRAINT pk_test PRIMARY KEY (id) 
);

INSERT INTO mcve.test VALUES (42, 'StringInDatabase');