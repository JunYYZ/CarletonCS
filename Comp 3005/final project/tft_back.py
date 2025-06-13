import sqlite3
import re

# Connect to the SQLite database (or create it if it doesn't exist)
conn = sqlite3.connect('tft_database.db')
cursor = conn.cursor()

# Enable foreign key support
cursor.execute("PRAGMA foreign_keys = ON;")


# Create the tables based on the ER diagram
cursor.executescript("""
-- Team_Composition Table
CREATE TABLE IF NOT EXISTS Team_Composition (
    teamName TEXT PRIMARY KEY,
    winrate REAL
);
                     
-- Team_Composition_Champions Table
CREATE TABLE IF NOT EXISTS Team_Composition_Champions (
    teamName TEXT,
    championID INTEGER,
    PRIMARY KEY (teamName, championID),
    FOREIGN KEY (teamName) REFERENCES Team_Composition(teamName) ON DELETE CASCADE,
    FOREIGN KEY (championID) REFERENCES Champions(championID) ON DELETE CASCADE
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
    attackRange REAL,
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

-- Components Table
CREATE TABLE IF NOT EXISTS Components (
    componentID INTEGER PRIMARY KEY AUTOINCREMENT,
    name TEXT NOT NULL,
    ad REAL,
    ap REAL,
    attack_speed REAL,
    hp REAL,
    armor REAL,
    magic_resist REAL,
    mana REAL,
    crit REAL
);

-- Effects Table
CREATE TABLE IF NOT EXISTS Effects (
    effectID INTEGER PRIMARY KEY AUTOINCREMENT,
    effect_name TEXT NOT NULL UNIQUE
);

-- Roles Table
CREATE TABLE IF NOT EXISTS Roles (
    roleID INTEGER PRIMARY KEY AUTOINCREMENT,
    role_name TEXT NOT NULL UNIQUE
);

-- Player_Augments Table (N:3 relationship)
CREATE TABLE IF NOT EXISTS Player_Augments (
    playerID INTEGER,
    augmentID INTEGER,
    PRIMARY KEY (playerID, augmentID),
    FOREIGN KEY (playerID) REFERENCES Player(playerID),
    FOREIGN KEY (augmentID) REFERENCES Augments(augmentID)
);

-- Player_Component Table (N:N relationship)
CREATE TABLE IF NOT EXISTS Player_components (
    playerID INTEGER,
    componentID INTEGER,
    PRIMARY KEY (playerID, componentID),
    FOREIGN KEY (playerID) REFERENCES Player(playerID),
    FOREIGN KEY (componentID) REFERENCES Components(componentID)
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
""")

print("Database 'tft_database.db' created successfully with the specified schema.")

# cursor.execute("INSERT INTO Team_Composition (teamName, winrate) VALUES (?, ?)", ('Team A', 0.14))

# cursor.execute("INSERT INTO Player (name, health, podium_position, winrate, teamName) VALUES (?, ?, ?, ?, ?)",
#                ('Player1', 100, 1, 0.60, 'Team A'))

# cursor.execute("INSERT INTO Augments (name, effect) VALUES (?, ?)", ('Combat Augment', 'Increase attack speed by 20%'))

# cursor.execute("INSERT INTO Champions (name, ability, ad, ap, attack_speed, hp, armor, magic_resist, mana, manaCost, attackRange, cost) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
#                ('Amumu', 'Misfit Toy', 35, 100, 0.7, 500, 15, 15, 40, 120, 4, 1),
#                ('Powder', 'Misfit Toy', 35, 100, 0.7, 500, 15, 15, 40, 120, 4, 1),
#                ('Powder', 'Misfit Toy', 35, 100, 0.7, 500, 15, 15, 40, 120, 4, 25, 140, 1),
#                ('Powder', 'Misfit Toy', 35, 100, 0.7, 500, 15, 15, 40, 120, 4, 25, 140, 1),
#                ('Powder', 'Misfit Toy', 35, 100, 0.7, 500, 15, 15, 40, 120, 4, 25, 140, 1),
#                ('Powder', 'Misfit Toy', 35, 100, 0.7, 500, 15, 15, 40, 120, 4, 25, 140, 1),
#                ('Powder', 'Misfit Toy', 35, 100, 0.7, 500, 15, 15, 40, 120, 4, 25, 140, 1),
#                ('Powder', 'Misfit Toy', 35, 100, 0.7, 500, 15, 15, 40, 120, 4, 25, 140, 1),
#                ('Powder', 'Misfit Toy', 35, 100, 0.7, 500, 15, 15, 40, 120, 4, 25, 140, 1)
#                )

# cursor.execute("INSERT INTO Classes (name, effect, maxAmount) VALUES (?, ?, ?)",
#                ('Mage', 'Ability power increased', 6))

# cursor.execute("INSERT INTO Origins (name, effect, maxAmount, boolUnique) VALUES (?, ?, ?, ?)",
#                ('Demacia', 'Shields allies', 4, 0))

# cursor.execute("INSERT INTO Items (name, type, ad, ap, attack_speed, hp, armor, magic_resist, mana, effect) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
#                ('Infinity Edge', 'Offensive', 20, 0, 0, 0, 0, 0, 0, 'Critical strikes deal more damage'))

# cursor.execute("INSERT INTO Components (name, type, ad, ap, attack_speed, hp, armor, magic_resist, mana) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)",
#                ('B.F. Sword', 'Offensive', 15, 0, 0, 0, 0, 0, 0))

# cursor.execute("INSERT INTO Effects (effect_name, effect_description, effect_type) VALUES (?, ?, ?)",
#                ('Burn', 'Deals damage over time', 'Debuff'))

# cursor.execute("INSERT INTO Roles (role_name, role_type, role_description) VALUES (?, ?, ?)",
#                ('Assassin', 'Damage Dealer', 'High burst damage to enemies'))

# # Insert into relationship tables
# cursor.execute("INSERT INTO Player_Augments (playerID, augmentID) VALUES (?, ?)", (1, 1))

# cursor.execute("INSERT INTO Player_Champions (playerID, championID) VALUES (?, ?)", (1, 1))

# cursor.execute("INSERT INTO Champion_Classes (championID, classID) VALUES (?, ?)", (1, 1))

# cursor.execute("INSERT INTO Champion_Origins (championID, originID) VALUES (?, ?)", (1, 1))

# cursor.execute("INSERT INTO Item_Components (itemID, componentID) VALUES (?, ?)", (1, 1))

# cursor.execute("INSERT INTO Champion_Effects (championID, effectID) VALUES (?, ?)", (1, 1))

# cursor.execute("INSERT INTO Champion_Roles (championID, roleID) VALUES (?, ?)", (1, 1))

