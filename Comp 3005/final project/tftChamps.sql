    -- Team_Composition Table
    CREATE TABLE IF NOT EXISTS Team_Composition (
        teamName TEXT PRIMARY KEY,
        winrate REAL
    );

    -- Player Table
    CREATE TABLE IF NOT EXISTS Player (
        playerID INTEGER PRIMARY KEY AUTOINCREMENT,
        name TEXT NOT NULL,
        health INTEGER,
        podium_position INTEGER,
        winrate REAL,
        teamName TEXT,
        FOREIGN KEY (teamName) REFERENCES Team_Composition(teamName)
    );

    -- Augments Table
    CREATE TABLE IF NOT EXISTS Augments (
        augmentID INTEGER PRIMARY KEY AUTOINCREMENT,
        name TEXT NOT NULL,
        effect TEXT
    );

    -- Champions Table
    CREATE TABLE IF NOT EXISTS Champions (
        championID INTEGER PRIMARY KEY AUTOINCREMENT,
        name TEXT NOT NULL,
        ability TEXT,
        ad REAL,
        ap REAL,
        attack_speed REAL,
        hp REAL,
        armor REAL,
        magic_resist REAL,
        mana REAL,
        manaCost REAL,
        attackRange Real,
        cost INTEGER
    );

    -- Classes Table
    CREATE TABLE IF NOT EXISTS Classes (
        classID INTEGER PRIMARY KEY AUTOINCREMENT,
        name TEXT NOT NULL,
        effect TEXT,
        maxAmount INTEGER
    );

    -- Origins Table
    CREATE TABLE IF NOT EXISTS Origins (
        originID INTEGER PRIMARY KEY AUTOINCREMENT,
        name TEXT NOT NULL,
        effect TEXT,
        maxAmount INTEGER,
        boolUnique BOOLEAN
    );

    -- Items Table
    CREATE TABLE IF NOT EXISTS Items (
        itemID INTEGER PRIMARY KEY AUTOINCREMENT,
        name TEXT NOT NULL,
        type TEXT,
        ad REAL,
        ap REAL,
        attack_speed REAL,
        hp REAL,
        armor REAL,
        magic_resist REAL,
        mana REAL,
        effect TEXT
    );

    -- Components Table
    CREATE TABLE IF NOT EXISTS Components (
        componentID INTEGER PRIMARY KEY AUTOINCREMENT,
        name TEXT NOT NULL,
        type TEXT,
        ad REAL,
        ap REAL,
        attack_speed REAL,
        hp REAL,
        armor REAL,
        magic_resist REAL,
        mana REAL
    );

    -- Effects Table
    CREATE TABLE IF NOT EXISTS Effects (
        effectID INTEGER PRIMARY KEY AUTOINCREMENT,
        effect_name TEXT NOT NULL,
        effect_description TEXT,
        effect_type TEXT
    );

    -- Roles Table
    CREATE TABLE IF NOT EXISTS Roles (
        roleID INTEGER PRIMARY KEY AUTOINCREMENT,
        role_name TEXT NOT NULL,
        role_type TEXT,
        role_description TEXT
    );

    -- Player_Augments Table (N:3 relationship)
    CREATE TABLE IF NOT EXISTS Player_Augments (
        playerID INTEGER,
        augmentID INTEGER,
        PRIMARY KEY (playerID, augmentID),
        FOREIGN KEY (playerID) REFERENCES Player(playerID),
        FOREIGN KEY (augmentID) REFERENCES Augments(augmentID)
    );

    -- Player_Items Table (N:N relationship)
    CREATE TABLE IF NOT EXISTS Player_Items (
        playerID INTEGER,
        itemID INTEGER,
        PRIMARY KEY (playerID, itemID),
        FOREIGN KEY (playerID) REFERENCES Player(playerID),
        FOREIGN KEY (itemID) REFERENCES Items(itemID)
    );

    -- Player_Champions Table (N:N relationship)
    CREATE TABLE IF NOT EXISTS Player_Champions (
        playerID INTEGER,
        championID INTEGER,
        PRIMARY KEY (playerID, championID),
        FOREIGN KEY (playerID) REFERENCES Player(playerID),
        FOREIGN KEY (championID) REFERENCES Champions(championID)
    );

    -- Champion_Classes Table (N:(0,2) relationship)
    CREATE TABLE IF NOT EXISTS Champion_Classes (
        championID INTEGER,
        classID INTEGER,
        PRIMARY KEY (championID, classID),
        FOREIGN KEY (championID) REFERENCES Champions(championID),
        FOREIGN KEY (classID) REFERENCES Classes(classID)
    );

    -- Champion_Origins Table (N:(1,2) relationship)
    CREATE TABLE IF NOT EXISTS Champion_Origins (
        championID INTEGER,
        originID INTEGER,
        PRIMARY KEY (championID, originID),
        FOREIGN KEY (championID) REFERENCES Champions(championID),
        FOREIGN KEY (originID) REFERENCES Origins(originID)
    );

    -- Item_Components Table (1:2 relationship)
    CREATE TABLE IF NOT EXISTS Item_Components (
        itemID INTEGER,
        componentID INTEGER,
        PRIMARY KEY (itemID, componentID),
        FOREIGN KEY (itemID) REFERENCES Items(itemID),
        FOREIGN KEY (componentID) REFERENCES Components(componentID)
    );

    -- Champion_Effects Table (N:N relationship)
    CREATE TABLE IF NOT EXISTS Champion_Effects (
        championID INTEGER,
        effectID INTEGER,
        PRIMARY KEY (championID, effectID),
        FOREIGN KEY (championID) REFERENCES Champions(championID),
        FOREIGN KEY (effectID) REFERENCES Effects(effectID)
    );

    -- Champion_Roles Table (N:N relationship)
    CREATE TABLE IF NOT EXISTS Champion_Roles (
        championID INTEGER,
        roleID INTEGER,
        PRIMARY KEY (championID, roleID),
        FOREIGN KEY (championID) REFERENCES Champions(championID),
        FOREIGN KEY (roleID) REFERENCES Roles(roleID)
    );