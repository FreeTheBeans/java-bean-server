--liquibase formatted sql

--changeset tphipson:create-player-table
CREATE TABLE "players" (
  "player_id" SERIAL NOT NULL PRIMARY KEY,
  "username" varchar NOT NULL,
  "last_game_state" varchar DEFAULT 'start_state',
  "uid" varchar
);
--rollback DROP TABLE players;

--changeset tphipson:alter-player-default-state
ALTER TABLE "players" (
  "last_game_state" varchar DEFAULT 'startState'
);
--rollback tphipson:alter-player-default-state
ALTER TABLE "players" (
  "last_game_state" varchar DEFAULT 'start_state'
);
