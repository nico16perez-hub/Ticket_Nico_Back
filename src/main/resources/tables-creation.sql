CREATE TABLE IF NOT EXISTS Users (
    id LONG PRIMARY KEY AUTO_INCREMENT NOT NULL,
    name VARCHAR(50) NOT NULL,
    surname VARCHAR(50) NOT NULL,
    user_name VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(64) NOT NULL,
    area VARCHAR(50) NOT NULL,
    role VARCHAR(50) NOT NULL
);

CREATE TABLE IF NOT EXISTS Tickets (
    id LONG PRIMARY KEY AUTO_INCREMENT NOT NULL,
    user_id LONG NOT NULL,
    user_name VARCHAR(50) NOT NULL,
    area VARCHAR(50) NOT NULL,
    ticket_date DATETIME NOT NULL,
    title VARCHAR(50) NOT NULL,
    type VARCHAR(50) NOT NULL,
    description VARCHAR(255) NOT NULL,
    solution TEXT,
    solved_by VARCHAR(50),
    solved_date DATETIME,
    image BLOB,
    closed BOOLEAN DEFAULT FALSE,
    important BOOLEAN DEFAULT FALSE,
    FOREIGN KEY (user_id) REFERENCES Users(id)
);

CREATE TABLE IF NOT EXISTS Works (
    id LONG PRIMARY KEY AUTO_INCREMENT NOT NULL,
    user_id LONG NOT NULL,
    user_name VARCHAR(50) NOT NULL,
    work_date DATETIME NOT NULL,
    title VARCHAR(50) NOT NULL,
    description LONGTEXT NOT NULL,
    image BLOB,
    FOREIGN KEY (user_id) REFERENCES Users(id)
);

CREATE TABLE IF NOT EXISTS Pending (
    id LONG PRIMARY KEY AUTO_INCREMENT NOT NULL,
    pending_date DATETIME NOT NULL,
    notes VARCHAR(255) NOT NULL,
    done BOOLEAN DEFAULT FALSE
);

CREATE TABLE IF NOT EXISTS Guides (
    id LONG PRIMARY KEY AUTO_INCREMENT NOT NULL,
    guide_title VARCHAR(50) NOT NULL,
    guide LONGTEXT NOT NULL,
    admin_only BOOLEAN DEFAULT FALSE
);

CREATE TABLE IF NOT EXISTS UserTickets (
    user_id LONG NOT NULL,
    ticket_id LONG NOT NULL,
    PRIMARY KEY (user_id, ticket_id),
    FOREIGN KEY (user_id) REFERENCES Users(id),
    FOREIGN KEY (ticket_id) REFERENCES Tickets(id)
);

CREATE TABLE IF NOT EXISTS UserWorks (
    user_id LONG NOT NULL,
    work_id LONG NOT NULL,
    PRIMARY KEY (user_id, work_id),
    FOREIGN KEY (user_id) REFERENCES Users(id),
    FOREIGN KEY (work_id) REFERENCES Works(id)
);
CREATE TABLE IF NOT EXISTS RecurringTasks (
    id VARCHAR(36) PRIMARY KEY NOT NULL,
    user_id LONG NOT NULL,
    title VARCHAR(120) NOT NULL,
    description LONGTEXT,
    FOREIGN KEY (user_id) REFERENCES Users(id)
);

CREATE TABLE IF NOT EXISTS DailyTasks (
    id VARCHAR(36) PRIMARY KEY NOT NULL,
    user_id LONG NOT NULL,
    user_name VARCHAR(120) NOT NULL,
    task_date DATE NOT NULL,
    type VARCHAR(30) NOT NULL,
    title VARCHAR(120) NOT NULL,
    description LONGTEXT,
    area VARCHAR(80) NOT NULL,
    FOREIGN KEY (user_id) REFERENCES Users(id)
);

CREATE TABLE IF NOT EXISTS Claims (
    id VARCHAR(36) PRIMARY KEY NOT NULL,
    user_id LONG NOT NULL,
    user_name VARCHAR(120) NOT NULL,
    claim_date DATE NOT NULL,
    title VARCHAR(120) NOT NULL,
    area VARCHAR(80) NOT NULL,
    claimant VARCHAR(120) NOT NULL,
    problem_type VARCHAR(120) NOT NULL,
    description LONGTEXT,
    solution LONGTEXT,
    images LONGTEXT,
    FOREIGN KEY (user_id) REFERENCES Users(id)
);

ALTER TABLE Claims ADD COLUMN IF NOT EXISTS claimant VARCHAR(120) DEFAULT '';
ALTER TABLE Claims ADD COLUMN IF NOT EXISTS problem_type VARCHAR(120) DEFAULT '';
ALTER TABLE Claims ADD COLUMN IF NOT EXISTS solution LONGTEXT;
ALTER TABLE Claims ADD COLUMN IF NOT EXISTS images LONGTEXT;

CREATE TABLE IF NOT EXISTS CompletedWorks (
    id VARCHAR(36) PRIMARY KEY NOT NULL,
    user_id LONG NOT NULL,
    user_name VARCHAR(120) NOT NULL,
    work_date DATE NOT NULL,
    title VARCHAR(120) NOT NULL,
    area VARCHAR(80) NOT NULL,
    description LONGTEXT,
    solution LONGTEXT,
    edited_by VARCHAR(120),
    edited_at DATETIME,
    FOREIGN KEY (user_id) REFERENCES Users(id)
);

ALTER TABLE CompletedWorks ADD COLUMN IF NOT EXISTS area VARCHAR(80) DEFAULT 'Sistemas';
ALTER TABLE CompletedWorks ADD COLUMN IF NOT EXISTS description LONGTEXT;
ALTER TABLE CompletedWorks ADD COLUMN IF NOT EXISTS solution LONGTEXT;
ALTER TABLE CompletedWorks ADD COLUMN IF NOT EXISTS edited_by VARCHAR(120);
ALTER TABLE CompletedWorks ADD COLUMN IF NOT EXISTS edited_at DATETIME;

ALTER TABLE DailyTasks ADD COLUMN IF NOT EXISTS area VARCHAR(80) DEFAULT 'Sistemas';
