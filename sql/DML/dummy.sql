--liquibase formatted sql

--changeset tphipson:insert-dummy-player
INSERT INTO players (username) VALUES ('dummy-test-user');
--rollback DELETE FROM players WHERE "username" = 'dummy-test-user';