raw_data = """
Champion	Cost	Origin	Class	hp	armor	mr	ad	ap	attack speed	mana	mana cost	attack range	ability	Effect	Roles
Amumu	1	Automata	Watcher	600	35	35	45	100	0.6	0	0	1	Obsolete Technology	Flat Damage Reduction, Manaless	Frontline, Tank, AP
Darius	1	Conqueror	Watcher	600	40	40	55	100	0.7	30	70	1	Decimate	Sustain, Dot	Frontline, AD, Caster
Draven	1	Conqueror	Pit Fighter	500	15	15	55	100	0.7	30	60	4	Spinning Axes	Can Crit	Backline, AD, Carry, DPS
Irelia	1	Rebel	Sentinel	700	20	40	45	100	0.6	30	70	1	Defiant Dance	Shield, Thorns	Frontline, Tank, AP
Lux	1	Academy	Sorcerer	500	20	20	30	100	0.7	0	50	4	Prismatic Barrier	Shield	Backline, AP, Utility, Caster
Maddie	1	Enforcer	Sniper	500	15	15	50	100	0.7	20	120	4	Fan the Hammer	Targets Furthest Enemy	Backline, AD, Caster, DPS
Morgana	1	Black Rose	Visionary	500	20	20	30	100	0.7	0	40	4	Tormented Soul	Reduces Shields, Dot	Backline, AP, Utility, Caster, DPS
Powder	1	Family, Scrap	Ambusher	500	15	15	35	100	0.75	40	120	4	Misfit Toy	Targets Largest Group, Burn, Wound	Backline, AP, Caster, Utility
Singed	1	Chembaron	Sentinel	650	45	45	50	100	0.55	30	90	1	Dangerous Mutations	Buffs, Damage Reduction	Frontline, AP, Tank, Utility
Steb	1	Enforcer	Bruiser	650	40	40	45	100	0.65	30	90	1	Field Medicine	Heal	Frontline, AP, Tank, Utility
Trundle	1	Scrap	Bruiser	650	40	40	50	100	0.65	30	90	1	Desperate Chomp	Sustain	Frontline, AD, DPS, Tank, Carry
Vex	1	Rebel	Visionary	450	15	15	30	100	0.7	0	60	4	Looming Darkness		Backline, AP, DPS, Caster
Violet	1	Family	Pit Fighter	650	40	40	40	100	0.8	20	70	1	1-2-3 Combo	Single Target CC	Frontline, AD, DPS, Caster
Zyra	1	Experiment	Sorcerer	500	20	20	30	100	0.7	10	60	4	Grasping Roots	AOE CC	Backline, AP, Caster, Utility
Akali	2	Rebel	Quickstriker	700	45	45	45	100	0.75	0	60	1	Shuriken Flip	Damage Amp	Frontline, AP, Assassin, Caster
Camille	2	Enforcer	Ambusher	700	45	45	50	100	0.75	0	25	1	Adaptive Strike	Sustain	Frontline, AD, DPS, Carry
Leona	2	Academy	Sentinel	800	50	50	55	100	0.6	50	90	1	Eclipse	damage reduction	Frontline, AP, Tank
Nocturn	2	Automata	Quickstriker	700	45	45	65	100	0.75	0	40	1	Overdive Blades	Dot	Frontline, AD, DPS, Carry
Rell	2	Conqueror	Sentinel, Visionary	800	45	45	60	100	0.6	40	90	1	Shatterin Strike	Shield	Frontline, AP, Tank
Renata Glasc	2	Chembaron	Visionary	600	20	20	35	100	0.7	20	80	4	Loyalty Program	Shield	Backline, AP, Utility
Sett	2	Rebel	Bruiser	850	50	50	60	100	0.6	50	100	1	Facebreaker	AOE CC	Frontline, AP, Tank
Tristana	2	Emissary	Artillerist	550	20	20	42	100	0.7	20	60	4	Draw a Bead	Scaling	Backline, Carry, DPS, AD
Urgot	2	Experiment	Pit Fighter, Artillerist	700	45	45	50	100	0.7	20	70	2	Corrosive Charge	Sunder	Frontline, AD, DPS
Vander	2	Family	Watcher	800	45	50	50	100	0.7	0	50	1	Hound of the Underground	Damage reduction	Frontline, AD, Tank
Vladimir	2	Black Rose	Watcher, Sorcerer	800	45	45	45	100	0.65	0	65	1	transfusion	Sustain	Frontline, AP, Tank
Zeri	2	Firelight	Sniper	600	20	20	45	100	0.75	0	3	4	living battery	Manaless	Backline, AD, DPS, Carry
Ziggs	2	Scrap	Dominator	600	20	20	35	100	0.7	15	60	4	Bom full of Bombs		Backline, AP, DPS, Caster
Blitzcrank	3	Automata	Dominator	850	50	50	60	100	0.6	20	70	1	Static Field	Thorns, Damage reduction, shield	Frontline, AP, Tank
Cassiopeia	3	Black Rose	Dominator	700	25	25	40	100	0.7	10	40	4	Thorned Miasma		Backline, AP, DPS, Caster
Ezreal	3	Academy, Rebel	Artillerist	700	45	45	60	100	0.75	0	60	4	Essence Flux		Backline, AD, DPS, Caster
Gangplank	3	Scrap	Form Swap, Pit Fighter	800	45	45	60	100	0.75	0	60	4	Fire Bomb	Sustain	Frontline, Backline, AD, Caster
Kog'Maw	3	Automata	Sniper	650	25	25	15	100	0.7	0	40	4	Upgrading Barrage Module	Scaling	Backline, AP, DPS, Carry
Loris	3	Enforcer	Sentinel	850	50	50	50	100	0.65	50	100	1	Piltover Bulwark	Shield, Damage reduction	Frontline, AP, Tank
Nami	3	Emissary	Sorcerer	700	25	25	40	100	0.7	0	60	4	Ocean's Ebb		Backline, AP, Caster
Nunu & Willump	3	Experiment	Bruiser, Visionary	800	50	50	50	100	0.6	60	125	1	ZOMBIE POWER!!	damage reduction	Frontline, AP, Tank
Renni	3	Chembaron	Bruiser	850	50	50	55	100	0.65	40	100	1	Sludgerunner's Smash	Sustain, Single target CC	Frontline, AD, Tank, Caster
Scar	3	Firelight	Watcher	800	50	50	50	100	0.65	80	155	1	Sumpsnipe Surprise	Sustain, AoE CC	Frontline, AP, Tank
Smeech	3	Chembaron	Ambusher	800	50	50	68	100	0.8	20	80	1	Scrap Hacker	Reset	Frontline, AD, Caster, Assassin
Swain	3	Conqueror	Form Swap, Sorcerer	950	50	45	45	100	0.6	40	100	4	Ascended Demonic Muder of Crows	Sustain, Reset	Frontline, Backline, AP, Caster, DPS
Twisted Fate	3	Enforcer	Quickstriker	700	25	25	35	100	0.7	25	75	4	Wild Cards	Heal, Single Target CC	Backline, AP, Caster, DPS
Ambessa	4	Emissary, Conqueror	Quickstriker	1100	50	50	65	100	0.8	40	100	1	Unrelenting Huntress	Sustain, Single target CC	Frontline, AD, Caster, DPS
Corki	4	Scrap	Artillerist	850	30	30	63	100	0.75	0	60	4	Broadside Barrage	Sunder	Backline, AD, DPS, Caster
Dr. Mundo	4	Experiment	Dominator	1100	60	60	65	100	0.65	30	100	1	Maximum Dosage	Sustain	Frontline, AP, Tank
Ekko	4	Firelight, Scrap	Ambusher	1100	60	60	50	100	0.85	0	60	1	Splitting Seconds	Shred	Frontline, AP, Caster, Assassin
Elise	4	Black Rose	Form Swap, Bruiser	900	40	40	55	100	0.65	50	100	4	Volatile Spiderling Cocoon	AOE CC, Sustain	Frontline, Backline, Ap, Caster, DPS
Garen	4	Emissary	Watcher	1000	60	60	65	100	0.6	60	125	1	Demacian Justice	Sustain, shield	Frontline, AD, Caster, Tank
Heimerdinger	4	Academy	Visionary	800	30	30	40	100	0.75	0	40	4	Progress!	Random Targeting, scaling	Backline, AP, Caster, DPS
Illaoi	4	Rebel	Sentinel	1100	60	60	70	100	0.65	65	125	1	Test of Spirit	Damage Reduction, sustain	Frontline, AP, Tank
Silco	4	Chembaron	Dominator	800	30	30	40	100	0.75	30	80	4	Channed Monstrosity		Backline, AP, Caster, DPS
Twitch	4	Experiment	Sniper	800	30	30	70	100	0.75	0	60	4	Spray and Pray	Random Targeting	Backline, AD, Carry, DPS
Vi	4	Enforcer	Pit Fighter	1100	50	50	75	100	0.85	40	100	1	Wrecking Crew	Shield, Aoe CC	Frontline, AD, Caster
Zoe	4	Rebel	Sorcerer	800	30	30	40	100	0.75	20	80	4	Paddle Star!		Backline, AP, Caster, DPS
Caitlyn	5	Enforcer	Sniper	900	40	40	80	100	0.55	0	50	4	Air Raid	Random Targeting, sunder, shred	Backline, AD, Carry, Caster, DPS
Jayce	5	Academy	Form Swap	1200	60	60	90	100	0.8	30	80	4	Special Delivery: Hexcrystal Destruction	Shield, Single Target CC, Buffs	Frontline, Backline, AD, Caster, DPS, Utility
Jinx	5	Rebel	Ambusher	900	40	40	60	100	0.8	0	60	4	Ruin Everything	Boardwipe, AOE CC	Backline, AD, Caster, Carry, DPS
LeBlanc	5	Black Rose	Sorcerer	900	40	40	50	100	0.8	45	90	4	The Chains of Fate	AoE CC	Backline, AP, Caster
Malzahar	5	Automata	Visionary	950	40	40	45	100	0.8	30	90	4	Call of the Machine	Boardwipe, Dot, shred, scaling	Backline, AP, Caster, Dps
Mordekaiser	5	Conqueror	Dominator	1200	70	70	75	100	0.55	25	100	1	Grasp of the Iron revenant	Damage Reduction, sustain	Frontline, AP, Caster, Carry, DPS
Rumble	5	Scrap, Junker King	Sentinel	1200	70	70	60	100	0.8	40	120	1	The Equalizer	Burn, Wound	Frontline, AP, Caster, utility
Sevika	5	Chembaron, Highroller	Pit Fighter	1200	60	60	80	100	0.8	0	60	1	Beat the odds	Shield, Gold Gen, Reset, Single Target CC, Damage Reduction	Frontline, AD, Caster, DPS, utility
"""

