PRAGMA foreign_keys = ON;

CREATE TABLE "users" (
  "id"			INTEGER PRIMARY KEY,
  "email"		TEXT NOT NULL UNIQUE COLLATE NOCASE,
  "username" 	TEXT NOT NULL,
  "password" 	TEXT NOT NULL
);

CREATE TABLE "photos" (
  "id"			INTEGER PRIMARY KEY,
  "geolocation" TEXT,
  "title" TEXT NOT NULL,
  "comment" TEXT,
  "tag" TEXT
);

CREATE TABLE "users_photos_settings" (
  "user_id"		INTEGER NOT NULL,
  "photo_id"			INTEGER NOT NULL,
  "is_active_geolocation" INTEGER NOT NULL,
  FOREIGN KEY("user_id") REFERENCES "users"("id")
    ON UPDATE CASCADE ON DELETE CASCADE,
  FOREIGN KEY("photo_id") REFERENCES "photos"("id")
    ON UPDATE CASCADE ON DELETE CASCADE,
  UNIQUE("user_id","photo_id")
);

INSERT INTO "users" ("email", "username", "password")
VALUES
  ("admin@google.com", "admin", "123"),
  ("tester@google.com", "tester", "321");

