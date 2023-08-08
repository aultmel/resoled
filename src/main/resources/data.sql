
 SET foreign_key_checks = 0;
  TRUNCATE TABLE role;
 SET foreign_key_checks = 1;

INSERT INTO role (name, id) VALUES ("USER", 1);

INSERT INTO role (name, id)
VALUES ("ADMIN", 2);

