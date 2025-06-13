BEGIN TRANSACTION;

DELETE FROM songs WHERE contributor = '101224986';
DELETE FROM cds WHERE contributor = '101224986';

INSERT OR IGNORE INTO cds (cd_id, title, artist, producer, year, contributor)
VALUES ('101224986CD1', 'Short n'' Sweet', 'Sabrina Carpenter', 'Julian Bunetta', 2024, '101224986'),
('101224986CD2', 'Cycles ', 'Frank Sinatra', 'Don Costa', 1968, '101224986');

INSERT INTO songs (title, composer, cd_id, track, contributor)
VALUES ('Taste', 'Sabrina Carpenter', '101224986CD1', 1, '101224986'),
('Please Please Please', 'Sabrina Carpenter', '101224986CD1', 2, '101224986'),
('Good Graces', 'Sabrina Carpenter', '101224986CD1', 3, '101224986'),
('Sharpest Tool', 'Sabrina Carpenter', '101224986CD1', 4, '101224986'),
('Coincidence', 'Sabrina Carpenter', '101224986CD1', 5, '101224986'),
('Bed Chem', 'Sabrina Carpenter', '101224986CD1', 6, '101224986'),
('Espresso', 'Sabrina Carpenter', '101224986CD1', 7, '101224986'),
('Dumb & Poetic', 'Sabrina Carpenter', '101224986CD1', 8, '101224986'),
('Slim Pickins', 'Sabrina Carpenter', '101224986CD1', 9, '101224986'),
('Juno', 'Sabrina Carpenter', '101224986CD1', 10, '101224986'),
('Lie to Girls', 'Sabrina Carpenter', '101224986CD1', 11, '101224986'),
('Don''t Smile', 'Sabrina Carpenter', '101224986CD1', 12, '101224986');

INSERT INTO songs (title, composer, cd_id, track, contributor)
VALUES ('Rain in My Heart', 'Teddy Randazzo, Victoria Pike', '101224986CD2', 1, '101224986'),
('From Both Sides, Now', 'Joni Mitchell', '101224986CD2', 2, '101224986'),
('Little Green Apples', 'Bobby Russell', '101224986CD2', 3, '101224986'),
('Pretty Colors', 'Al Gorgoni, Chip Taylor', '101224986CD2', 4, '101224986'),
('Cycles', 'Gayle Caldwell', '101224986CD2', 5, '101224986'),
('Wandering', 'Gayle Caldwell', '101224986CD2', 6, '101224986'),
('By the Time I Get to Phoenix', 'Jimmy Webb', '101224986CD2', 7, '101224986'),
('Moody River', 'Gary D. Bruce', '101224986CD2', 8, '101224986'),
('My Way of Life', 'Bert Kaempfert, Herb Rehbein, Carl Sigman', '101224986CD2', 9, '101224986'),
('Gentle on My Mind', 'John Hartford', '101224986CD2', 10, '101224986');

COMMIT;