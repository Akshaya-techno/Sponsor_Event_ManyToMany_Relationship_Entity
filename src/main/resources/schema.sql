CREATE TABLE IF NOT EXISTS sponsor(
    id INTEGER PRIMARY KEY AUTO_INCREMENT,
    name TEXT,
    industry TEXT
);

CREATE TABLE IF NOT EXISTS event(
    id INTEGER PRIMARY KEY AUTO_INCREMENT,
    name TEXT,
    date TEXT
);

CREATE TABLE IF NOT EXISTS event_sponsor(
    sponsorId INTEGER,
    eventId INTEGER,
    PRIMARY KEY (sponsorId, eventId),
    FOREIGN KEY (sponsorId) REFERENCES sponsor(id),
    FOREIGN KEY (eventId) REFERENCES event(id)
);