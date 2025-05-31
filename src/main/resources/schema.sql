-- Artist table
CREATE TABLE artist (
                        id BIGINT PRIMARY KEY,
                        name VARCHAR(255) NOT NULL,
                        genre VARCHAR(255)
);

-- Album table
CREATE TABLE album (
                       id BIGINT PRIMARY KEY,
                       name VARCHAR(255) NOT NULL,
                       artist_id BIGINT NOT NULL,
                       FOREIGN KEY (artist_id) REFERENCES artist(id)
);

-- Track table
CREATE TABLE track (
                       id BIGINT PRIMARY KEY,
                       name VARCHAR(255) NOT NULL,
                       duration_sec INT,
                       path VARCHAR(500),
                       album_id BIGINT NOT NULL,
                       isFavorite BOOLEAN NOT NULL,
                       FOREIGN KEY (album_id) REFERENCES album(id)
);

-- Playlist table
CREATE TABLE playlist (
                          id BIGINT PRIMARY KEY,
                          name VARCHAR(255) NOT NULL,
                          creation_date DATE
);

-- PlaylistTrack junction table
CREATE TABLE playlist_track (
                                playlist_id BIGINT NOT NULL,
                                track_id BIGINT NOT NULL,
                                trackPlaylistNumber INT,
                                PRIMARY KEY (playlist_id, track_id),
                                FOREIGN KEY (playlist_id) REFERENCES playlist(id),
                                FOREIGN KEY (track_id) REFERENCES track(id)
);

CREATE SEQUENCE artist_id_seq START 1;
CREATE SEQUENCE album_id_seq START 1;
CREATE SEQUENCE track_id_seq START 1;
CREATE SEQUENCE playlist_id_seq START 1;
CREATE SEQUENCE playlist_track_id_seq START 1;