augment_data = """
A Golden Find:	Champions evolved by the Anomaly drop 2 gold every 2 kills. Gain 10 free rerolls.
A Magic Roll:	Roll 3 dice. Gain rewards based on their total.
Academic Research:	Whenever you build an item, gain a completed item anvil instead. That anvil always offers an Academy item and the item built. Gain 2 random components. Gain a Lux and an Ezreal.
Academy Crest:	Gain an Academy Emblem and a Leona.
Academy Crown:	Gain an Academy Emblem and a random sponsored item.
Adrenaline Burst:	Combat start and every 6 seconds, all Quickstrikers attack 85% faster for 2.50 seconds. Gain a Nocturne and Akali.
All That Shimmers:	Choose a gold-generating Artifact items and gain a Magnetic Remover. Your max interest is increased to 7. Interest is extra gold you gain per 10g saved.
All That Shimmers+:	Choose a gold-generating Artifact. Gain a Magnetic Remover and 4 gold. Your max interest is increased to 7. Interest is extra gold you gain per 10g saved.
Ambusher Crest:	Gain an Ambusher Emblem and a Camille.
Ambusher Crown:	Gain an Ambusher Emblem, a Hand of Justice, and a Camille.
An Exalted Adventure:	Gain three 2-cost champions. If you 3-star two of them, gain an orb filled with loot. Gain 6 rerolls.
Anger Issues:	All your current and future completed items transform into Guinsoo's Rageblades that grant 35 Armor and Magic Resist. Every 2 Rageblade stacks grant 1% Attack Damage and Ability Power.
Another Anomaly:	After the Anomaly round on 4-6, gain a item that duplicates the chosen Anomaly Effect onto the equipped champion.
Arcane Retribution:	When Sorcerers die, they deal 300% of their Ability Power as magic damage to all adjacent units. Gain a Vladimir.
Artifactory:	At the start of each turn, your benched completed items transform into a random Artifact item. Gain 1 Artifact Anvil and 2 Removers.
Artillerist Crest:	Gain an Artillerist Emblem and a Tristana.
Artillerist Crown:	Gain an Artillerist Emblem, a Runaan's Hurricane, and a Tristana.
At What Cost:	Immediately go to level 6 and gain 8 XP. You don't get to choose your future augments.
Automata Crest:	Gain an Automata Emblem and a Nocturne.
Automata Crown:	Gain an Automata Emblem, a Guardbreaker, and a Nocturne.
Backup:	Your team gains 10% Attack Speed if at least 4 allies start combat in the back two rows.
Bad Luck Protection:	Your team can no longer critically strike. Convert each 1% Critical Strike Chance into 1% Attack Damage. Gain a Sparring Gloves.
Balanced Budget:	At the start of the next 4 rounds, gain 7 gold.
Balanced Budget+:	At the start of the next 4 rounds, gain 10 gold.
Band of Thieves I:	Gain 1 Thief's Gloves.
Battle Scars:	Every time Watchers are attacked, they gain 2% Attack Damage. Every time Watchers are hit by an ability, they gain 4 Ability Power. Gain a Vander.
Beggars Can Be Choosers:	You get +3 Augment rerolls for all other augment choices. Gain 7 gold.
Belt Overflow:	Gain 4 Giant's Belts. Your Giant's Belts grant +75 bonus Health.
Big Grab Bag:	Gain 3 random components, 2 gold, and 1 Reforger. Reforgers allow you to remake any item.
Birthday Present:	Gain a 2-star champion every time you level up. The champion's cost tier is your level minus 4 (min: 1-cost).
Black Rose Crest:	Gain a Black Rose Emblem.
Black Rose Crown:	Gain a Black Rose Emblem, a Morellonomicon, and a Cassiopeia.
Blade Dance:	Gain an Irelia. Your strongest Irelia gains 40% Attack Speed and gains a brand new Ability that dashes her between two targets, dealing physical damage to both.
Blazing Soul I:	Combat start: Your highest Attack Speed champion gains 20 Ability Power and 20% Attack Speed. Repeat on another ally every 3 seconds.
Blazing Soul II:	Combat start: Your highest Attack Speed champion gains 35 Ability Power and 30% Attack Speed. Repeat on another ally every 3 seconds.
Blinding Speed:	Gain a Red Buff, Guinsoo's Rageblade, a Recurve Bow and a Magnetic Remover. Useful for Attack Carries!
Blistering Strikes:	Your team's attacks Burn their targets for 5% of their max Health over 5 seconds. Attacks also reduce their targets' healing received by 33%.
Branching Out:	Gain a random Emblem and a Reforger. Reforgers allow you to remake any item.
BRB:	You cannot perform actions for the next 4 rounds. Afterwards, gain 2 completed item anvils.
Bronze For Life I:	Your team gains 2% Damage Amp for each Bronze-tier trait.
Bronze For Life II:	Your team gains 2.50% Damage Amp and 1.50% for each Bronze-tier trait.
Bruiser Crest:	Gain a Bruiser Emblem and a Sett.
Bruiser Crown:	Gain a Bruiser Emblem, a Redemption, and a Sett.
Brutal Revenge:	Gain 2 Rennis. Your strongest Renni's Ability costs 35 less and causes her to lunge towards the fartheset enemy within 2 hexes, dealing 125% damage to the target and reduced damage to enemies in her path.
Build a Bud!:	Gain a random 3-star 1-cost champion and 8 gold.
Built Different:	Your units with no Traits active gain 240-530 Health and 45-60% Attack Speed (based on current Stage).
Bulky Buddies I:	Allies that start combat next to exactly 1 other ally gain 100 Health. When that champion dies, the other gains a 10% max Health Shield for 10 seconds.
Bulky Buddies II:	Allies that start combat next to exactly 1 other ally gain 150 Health. When that champion dies, the other gains a 12% max Health Shield for 10 seconds.
Bulky Buddies III:	Allies that start combat next to exactly 1 other ally gain 300 Health. When that champion dies, the other gains a 15% max Health Shield for 10 seconds.
Buried Treasures III:	Gain a random item component at the start of the next 6 rounds (including this round).
Calculated Enhancement:	Each combat, 4 random champions in your last row gain 35% Attack Damage and 40 Ability Power.
Call to Chaos:	Gain a powerful and random reward.
Called Shot:	Set your win streak to +4. Gain 4 gold.
Caretaker's Ally:	Gain a random 2-cost champion now. Gain the same one again every time you level up.
Caretaker's Chosen:	As you level, gain more powerful items. Level 4: component anvil Level 6: completed item anvil Level 8: choose 1 of 5 Radiant items
Caretaker's Favor:	Gain a component anvil when you reach level 5, 6, 7, and 8. The anvil offers 4 choices.
Category Five:	Gain a Runaan's Hurricane. Your Runaan's Hurricanes shoot 1 extra bolts, each dealing 85% of the original damage.
Chem-Baron Crest:	Gain a Chem-Baron Emblem and a Renata Glasc.
Chem-Baron Crown:	Gain a Chem-Baron Emblem, a Nashor's Tooth, and a Smeech.
Clear Mind:	If there are no champions on your bench at the end of player combat, gain 3 XP.
Climb The Ladder I:	Each time an ally dies, allies that share at least one trait with them gain 3 Ability Power, 3% Attack Damage, 3 Armor, and 3 Magic Resist.
Climb The Ladder II:	Each time an ally dies, allies that share at least one trait with them gain 5 Ability Power, 5% Attack Damage, 5 Armor, and 5 Magic Resist.
Clockwork Accelerator:	Your team gains 10% Attack Speed every 3 seconds in combat.
Cloning Facility:	Empower a hex in the center of the board. Summon a clone of the champion in it with 70% Health and 20% increased Mana cost.
Cluttered Mind:	Gain 4 random 1-cost champions now. If your bench is full at the end of player combat, gain 3 XP.
Combat Medic:	Gain a Steb. Your strongest Steb's Ability's Mana cost is reduced by 10, but no longer heals. Steb's spell grants 30% Omnivamp and strikes 3 times, each dealing 70%.
Component Buffet:	Whenever you would get a component, gain a component anvil instead. Gain a random component. The anvil offers 4 choices.
Conqueror Crest:	Gain a Conqueror Emblem and a Rell.
Conqueror Crown:	Gain a Conqueror Emblem, a Last Whisper, and a Rell.
Contested:	After each player combat, gain 1 gold for every 2 other players who have fielded the leftmost unit on your bench this game.
Cooking Pot:	At the start of each turn, all units holding a Frying Pan or Spatula item grant the nearest champion 20 permanent Health. Gain a Frying Pan.
Coronation:	Gain a Tactician's Crown. Tactician's Crown, Shield, and Cape grant the holder an additional 30% Attack Speed, 40% Attack Damage, and 40 Ability Power.
Corrosion:	Enemy champions in the first two rows lose 3 Armor and Magic Resist every 2 seconds.
Crafted Crafting:	Whenever you craft a completed item, gain 3 rerolls.
Crimson Pact:	Gain a Vladimir. Your strongest Vladimir gains +3 Range and gains 5 bonus Mana each attack. His Ability no longer heals but grants 6% Damage Amp, deals 80% bonus damage, and spreads additional damage to the 2 nearest enemies.
Crown Guarded:	Gain a Crownguard. Your Crownguards' start of combat effect is 100% stronger.
Crown's Will:	Gain a Needlessly Large Rod. Your units gain 10 Ability Power and 10 Armor.
Dark Alley Dealings:	Gain a Suspicious Trenchcoat. After 3 player combats, gain a Trickster's Glass.
Delayed Start:	Sell your board and bench. Gain 4 random 2-star 1 cost champions. Disable your Shop for the next 3 rounds.
Diversified Portfolio:	Each round, gain 1 gold for every 3 non-unique traits active. Gain 1 gold.
Diversified Portfolio+:	Each round, gain 1 gold for every 3 non-unique traits active. Gain 4 gold.
Domination:	Dominators gain 20% Attack Speed while shielded. When a Dominator gets a kill, all Dominators gain 150 Shield for 4 seconds. Gain a Cassiopeia.
Dominator Crest:	Gain a Dominator Emblem and a Blitzcrank.
Dominator Crown:	Gain a Dominator Emblem, a Protector's Vow, and a Blitzcrank.
Dual Purpose:	The first time you buy XP each round, gain 2 gold. Whenever you buy XP, reroll your Shop.
Dummify:	Lose all champions on your board and bench. Gain a Training Dummy with 70% of their combined health.
Duo Queue:	Gain 2 random 5-cost champions and 2 copies of a random component.
Enforcer Crest:	Gain an Enforcer Emblem and a Loris.
Enforcer Crown:	Gain an Enforcer Emblem, an Infinity Edge, and a Loris.
Epoch:	Now, and at the start of every stage, gain 6 XP and 2 free rerolls.
Epoch+:	Now, and at the start of every stage, gain 8 XP and 3 free rerolls.
Expected Unexpectedness:	Now and at the start of the next 2 stages, roll 3 dice. Gain various rewards based on their total.
Eye For An Eye:	For every 14 ally champions that die, gain a random component (max 4).
Eye For An Eye+:	Gain a random component. For every 11 ally champions that die, gain another component (max 3).
Family Crest:	Gain a Family Emblem and a Violet.
Family Crown:	Gain a Family Emblem, a Redemption, a Vander, and a Violet.
Final Polish:	Gain a Support Anvil and a completed item anvil.
Find Your Center:	Your champion that starts combat in the center of the board gains 15% Damage Amp and 15% max Health.
Fine Vintage:	Completed items left on your bench for 4 rounds transform into Support Anvils.
Firelight Crest:	Gain a Firelight Emblem and a Zeri.
Firelight Crown:	Gain a Firelight Emblem, a Protector's Vow, and a Scar.
Firesale:	Each round, steal a random champion from the shop. Gain 3 gold.
Flexible:	Gain 1 random emblem. At the start of every Stage, gain a random emblem. Your team gains 30 Health for each emblem they are holding.
Flurry of Blows:	Gain a Zeke's Herald. Champions buffed by Zeke's also gain 35% Critical Strike Chance.
Forbidden Magic:	Every 3 takedowns by Black Rose champions or Sion grant Sion permanent 1.50% Attack Damage and 10 Max Health. Gain 3 Black Rose champions.
Forward Thinking:	Lose all your gold. After 6 player combats, gain back the original amount and another 80 gold.
Fractured Crystals:	When an Automata champion fires their blast, they fire a second blast at the closest enemy dealing 50% of the original damage. Gain an Amumu and Nocturne.
Ghost of Friends Past:	Whenever an allied champion dies, your team permanently gains 15 health, 2% Attack Damage, or 2 Ability Power, based on the dying champion's role.
Glass Cannon I:	Units that start combat in the back row begin combat at 80% health but gain 12% Damage Amp.
Glass Cannon II:	Units that start combat in the back row begin combat at 80% health but gain 20% Damage Amp.
Gloves Off:	Gain a Vander. Your strongest Vander gains a brand new Ability that no longer grants resistances, but deals 100% increased damage and punches the target backwards, dealing 25% of the original damage to all enemies hit.
Going Long:	You no longer gain interest. Gain 15 gold now. Round start: gain 4 XP. Interest is extra gold you gain per 10g saved.
Gold For Dummies:	Gain a Training Dummy. Every 10 seconds, all Training Dummies grant 1 gold.
Golemify:	Lose all champions on your board and bench. Gain a Golem with 70% of their combined Health and 50% of their combined Attack Damage.
Good For Something I:	Champions that aren't holding items have a 50% to drop 1 gold on death.
Greater Moonlight:	Combat start: 1 random 1-cost champion is upgraded to 4-star for that round and gains 15% Attack Damage and 15 Ability Power.
Guerilla Warfare:	Firelight champions gain 3% Attack Damage and 3 Ability Power for every hex traveled. Gain a Scar.
Hall of Mirrors:	Combat start: All champions in your front row become clones of the champion in the center of the row. Clones have 90% of their original health, and deal 10% less damage.
Hard Commit:	Gain a random emblem. Now and at the start of each stage, gain a 1-star champion of that trait with a cost equal to the Stage (max 5).
Health is Wealth I:	Your team gains 10% Omnivamp. Get a bonus of 10 gold when your team first accumulates 10000 total healing.
Health is Wealth II:	Your team gains 15% Omnivamp. Get a bonus of 20 gold when your team first accumulates 10000 total healing.
Heavily Smash:	Every 3 seconds, Bruisers deal 6% bonus max Health bonus physical damage on their next attack. Gain a Steb and a Trundle.
Hedge Fund:	Gain 22 gold. Your max interest is increased to 10. Interest is extra gold you gain per 10g saved.
Help Is On The Way:	After 8 player combats, choose 1 of 4 Support items.
Heroic Grab Bag:	Gain 2 Lesser Champion Duplicators and 9 gold. This item allows you to copy a 3-cost or less champion.
High Voltage:	Gain an Ionic Spark. Your Ionic Sparks have +3 hex radius and do 25% more damage.
I Hope This Works:	Gain a Powder. Your strongest Powder's explosion radius is increased by two hexes, has less damage falloff, but deals 60% damage to ALLIES.
I'm the Carry Now:	Get a Golem with tailored offensive items. It gets stronger at the start of each Stage.
Immovable Object:	Gain a Randuin's Omen. Its range is increased by 1 hex and its effect is increased by 60%.
Inspiring Epitaph:	When a unit dies, the nearest ally gains a 20% max Health Shield and 10% stacking Attack Speed.
Invested+:	Gain 26 gold. At the start of each round, gain 1 Shop reroll for every 10 gold above 50 (max 80 gold).
Invested++:	Gain 45 gold. At the start of each round, gain 1 Shop reroll for every 10 gold above 50 (max 80 gold).
Investment Strategy I:	Your champions gain 7 permanent max health per interest you earn.
Investment Strategy II:	Gain 5 gold. Your champions gain 10 permanent max health per interest you earn. Your max interest is increased to 7.
Iron Assets:	Gain a component anvil and 4 gold. The anvil offers 4 choices.
Item Collector I:	Your team gains 10 Health. For each unique item they are holding, your team gains bonus 2 Health, 1 Attack Damage, and 1 Ability Power.
Item Collector II:	Your team gains 20 Health. For each unique item they are holding, your team gains bonus 5 Health, 1.50 Attack Damage, and 1.50 Ability Power.
Item Grab Bag I:	Gain 1 random completed item.
Kingslayer:	After winning player combat, gain 1 gold. If they had more health than you, gain 3 Gold instead. Gain 1 gold now.
Lategame Specialist:	When you reach Level 9, gain 33 gold.
Latent Forge:	After 8 player combats, gain an Artifact anvil. The anvil offers 4 choices. Artifacts are more powerful items with a unique effect.
Law Enforcement:	Enforcer champions gain 10% Attack Damage. Every 5 Wanted enemy deaths grant 6 gold. Gain a Steb and Maddie.
Level Up!:	When you buy XP, gain an additional 2. Gain 12 immediately.
Lineup:	Your team gains 2.50 Armor and Magic Resist for each unit that starts combat in the front two rows.
Little Buddies:	Your 4-cost and 5-cost champions gain 65 Health and 7% Attack Speed for every 1-cost and 2-cost champion on your board.
Living Forge:	Gain an Artifact anvil now and after every 9 player combats. Artifacts are more powerful items with a unique effect.
Lone Hero:	Your last surviving unit gains 100% Attack Speed and 30% Durability
Long Distance Pals:	Combat start: Your 2 units furthest from each other form a bond, sharing 22% of their Armor, Magic Resist, Attack Damage, and Ability Power with each other.
Loot Explosion:	Ambusher kills have a chance to drop loot, scaling with Critical Strike Chance. The value of the loot given can also critically strike, granting even more loot. Gain a Camille and a Powder.
Lucky Gloves:	Thief's Gloves will always give your champions ideal items. Gain 2 Sparring Gloves. In 5 rounds, get another Sparring Gloves.
Lucky Gloves+:	Thief's Gloves will always give your champions ideal items. Gain 3 Sparring Gloves.
Lunch Money:	Every 8 damage you deal to enemy tacticians gives you 2 gold.
Mace's Will:	Gain a Sparring Gloves. Your team gains 8% Attack Speed and 20% Critical Strike Chance.
Mad Chemist:	Gain a Singed. Your strongest Singed cannot attack but constantly runs, leaving a poison trail dealing magic damage over time. His Ability will always target himself and instead grants 20% Omnivamp and Move Speed.
Malicious Monetization:	Gain 6 gold. For the next 3 rounds, enemy champions drop 2 gold when killed.
Manaflow I:	Your units that start combat in the back row gain 3 additional Mana per attack.
Manaflow II:	Your units that start combat in the back row gain 5 additional Mana per attack.
Max Cap:	Your max level is 7. Gain 1 Tactician's Shield which increases your team size by +1, and 60 gold.
Mentorship I:	If an ally starts combat next to a higher-cost ally, it gains 12% Attack Speed and 150 Health.
Mentorship II:	If an ally starts combat next to a higher-cost ally, it gains 18% Attack Speed and 220 Health.
Missed Connections:	Gain a copy of each 1-cost champion.
Moonlight:	Combat start: 2 random 1-cost champions are upgraded to 3-star for that round and gain 10% Attack Damage and 10 Ability Power.
NO SCOUT NO PIVOT:	Units can no longer be benched or sold after fighting in a player combat. After each player combat, units that fought gain 15 Health, 1.50% Attack Damage, and 1.50% Ability Power.
Noble Sacrifice:	When your first ally dies each combat, grant your team 15 + 15% of that ally's Armor and Magic Resistance.
Not Today:	Gain an Edge of Night. Champions holding this item gain 35% Attack Speed.
Noxian Guillotine:	Conquerors execute enemies below 12% Health. When they do, they gain 5 Armor and Magic Resist for the rest of combat. Gain a Darius and Draven.
One Buff, Two Buff:	Gain a Red Buff, a Blue Buff, and a Champion Duplicator.
One For All I:	Your team gains 2% max Health and 1.50% Damage Amp for each unique one-cost champion on your board. Gain 2 one-costs.
One For All II:	Your team gains 3% max Health and 2.50% Damage Amp for each unique one-cost champion on your board. Gain 3 one-costs.
One, Two, Five!:	Gain 1 random component, 2 gold, and 1 random 5-cost champion.
Ones Twos Three:	Gain 2 1-cost champion, 2 2-cost champions, and 1 3-cost champion.
Over Encumbered:	For the next stage, you only get 1 bench slot. After, get 3 item components.
Overheal:	Every third attack deals an additional 115% damage and heals 50% of the damage. Excess healing is converted to a shield up to 300 Health.
Paint the Town Blue:	When the first 5 Rebels die each combat, summon a copy of themselves that is one-star lower with 400 less Health. Gain an Akali and an Irelia.
Pair of Fours:	If your team has exactly 2 four-cost champions, they each gain 404 Health and 24.40% Attack Speed. Gain a random 4-cost.
Pandora's Bench:	Gain 2 gold. At the start of every round, champions on the 3 rightmost bench slots transform into random champions of the same cost.
Pandora's Items:	Round start: items on your bench are randomized. Gain 1 random component.
Pandora's Items II:	Round start: items on your bench are randomized. Gain 2 random components.
Pandora's Items III:	Round start: items on your bench are randomized. Gain 1 random Radiant item.
Patience is a Virtue:	Each round, gain 2 free rerolls if you did not buy a champion last round.
Patient Study:	After player combat, gain 2 XP if you won or 3 XP if you lost.
Phreaky Friday:	Gain an Infinity Force. After 5 player combats, gain another. Infinity Force: Artifact that offers tons of offensive and defensive stats
Phreaky Friday +:	Gain an Infinity Force. After 3 player combats, gain another. Infinity Force: Artifact that offers tons of offensive and defensive stats
Piercing Lotus I:	Your team gains 10% Critical Strike chance, and their Abilities can critically strike. Critical strikes 20% Shred and Sunder the target for 3 seconds.
Piercing Lotus II:	Your team gains 30% Critical Strike chance, and their Abilities can critically strike. Critical strikes 20% Shred and Sunder the target for 3 seconds.
Pilfer:	Each round, gain a 1-star copy of the first champion you killed last combat.
Pit Fighter Crest:	Gain a Pit Fighter Emblem and an Urgot.
Pit Fighter Crown:	Gain a Pit Fighter Emblem, a Sterak's Gage, and a Gangplank.
Placebo:	Gain 8 gold. Your team gains 1% Attack Speed.
Placebo+:	Gain 15 gold. Your team gains 1% Attack Speed.
Portable Forge:	Choose 1 of 4 Artifacts. Artifacts are more powerful items with a unique effect.
Power Up:	Your next augment is one tier higher.
Powered Shields:	While Shielded, your units gain 12% Durability. The first time allies fall below 50% Health, they gain 125-275 Shield (based on Stage) for 3 seconds.
Prismatic Pipeline:	The next non-player combat round will drop an additional Prismatic Orb full of amazing loot. All Gold and Prismatic orbs contain even more loot!
Prismatic Ticket:	Each time your Shop is rerolled, you have a 45% chance to gain a free reroll.
Prizefighter:	Gain 2 item components. Every 5 wins gives you an item component.
Pumping Up I:	Your team gains 8% Attack Speed now. Each round after, they gain 0.50% more.
Pumping Up II:	Your team gains 10% Attack Speed now. Each round after, they gain 1% more.
Pumping Up III:	Your team gains 12% Attack Speed now. Each round after, they gain 2% more.
Pyromaniac:	Gain a Red Buff. Your Burns deal 50% increased damage.
Quality Over Quantity:	Units holding exactly 1 item upgrade that item to Radiant. Gain 2 Magnetic Removers. Thief's Gloves counts as multiple items.
Quickstriker Crest:	Gain a Quickstriker Emblem and an Akali.
Quickstriker Crown:	Gain a Quickstriker Emblem, a Guinsoo's Rageblade, and a Nocturne.
Radiant Refactor:	Gain a Masterwork Upgrade and 1 component anvil. Masterwork Upgrade upgrades an item to Radiant!
Radiant Relics:	Choose 1 of 5 Radiant items. Gain a Magnetic Remover. Radiant items are very powerful versions of completed items.
Raining Gold:	Gain 8 gold now and 1 gold every round.
Raining Gold+:	Gain 18 gold now and 1 gold every round.
Rebel Crest:	Gain a Rebel Emblem and an Akali.
Rebel Crown:	Gain a Rebel Emblem, a Jeweled Gauntlet, and an Akali.
Recombobulator:	Champions on your board permanently transform into random champions 1 cost tier higher. Gain 2 Magnetic Removers.
ReinFOURcement:	The next 4-cost champion you buy is instantly upgraded to 2-star. Get 12 gold.
Replication:	Choose 1 of 3 components. For the next 2 rounds, gain a copy of that component.
Reroll Transfer:	For every 1 unused Augment Reroll, gain 3 free shop rerolls. Gain 3 gold. Does not include the round this augment is selected.
Restart Mission:	Remove all champions on your board and bench. Gain 2 random 2-star 3-costs, 3 2-star 2-costs, and 1 2-star 1-cost champion.
Rigged Shop:	Your next shop and every 4 shops will contain all 3-cost champions.
Rigged Shop+:	Your next shop and every 4 shops will contain all 3-cost champions. Gain 5 rerolls.
Risky Moves:	Your Tactician loses 20 Health, but after 7 player combats, gain 30 gold.
Rocket Collection:	Artillerist rockets deal 50% increased damage. Every 60 rockets your Artillerists fire, gain a Collector (max 2). Gain a Tristana and Urgot.
Roll The Dice:	Gain a Rascal's Gloves item. This equips 2 random Radiant items every round. Radiant items are very powerful versions of completed items.
Rolling For Days I:	Gain 11 free Shop rerolls.
Salvage Bin:	Gain 1 random completed item now, and 1 component after 7 player combats. Selling champions breaks completed items into components (excluding Tactician's items and Emblems).
Salvage Bin:	Gain 1 random completed item now, and 1 component after 7 player combats. Selling champions breaks completed items into components (excluding Tactician's items and Emblems).
Salvage Bin+:	Gain 1 random completed item now, and 1 component after 4 player combats. Selling champions breaks completed items into components (excluding Tactician's items and Emblems).
Sated Spellweaver:	After casting an Ability, champions gain 20% Omnivamp for 3 seconds. Excess healing is converted to a shield up to 300 Health.
Scapegoat:	Gain a Training Dummy and 4 gold. If it is the first to die each player combat, gain 1 gold.
Scavenger:	The first 4 enemy champions that are killed each combat grant a champion on your team a temporary completed item.
Scoreboard Scrapper:	Every round, if you're in the bottom 4, your team permanently gains 1.50% Attack Damage and Ability Power. If you're in the top 4, they have 10% more Health.
Scrap Crest:	Gain a Scrap Emblem and a Ziggs.
Scrap Crown:	Gain a Scrap Emblem, a Ziggs, and 2 random components.
Sentinel Crest:	Gain a Sentinel Emblem and a Rell.
Sentinel Crown:	Gain a Sentinel Emblem, a Crownguard, and a Loris.
Shield Bash:	Sentinels gain 8% bonus Armor and Magic Resist. Every 4 seconds, their next attack deals 100% of their total resistances as magic damage. Gain a Loris.
Shimmerscale Essence:	Gain a Mogul's Mail. In 5 rounds, gain a Gamblers Blade. These items give gold as well as combat power.
Shop Glitch:	During non-player combat rounds, your shop refreshes for free every 3 seconds for 30 seconds.
Shopping Spree:	When you level up, gain a number of free shop refreshes equal to your level. Gain 4 gold.
Silver Spoon:	Gain 10 XP.
Slammin':	Gain 1 random Component(s). After each player combat, if there are no items on your bench (other than Consumables), gain 2 XP.
Slammin'+:	Gain 1 random component(s) and 10 XP now. After each player combat, if there are no items on your bench (other than Consumables), gain 2 XP.
Sniper Crest:	Gain a Sniper Emblem and a Zeri.
Sniper Crown:	Gain a Sniper Emblem, an Infinity Edge, and a Zeri.
Sniper's Nest:	Snipers gain +8% Damage Amp for each round fought from the same starting hex (Maximum +32%). Gain a Zeri.
Sorcerer Crest:	Gain a Sorcerer Emblem and a Vladimir.
Sorcerer Crown:	Gain a Sorcerer Emblem, an Adaptive Helm, and a Vladimir.
Spear's Will:	Your team gains 10% Attack Damage and 15 Mana. Gain a B.F. Sword.
Spirit Link:	Your team restores 4% of their max Health every 5 seconds. Increase the healing by 0.50% for every 10 missing player Health.
Spoils of War I:	Enemies have a 25% chance to drop loot when killed.
Spoils of War II:	Enemies have a 30% chance to drop loot when killed.
Spoils of War III:	Enemies have a 40% chance to drop loot when killed.
Sponging:	Combat start: Up to 5 champions with 1 or fewer items gain a copy of a random completed item from the nearest itemized ally.
Starry Night:	1-cost and 2-cost units in your shop have a chance to be 2-star. Gain 3 gold. Chances increase with player level.
Starry Night+:	1-cost and 2-cost units in your shop have a chance to be 2-star. Gain 8 gold. Chances increase with player level.
Subscription Service:	Now, and at the start of each Stage, open a Shop of 4 unique 4-cost champions and gain 6 gold.
Superstars I:	Your team deals 5% more damage, increased by 2% for every 3-star champion on your team. Gain 2 rerolls.
Superstars II:	Your team deals 7% more damage, increased by 5% for every 3-star champion on your team. Gain 4 rerolls.
Support Cache:	Choose 1 of 4 Support items.
Support Mining:	Gain a Training Dummy. When it dies 6 times, gain a Support Anvil and remove the Training Dummy.
Support Mining+:	Gain a Training Dummy. When it dies 4 times, gain a Support Anvil and remove the Training Dummy.
Survivor:	After 3 players are eliminated, gain 60 gold.
Sword Overflow:	Gain 5 BF Swords. Your BF Swords grant +2.50% Attack Speed.
Table Scraps:	After each carousel, gain one unit that was not taken and its item. Gain 1 gold.
Tactician's Kitchen:	Gain a Tactician's Cape and a random Emblem.
Team Building:	Gain a Lesser Champion Duplicator. Gain another after 7 player combats. This item allows you to copy a 3-cost or less champion.
Teaming Up I:	Gain 1 random component and 2 random Tier 3 champions.
Teaming Up II:	Gain 1 random Support item and 2 random 4-cost champions.
The Golden Egg:	Gain a golden egg that hatches in 11 turns for a huge amount of loot. Winning player combat speeds up the hatch timer by an extra turn.
The Mutation Survives:	Experiments gain 12% Health and grant a special hex. The Experiment in the hex is killed on combat start, granting their Experiment bonus to other laboratory hexes. Gain 3 Experiment champions.
Thorn-Plated Armor:	Gain a Bramble Vest. Your Bramble Vests deal 5-100% more damage (based on Stage) and heal the holder for 50% of the damage dealt.
Tiniest Titan:	Gain 2 player health and 1 gold after every player combat. Your Tactician also moves faster.
Tiniest Titan+:	Gain 2 player health and 1 gold after every player combat. Your Tactician also moves faster. Gain 15 gold now.
Titanic Titan:	Increase your current and max player health by 20. On carousel rounds you are released earlier, but are much slower.
Tomb Raider I:	For the next 3 players eliminated, choose one of their items to keep.
Tomb Raider II:	Every time a player is eliminated, choose one of their items to keep.
Top of the Scrap Heap:	Every 8 components Scrap champions convert, gain a random component (max 6). Gain a Powder and a Trundle.
Tower Defense:	Gain a Training Dummy equipped with a random emblem that fires ranged attacks at enemies. It upgrades as the game goes on.
Trade Sector:	Gain a free Shop reroll every round. Gain 1 gold.
Training Arc:	Pit Fighters permanently gain 1.50 Attack Damage if they lost the last combat. If they won, gain 45 Health instead. Gain an Urgot.
Trait Tracker:	The first time you activate 7 non-unique traits for 1 combat, gain 5 random emblems.
Trait: Geniuses:	When Heimerdinger casts, Ekko releases 3 afterimages, each dealing 30% damage. When Ekko casts, Heimerdinger fires 3 missiles, each dealing 100% damage. Gain a Heimerdinger and Ekko.
Trait: Martial Law:	When Ambessa casts, Caitlyn fires an empowered attack at the target, dealing 250% damage. Ambessa gains 35% of Caitlyn's Attack Damage. Gain a Caitlyn and Ambessa.
Trait: Menaces:	While fielded with Silco, Powder gains Dominator, but no longer benefits from Family. When her monkey explodes, it creates 3 of Silco's monstrosities. Gain a 2-star Powder and a Silco.
Trait: NamePending:	Vander gains 35 permanent max Health each time Silco casts. Silco gains 8 permanent Ability Power each time Vander dies. Gain a 2-star Vander, a Silco, and a Spear of Shojin.
Trait: Reunion:	When Vi casts, Ekko releases 3 afterimages towards her target dealing 50% damage. When Ekko casts, Vi slams an earthquake towards his target dealing 150% damage. Gain a Vi and Ekko.
Trait: Sisters:	When Vi scores a takedown, Jinx gains 50% bonus Attack Speed for 5 seconds. When Jinx scores a takedown, Vi gains 40% bonus Attack Damage for 5 seconds.
Trait: Unlikely Duo:	Jinx and Sevika gain 15% Attack Damage and 150 Health. Whenever one casts, they grant the other 20 mana. Sevika's arm is luckier. Gain a Jinx and Sevika.
Trifecta I:	Gain 2 3-cost champions. Combat Start: 3 random 3-cost champions gain 250 Health and 18% Attack Speed.
Trifecta II:	Gain 3 3-cost champions. Allies gain 9% Attack Speed. Combat Start: 3 random 3-cost champions gain 450 Health and 30% Attack Speed.
Trolling:	Gain a Trundle. Your strongest Trundle's Ability no longer heals, but grants him 125% Attack Speed for 5 seconds, and permanently grants him 1.50% Attack Damage. His maximum mana is reduced by 40.
Two Much Value:	Gain 1 reroll for every 3 unique two-cost champions fielded last combat. Gain 3 two-cost unit.
Two Trick:	Gain a random 2-star two-cost and 2 random 2-star one-cost champions.
Underdogs:	Whenever your team has fewer units alive than your opponent, your units regenerate 8% Health each second (maximum: 150).
Unleash The Beast:	Gain a Sterak's Gage. When its effect triggers, the holder gains 35% Attack Speed for the rest of combat and immunity to crowd control for 10 seconds.
Upward Mobility:	Buying XP costs 1 less. Gain 2 Health and 2 free rerolls whenever you level up.
Vampiric Vitality:	You heal for 25% of the damage you deal to enemy Tacticians. Your units gain 12% Omnivamp.
Visionary Crest:	Gain a Visionary Emblem and a Renata Glasc.
Visionary Crown:	Gain a Visionary Emblem, a Spear of Shojin, and a Renata Glasc.
Void Swarm:	Gain a Zz'Rot Portal and another after every 9 player combats. Zz'Rot Portal Voidlings gain 50% Attack Speed and 40% Omnivamp.
Void Swarm:	Gain a Zz'Rot Portal and another after every 9 player combats. Zz'Rot Portal Voidlings gain 50% Attack Speed and 40% Omnivamp.
Voidcaller:	For every 220 Mana Visionaries spend during combat, summon a Voidling, up to 5 Voidlings. Gain a Rell and Morgana. The Voidling has 550-700 Health based on Stage.
Wand Overflow:	Gain 5 Needlessly Large Rods. Your Needlessly Large Rods grant +2.50% Attack Speed.
Wandering Trainer I:	Gain 1 gold and a Training Dummy with 2 permanently attached Emblems.
Wandering Trainer II:	Gain 6 gold and a Training Dummy with 3 permanently attached Emblems.
War for the Undercity:	Everytime you choose to save your Shimmer on a Black Market, heal 4 player health and gain 6 gold. Gain a Renni.
Warpath:	After dealing 60 player damage, gain a chest of high cost champions and items.
Watcher Crest:	Gain a Watcher Emblem and a Vander.
Watcher Crown:	Gain a Watcher Emblem, a Steadfast Heart, and a Scar.
Welcome to the Playground:	If at least 2 Family members are alive after 12 seconds in combat or at the end of combat, gain a random copy of a Vander, Powder, or Violet. Gain a Powder and Violet.
What Doesn't Kill You:	Gain 2 gold after losing a player combat. Gain a random component after every 4 losses.
Why Not Both?:	While you field 2 of the same Form Swapper in different forms, both gain 30 Ability Power, Armor, and Magic Resist, and 30% Attack Damage. When you 3-star a Form Swapper, gain a 2-star copy. Gain a Swain and Gangplank.
Worth the Wait:	Gain a random two-star 1-cost champion. After 1 rounds, gain another copy of them at the start of each round for the rest of the game.
Worth the Wait II:	Gain a random 2-cost champion. Gain another copy of them at the start of each round for the rest of the game.
Young and Wild and Free:	You can always move freely on Carousel rounds. Gain 2 gold.
"""

