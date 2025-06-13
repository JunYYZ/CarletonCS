PRAGMA foreign_keys=OFF;

DROP TABLE IF EXISTS cds_temp;
DROP TABLE IF EXISTS songs_temp;

CREATE TABLE IF NOT EXISTS cds_temp(
    cd_id TEXT PRIMARY KEY NOT NULL, 
    title TEXT NOT NULL,
    artist TEXT NOT NULL,
    producer TEXT DEFAULT NULL,
    year INTEGER,
    contributor TEXT
);

CREATE TABLE IF NOT EXISTS songs_temp(
    song_id INTEGER PRIMARY KEY NOT NULL,
    title TEXT NOT NULL,
    composer TEXT NOT NULL,
    cd_id TEXT NOT NULL,
    track INTEGER NOT NULL,
    contributor TEXT
);

--
.read myTunes_Ali.sql

INSERT OR IGNORE INTO cds_temp (cd_id, title, artist, producer, year, contributor) SELECT * FROM cds;

INSERT INTO songs_temp (title, composer, cd_id, track, contributor) SELECT title, composer, cd_id, track, contributer FROM songs;

DROP TABLE IF EXISTS cds;
DROP TABLE IF EXISTS songs;

--
.read myTunes_Fatemeh.sql

INSERT OR IGNORE INTO cds_temp (cd_id, title, artist, producer, year, contributor) SELECT * FROM cds;

INSERT INTO songs_temp (title, composer, cd_id, track, contributor) SELECT title, composer, cd_id, track, contributer FROM songs;

DROP TABLE IF EXISTS cds;
DROP TABLE IF EXISTS songs;

--
.read myTunes_Monica.sql

INSERT OR IGNORE INTO cds_temp (cd_id, title, artist, producer, year, contributor) SELECT * FROM cds;

INSERT INTO songs_temp (title, composer, cd_id, track, contributor) SELECT title, composer, cd_id, track, contributer FROM songs;

DROP TABLE IF EXISTS cds;
DROP TABLE IF EXISTS songs;

--
.read myTunes_Rezieh.sql

INSERT OR IGNORE INTO cds_temp (cd_id, title, artist, producer, year, contributor) SELECT * FROM cds;

INSERT INTO songs_temp (title, composer, cd_id, track, contributor) SELECT title, composer, cd_id, track, contributer FROM songs;

DROP TABLE IF EXISTS cds;
DROP TABLE IF EXISTS songs;

--
.read myTunes_Saman.sql

INSERT OR IGNORE INTO cds_temp (cd_id, title, artist, producer, year, contributor) SELECT * FROM cds;

INSERT INTO songs_temp (title, composer, cd_id, track, contributor) SELECT title, composer, cd_id, track, contributer FROM songs;

DROP TABLE IF EXISTS cds;
DROP TABLE IF EXISTS songs;

--
.read myTunes_Abdelghny.sql

INSERT OR IGNORE INTO cds_temp (cd_id, title, artist, producer, year, contributor) SELECT * FROM cds;

INSERT INTO songs_temp (title, composer, cd_id, track, contributor) SELECT title, composer, cd_id, track, contributer FROM songs;

DROP TABLE IF EXISTS cds;
DROP TABLE IF EXISTS songs;

-- just in case
DROP TABLE IF EXISTS cds;
DROP TABLE IF EXISTS songs;

ALTER TABLE cds_temp RENAME TO cds;
ALTER TABLE songs_temp RENAME TO songs;

PRAGMA foreign_keys=ON;