--liquibase formatted sql

--changeset tphipson:create-player-table
CREATE TABLE "players" (
  "player_id" SERIAL NOT NULL PRIMARY KEY,
  "username" NOT NULL varchar,
  "last_game_state" integer DEFAULT 0,
  "uid" varchar
);