origins_data = """
Academy\tThe Academy sponsors 3 items each game. Copies of sponsored items grant bonus max Health and Damage Amp. Academy units holding sponsored items gain double the amount, plus an additional 5% Health and Damage Amp.\t6\tn
Automata\tAutomata gain a crystal when they deal damage. At 20 crystals, they blast their current target, dealing magic damage + 20% of damage dealt since the previous blast and reset. They also gain Armor and Magic Resist.\t6\tn
Black Rose\tEach Black Rose champion's star level increases Sion's power\t7\tn
Chembaron\tGain Shimmer after each player combat. If your loss streak is at least 3, gain more. At each stack of 100 Shimmer, the Black Market offers you contraband that only Chem-Barons can use. Chem-Barons gain max Health for each Black Market you pass on.\t7\tn
Conqueror\tConquerors' takedowns grant stacks of Conquest. After gaining enough Conquest, open War Chests full of loot! Conquerors gain Attack Damage and Ability Power, increased by 3% for each War Chest opened.\t9\tn
Emissary\tThis trait is active only when you have exactly 1 or 4 unique Emissaries. Ambessa: Allies gain 2 Armor and Magic Resist for each opponent defeated. Garen: On Combat Start, Garen and allies to his left and right gain 20% of his max Health. Nami: Allies' attacks grant 2 bonus Mana. Tristana: Allies gain 6% Attack Speed per star level.\t4\tn
Enforcer\tCombat Start: Enforcers gain Shield and Damage Amp. The highest Health enemy units become WANTED! When a Wanted enemy dies, Enforcers gain 30% Attack Speed.\t10\tn
Experiment\tGain Laboratory hexes on your board. Combat start: Experiments standing on Laboratory hexes gain the Experiment bonuses of all Experiments on Laboratory hexes, plus max Health.\t7\tn
Family\tFamily members support each other, reducing their max Mana and gaining extra bonuses.\t5\tn
Firelight\tEvery 6 seconds, Firelights dash. While dashing, they attack with infinite range and heal a percentage of the damage taken since their last dash.\t4\tn
Rebel\tRebels gain 12% max Health. After your team loses 30% of their Health, a smoke signal appears, granting Rebels 60% Attack Speed for 4 seconds and extra power for the rest of combat.\t10\tn
Scrap\tCombat start: Components held by Scrap units temporarily turn into full items. Scrap units gain Shield for 30 seconds for each component held by your team, including those that make up a full item.\t9\tn
Highroller\tWhen casting, Sevika rolls a random Jinx modification to her Ability and gains 80% Durability for 1.50 seconds.\t1\ty
Junker King\tEvery 3 rounds, open an armory to purchase permanent upgrades to your strongest Rumble's mech.\t1\ty
"""

classes_data = """
Ambusher\tDamage from Ambushers' Abilities can critically strike. They also gain bonus Critical Strike Chance and Critical Strike Damage.\t5
Artillerist\tEvery 5 attacks, Artillerists launch a rocket that deals 125% Attack Damage around the target. They also gain Attack Damage.\t6
Bruiser\tYour team gains 100 max Health. Bruisers gain more.\t6
Dominator\tCombat start: Dominators gain a Shield for 15 seconds. When Dominators cast, they gain stacking Ability Power based on the Mana spent.\t6
Form Swap\tInnate: Form Swappers change their stats and ability based on if they're placed in the front 2 rows or back 2 rows. Frontline Form Swappers gain Durability. Backline Form Swappers gain Damage Amp.\t4
Pit Fighter\tPit Fighters gain 15% Omnivamp and deal bonus true damage. Once per combat at 50% Health, they heal a percentage of their max Health over 2 seconds.\t8
Quickstriker\tQuickstrikers move faster and gain Attack Speed, based on their target's missing Health.\t4
Sentinel\tYour team gains Armor and Magic Resist. Sentinels gain triple.\t6
Sniper\tSnipers deal more damage to targets farther away.\t6
Sorcerer\tYour team gains 10 Ability Power. Sorcerers gain more.\t8
Visionary\tWhenever Visionaries gain Mana, they gain more.\t8
Watcher\tWatchers gain Durability, increased while above 50% Health.\t6
"""

# Function to parse the raw champion data into a list of dictionaries
def parse_champion_data(raw_data):
    lines = raw_data.strip().split('\n')
    headers = lines[0].split('\t')
    data_lines = lines[1:]
    champion_list = []

    for line in data_lines:
        if line.strip() == '' or line.startswith('--'):
            continue
        values = line.split('\t')
        champion = dict(zip(headers, values))
        # Split multi-value fields
        champion['Origin'] = [orig.strip() for orig in champion['Origin'].split(',')]
        champion['Class'] = [cls.strip() for cls in champion['Class'].split(',')]
        champion['Roles'] = [role.strip().lower() for role in champion['Roles'].split(',') if role.strip()]
        champion['Effect'] = [effect.strip().lower() for effect in champion.get('Effect', '').split(',') if effect.strip()]
        # Convert numerical fields
        numerical_fields = ['Cost', 'hp', 'armor', 'mr', 'ad', 'ap', 'attack speed', 'mana', 'mana cost', 'attack range']
        for field in numerical_fields:
            value = champion[field]
            try:
                champion[field] = float(value) if value != '' else None
            except ValueError:
                champion[field] = None
        champion_list.append(champion)
    return champion_list

# Parse the champion data
champion_list = parse_champion_data(raw_data)

# Parse the origins and classes data
def parse_origins_data(origins_data):
    origin_list = []
    lines = origins_data.strip().split('\n')
    for line in lines:
        if line.strip() == '':
            continue
        parts = line.strip().split('\t')
        if len(parts) < 4:
            # Handle lines with missing fields
            continue
        origin_info = {
            'name': parts[0].strip(),
            'effect': parts[1].strip(),
            'maxAmount': int(parts[2].strip()),
            'uniqueFlag': parts[3].strip()
        }
        origin_list.append(origin_info)
    return origin_list

def parse_classes_data(classes_data):
    class_list = []
    lines = classes_data.strip().split('\n')
    for line in lines:
        if line.strip() == '':
            continue
        parts = line.strip().split('\t')
        if len(parts) < 3:
            # Handle lines with missing fields
            continue
        class_info = {
            'name': parts[0].strip(),
            'effect': parts[1].strip(),
            'maxAmount': int(parts[2].strip())
        }
        class_list.append(class_info)
    return class_list

origin_list = parse_origins_data(origins_data)
class_list = parse_classes_data(classes_data)

# Insert Origins and Classes into their respective tables
def insert_origins(origin_list):
    for origin in origin_list:
        cursor.execute("""
            INSERT OR IGNORE INTO Origins (name, effect, maxAmount, boolUnique)
            VALUES (?, ?, ?, ?)
        """, (
            origin['name'],
            origin['effect'],
            origin['maxAmount'],
            origin['uniqueFlag']
        ))
    conn.commit()

def insert_classes(class_list):
    for cls in class_list:
        cursor.execute("""
            INSERT OR IGNORE INTO Classes (name, effect, maxAmount)
            VALUES (?, ?, ?)
        """, (
            cls['name'],
            cls['effect'],
            cls['maxAmount']
        ))
    conn.commit()

insert_origins(origin_list)
insert_classes(class_list)

# Insert champions into the Champions table
def insert_champions(champion_list):
    for champ in champion_list:
        cursor.execute("""
            INSERT INTO Champions (name, ability, ad, ap, attack_speed, hp, armor, magic_resist, mana, manaCost, attackRange, cost)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
        """, (
            champ['Champion'],
            champ['ability'],
            champ['ad'],
            champ['ap'],
            champ['attack speed'],
            champ['hp'],
            champ['armor'],
            champ['mr'],
            champ['mana'],
            champ['mana cost'],
            champ['attack range'],
            champ['Cost']
        ))
    conn.commit()

insert_champions(champion_list)

# Establish relationships
def get_origin_id_map():
    cursor.execute("SELECT originID, name FROM Origins")
    origins = cursor.fetchall()
    origin_id_map = {name: originID for originID, name in origins}
    return origin_id_map

def get_class_id_map():
    cursor.execute("SELECT classID, name FROM Classes")
    classes = cursor.fetchall()
    class_id_map = {name: classID for classID, name in classes}
    return class_id_map

def establish_relationships(champion_list):
    origin_id_map = get_origin_id_map()
    class_id_map = get_class_id_map()
    
    # Build effect_id_map and role_id_map
    cursor.execute("SELECT effectID, effect_name FROM Effects")
    effects = cursor.fetchall()
    effect_id_map = {name: effectID for effectID, name in effects}
    cursor.execute("SELECT roleID, role_name FROM Roles")
    roles = cursor.fetchall()
    role_id_map = {name: roleID for roleID, name in roles}
    
    for champ in champion_list:
        # Get championID
        cursor.execute("SELECT championID FROM Champions WHERE name = ?", (champ['Champion'],))
        result = cursor.fetchone()
        if result is None:
            print(f"Champion '{champ['Champion']}' not found in Champions table.")
            continue
        champ_id = result[0]

        # Origins
        for origin in champ['Origin']:
            origin_id = origin_id_map.get(origin)
            if origin_id:
                cursor.execute("""
                    INSERT OR IGNORE INTO Champion_Origins (championID, originID)
                    VALUES (?, ?)
                """, (champ_id, origin_id))
            else:
                print(f"Origin '{origin}' not found for champion '{champ['Champion']}'")

        # Classes
        for cls in champ['Class']:
            class_id = class_id_map.get(cls)
            if class_id:
                cursor.execute("""
                    INSERT OR IGNORE INTO Champion_Classes (championID, classID)
                    VALUES (?, ?)
                """, (champ_id, class_id))
            else:
                print(f"Class '{cls}' not found for champion '{champ['Champion']}'")

        # Roles
        for role in champ['Roles']:
            role_id = role_id_map.get(role)
            if not role_id:
                # Insert new role into Roles table
                cursor.execute("INSERT OR IGNORE INTO Roles (role_name) VALUES (?)", (role,))
                # Fetch the roleID
                cursor.execute("SELECT roleID FROM Roles WHERE role_name = ?", (role,))
                role_id = cursor.fetchone()[0]
                role_id_map[role] = role_id
            cursor.execute("""
                INSERT OR IGNORE INTO Champion_Roles (championID, roleID)
                VALUES (?, ?)
            """, (champ_id, role_id))

        # Effects
        for effect in champ['Effect']:
            effect_id = effect_id_map.get(effect)
            if not effect_id:
                # Insert new effect into Effects table
                cursor.execute("INSERT OR IGNORE INTO Effects (effect_name) VALUES (?)", (effect,))
                # Fetch the effectID
                cursor.execute("SELECT effectID FROM Effects WHERE effect_name = ?", (effect,))
                effect_id = cursor.fetchone()[0]
                effect_id_map[effect] = effect_id
            cursor.execute("""
                INSERT OR IGNORE INTO Champion_Effects (championID, effectID)
                VALUES (?, ?)
            """, (champ_id, effect_id))

    conn.commit()

establish_relationships(champion_list)

# Functions to insert data into tables
# def insert_unique_entries(table_name, column_name, items):
#     for item in items:
#         cursor.execute(f"INSERT OR IGNORE INTO {table_name} ({column_name}) VALUES (?)", (item,))
#     conn.commit()

# Mapping of table names to primary key column names
table_pk_column = {
    'Champions': 'championID',
    'Classes': 'classID',
    'Origins': 'originID',
    'Effects': 'effectID',
    'Roles': 'roleID',
    # Add other tables if necessary
}

def get_id(table_name, column_name, value):
    pk_column_name = table_pk_column.get(table_name)
    if not pk_column_name:
        # Handle tables not in the mapping
        pk_column_name = table_name[:-1] + 'ID'
    cursor.execute(f"SELECT {pk_column_name} FROM {table_name} WHERE {column_name} = ?", (value,))
    result = cursor.fetchone()
    return result[0] if result else None

# def get_id(table_name, column_name, value):
#     cursor.execute(f"SELECT {table_name[:-1] + 'ID'} FROM {table_name} WHERE {column_name} = ?", (value,))
#     result = cursor.fetchone()
#     return result[0] if result else None

# Updated parsing function
def parse_augment_data(augment_data):
    augment_list = []
    lines = augment_data.strip().split('\n')
    for line in lines:
        line = line.strip()
        if ':' in line:
            # Split at the first colon
            name, effect = line.split(':', 1)
            augment_list.append({
                'name': name.strip(),
                'effect': effect.strip()
            })
        else:
            # Line does not contain a colon, skip or handle accordingly
            continue
    return augment_list

# Parse the data
augment_list = parse_augment_data(augment_data)

# Function to insert augments into the Augments table
def insert_augments(augment_list):
    for augment in augment_list:
        cursor.execute("""
            INSERT OR IGNORE INTO Augments (name, effect)
            VALUES (?, ?)
        """, (
            augment['name'],
            augment['effect']
        ))
    conn.commit()

# Insert augments into the Augments table
insert_augments(augment_list)

# Collect all unique origins, classes, effects, and roles
# origins_set = set()
# classes_set = set()
effects_set = set()
roles_set = set()
for champ in champion_list:
    # origins_set.update(champ['Origin'])
    # classes_set.update(champ['Class'])
    effects_set.update(champ['Effect'])
    roles_set.update(champ['Roles'])

# Insert unique entries into respective tables
# insert_unique_entries('Origins', 'name', origins_set)
# insert_unique_entries('Classes', 'name', classes_set)
# insert_unique_entries('Effects', 'effect_name', effects_set)
# insert_unique_entries('Roles', 'role_name', roles_set)

# Insert champions into the Champions table
insert_augments(augment_list)

# Define the component data
components_data = [
    {"name": "B.F. Sword", "ad": 10, "ap": 0, "attack_speed": 0, "hp": 0, "armor": 0, "magic_resist": 0, "mana": 0, "crit": 0},
    {"name": "Chain Vest", "ad": 0, "ap": 0, "attack_speed": 0, "hp": 0, "armor": 20, "magic_resist": 0, "mana": 0, "crit": 0},
    {"name": "Giant's Belt", "ad": 0, "ap": 0, "attack_speed": 0, "hp": 150, "armor": 0, "magic_resist": 0, "mana": 0, "crit": 0},
    {"name": "Needlessly Large Rod", "ad": 0, "ap": 10, "attack_speed": 0, "hp": 0, "armor": 0, "magic_resist": 0, "mana": 0, "crit": 0},
    {"name": "Negatron Cloak", "ad": 0, "ap": 0, "attack_speed": 0, "hp": 0, "armor": 0, "magic_resist": 20, "mana": 0, "crit": 0},
    {"name": "Recurve Bow", "ad": 0, "ap": 0, "attack_speed": 10, "hp": 0, "armor": 0, "magic_resist": 0, "mana": 0, "crit": 0},
    {"name": "Sparring Gloves", "ad": 0, "ap": 0, "attack_speed": 0, "hp": 0, "armor": 0, "magic_resist": 0, "mana": 0, "crit": 20},
    {"name": "Tear of the Goddess", "ad": 0, "ap": 0, "attack_speed": 0, "hp": 0, "armor": 0, "magic_resist": 0, "mana": 15, "crit": 0},
    {"name": "Spatula", "ad": 0, "ap": 0, "attack_speed": 0, "hp": 0, "armor": 0, "magic_resist": 0, "mana": 0, "crit": 0},
    {"name": "Frying Pan", "ad": 0, "ap": 0, "attack_speed": 0, "hp": 0, "armor": 0, "magic_resist": 0, "mana": 0, "crit": 0},
]

# Function to insert components into the Components table
def insert_components(components_data):
    for component in components_data:
        cursor.execute("""
            INSERT OR IGNORE INTO Components (name, ad, ap, attack_speed, hp, armor, magic_resist, mana, crit)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)
        """, (
            component["name"],
            component["ad"],
            component["ap"],
            component["attack_speed"],
            component["hp"],
            component["armor"],
            component["magic_resist"],
            component["mana"],
            component["crit"]
        ))
    conn.commit()

# Insert components into the database
insert_components(components_data)


# Commit the changes and close the connection
conn.commit()
conn.close()

print("Database 'tft_database.db' created and populated successfully.")
