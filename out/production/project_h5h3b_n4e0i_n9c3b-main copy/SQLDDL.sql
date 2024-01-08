DROP TABLE Teams CASCADE CONSTRAINTS;
DROP TABLE Team_Members CASCADE CONSTRAINTS;
DROP TABLE Coaches CASCADE CONSTRAINTS;
DROP TABLE Players CASCADE CONSTRAINTS;
DROP TABLE Train CASCADE CONSTRAINTS;
DROP TABLE Referees CASCADE CONSTRAINTS;
DROP TABLE Spectators CASCADE CONSTRAINTS;
DROP TABLE Tournaments CASCADE CONSTRAINTS;
DROP TABLE Stadiums CASCADE CONSTRAINTS;
DROP TABLE Tickets CASCADE CONSTRAINTS;
DROP TABLE Matches CASCADE CONSTRAINTS;
DROP TABLE Oversee CASCADE CONSTRAINTS;
DROP TABLE Watch CASCADE CONSTRAINTS;
DROP TABLE Play CASCADE CONSTRAINTS;

CREATE TABLE Teams (
                       home_city char(100),
                       name      char(50),
                       net_worth decimal,
                       wins int,
                       losses int,
                       matches_played int,
                       PRIMARY KEY (home_city, name)
);

CREATE TABLE Team_Members(
                             home_city   char(100)   not NULL,
                             team_name   char(50)    not NULL,
                             sin         int,
                             member_name char(50),
                             age         int,
                             PRIMARY KEY (home_city, team_name, sin),
                             FOREIGN KEY (home_city, team_name) REFERENCES Teams (home_city, name)
                                 ON DELETE CASCADE
);

CREATE TABLE Coaches (
                         home_city   char(100)   not NULL,
                         team_name   char(50)   not NULL,
                         sin         int,
                         PRIMARY KEY (home_city, team_name, sin),
                         FOREIGN KEY (home_city, team_name, sin) REFERENCES Team_Members (home_city, team_name, sin)
                             ON DELETE CASCADE
);

CREATE TABLE Players (
                         home_city   char(100)   not NULL,
                         team_name   char(50)   not NULL,
                         sin         int,
                         jersey_no   int,
                         role        char(50),
                         PRIMARY KEY (home_city, team_name, sin),
                         FOREIGN KEY (home_city, team_name, sin) REFERENCES Team_Members (home_city, team_name, sin)
                             ON DELETE CASCADE
);

-- Assert coaches can only train players in the same team as them
CREATE TABLE Train(
                      coach_home_city  char(100)   not NULL,
                      coach_team_name  char(50)    not NULL,
                      coach_sin        int,
                      player_home_city char(100)   not NULL,
                      player_team_name char(50)    not NULL,
                      player_sin       int         not NULL,
                      PRIMARY KEY (coach_home_city, coach_team_name, coach_sin,
                                   player_home_city, player_team_name, player_sin),
                      FOREIGN KEY (coach_home_city, coach_team_name, coach_sin) REFERENCES Team_Members (home_city, team_name, sin)
                          ON DELETE CASCADE,
                      FOREIGN KEY (player_home_city, player_team_name, player_sin) REFERENCES Team_Members (home_city, team_name, sin)
                          ON DELETE CASCADE
);

CREATE TABLE Referees (
                          id      int,
                          name    char(50),
                          PRIMARY KEY (id)
);

CREATE TABLE Spectators (
                            sin         int,
                            name        char(50),
                            PRIMARY KEY (sin)
);

CREATE TABLE Tournaments (
                             name        char(100),
                             season      int,
                             PRIMARY KEY (name, season)
);

CREATE TABLE Stadiums (
                          name        char(100),
                          location    char(100),
                          PRIMARY KEY (name, location)
);

CREATE TABLE Tickets (
                         ticket_id int,
                         sin int not NULL,
                         price decimal,
                         date_of_purchase char(10),
                         seat_no      int,
                         date_of_game char(10),
                         PRIMARY KEY (ticket_id),
                         FOREIGN KEY (sin) REFERENCES Spectators (sin)
                             ON DELETE CASCADE
);

CREATE TABLE Matches (
                         match_id         int,
                         tournament_name  char(100)  not NULL,
                         season           int        not NULL,
                         stadium_name     char(100)  not NULL,
                         location         char(100)  not NULL,
                         date_and_time    char(16),
                         PRIMARY KEY (match_id),
                         FOREIGN KEY (tournament_name, season) REFERENCES Tournaments (name, season)
                             ON DELETE CASCADE,
                         FOREIGN KEY (stadium_name, location) REFERENCES Stadiums (name, location)
                             ON DELETE CASCADE
);

CREATE TABLE Oversee (
                         ref_id    int,
                         match_id  int,
                         PRIMARY KEY (ref_id, match_id),
                         FOREIGN KEY (ref_id) REFERENCES Referees (id)
                             ON DELETE CASCADE,
                         FOREIGN KEY (match_id) REFERENCES Matches (match_id)
                             ON DELETE CASCADE
);

CREATE TABLE Watch (
                       match_id    int,
                       sin         int,
                       PRIMARY KEY (match_id, sin),
                       FOREIGN KEY (match_id) REFERENCES Matches (match_id)
                           ON DELETE CASCADE,
                       FOREIGN KEY (sin) REFERENCES Spectators (sin)
                           ON DELETE CASCADE
);


CREATE TABLE Play (
                      home_city   char(100)   not NULL,
                      name        char(50)    not NULL,
                      match_id    int,
                      PRIMARY KEY (home_city, name, match_id),
                      FOREIGN KEY (home_city, name) REFERENCES Teams (home_city, name)
                          ON DELETE CASCADE,
                      FOREIGN KEY (match_id) REFERENCES Matches (match_id)
                          ON DELETE CASCADE
);

INSERT INTO Teams VALUES ('Vancouver', 'Canucks', 100000.00, 9, 4, 13);
INSERT INTO Teams VALUES ('Toronto', 'Maple Leafs', 200000.00, 5, 5, 10);
INSERT INTO Teams VALUES ('Montreal', 'Canadians', 99999.99, 10, 0, 10);
INSERT INTO Teams VALUES ('Edmonton', 'Oilers', 5000.00, 7, 4, 11);
INSERT INTO Teams VALUES ('Calgary', 'Flames', 700000.00, 1, 12, 13);
INSERT INTO Teams VALUES ('Vancouver', 'Rocket', 9001.00, 8, 11, 19);
INSERT INTO Teams VALUES ('Toronto', 'Flare', 69000.0, 20, 29, 49);
INSERT INTO Teams VALUES ('Montreal', 'Plasma', 77777.77, 12, 8, 20);
INSERT INTO Teams VALUES ('Edmonton', 'Black Knights', 5030.00, 7, 14, 21);
INSERT INTO Teams VALUES ('Vancouver', 'Britannia', 42000.00, 18, 14, 32);
INSERT INTO Teams VALUES ('Toronto', 'Evil Strike Force', 200300.00, 27, 5, 32);
INSERT INTO Teams VALUES ('Montreal', 'Galactic', 1.0, 10, 8, 18);
INSERT INTO Teams VALUES ('Edmonton', 'Magma', 7800.00, 13, 14, 27);
INSERT INTO Teams VALUES ('Calgary', 'Aqua', 700933.00, 8, 13, 21);
INSERT INTO Teams VALUES ('Calgary', 'Skull', 78730.00, 0, 11, 11);

INSERT INTO Team_Members VALUES ('Vancouver', 'Canucks', 123456789, 'Some Coach', 44);
INSERT INTO Team_Members VALUES ('Toronto', 'Maple Leafs', 121212121, 'Sol Dudeguy', 28);
INSERT INTO Team_Members VALUES ('Montreal', 'Canadians', 884974349, 'Big Bob', 51);
INSERT INTO Team_Members VALUES ('Edmonton', 'Oilers', 000000001, 'Yoda', 100);
INSERT INTO Team_Members VALUES ('Calgary', 'Flames', 339483239, 'Gigachad', 69);

INSERT INTO Team_Members VALUES ('Vancouver', 'Canucks', 547932345, 'Some Player', 21);
INSERT INTO Team_Members VALUES ('Toronto', 'Maple Leafs', 987654321, 'Ky Kooskey', 23);
INSERT INTO Team_Members VALUES ('Montreal', 'Canadians', 007008009, 'Mudrock', 29);
INSERT INTO Team_Members VALUES ('Edmonton', 'Oilers', 400400400, 'Luke', 17);
INSERT INTO Team_Members VALUES ('Calgary', 'Flames', 483250283, 'Noob', 18);

INSERT INTO Coaches VALUES ('Vancouver', 'Canucks', 123456789);
INSERT INTO Coaches VALUES ('Toronto', 'Maple Leafs', 121212121);
INSERT INTO Coaches VALUES ('Montreal', 'Canadians', 884974349);
INSERT INTO Coaches VALUES ('Edmonton', 'Oilers', 000000001);
INSERT INTO Coaches VALUES ('Calgary', 'Flames', 339483239);

INSERT INTO Players VALUES ('Vancouver', 'Canucks', 547932345, 8, 'Forward');
INSERT INTO Players VALUES ('Toronto', 'Maple Leafs', 987654321, 7, 'Defence');
INSERT INTO Players VALUES ('Montreal', 'Canadians', 007008009, 1, 'Goalie');
INSERT INTO Players VALUES ('Edmonton', 'Oilers', 400400400, 4, 'Forward');
INSERT INTO Players VALUES ('Calgary', 'Flames', 483250283, 10, 'Defence');

INSERT INTO Train VALUES ('Vancouver', 'Canucks', 123456789, 'Vancouver', 'Canucks', 547932345);
INSERT INTO Train VALUES ('Toronto', 'Maple Leafs', 121212121, 'Toronto', 'Maple Leafs', 987654321);
INSERT INTO Train VALUES ('Montreal', 'Canadians', 884974349, 'Montreal', 'Canadians', 007008009);
INSERT INTO Train VALUES ('Edmonton', 'Oilers', 000000001, 'Edmonton', 'Oilers', 400400400);
INSERT INTO Train VALUES ('Calgary', 'Flames', 339483239, 'Calgary', 'Flames', 483250283);

INSERT INTO Referees VALUES (1, 'Broski');
INSERT INTO Referees VALUES (2, 'Buddy');
INSERT INTO Referees VALUES (3, 'Pal');
INSERT INTO Referees VALUES (4, 'Brotha');
INSERT INTO Referees VALUES (5, 'My Man');

INSERT INTO Spectators VALUES (196596859, 'Simp');
INSERT INTO Spectators VALUES (295060560, '#1 Fan');
INSERT INTO Spectators VALUES (309450503, 'Rager');
INSERT INTO Spectators VALUES (980850440, 'Mr. Watcher');
INSERT INTO Spectators VALUES (294454095, 'John');

INSERT INTO Tournaments VALUES ('Piston Cup', 2001);
INSERT INTO Tournaments VALUES ('NHL League', 2022);
INSERT INTO Tournaments VALUES ('Little League Hockey', 2024);
INSERT INTO Tournaments VALUES ('Stanley Cup', 1998);
INSERT INTO Tournaments VALUES ('Cyberpunk', 2077);

INSERT INTO Stadiums VALUES ('Rogers Arena', 'Vancouver');
INSERT INTO Stadiums VALUES ('Some Place', 'Some City');
INSERT INTO Stadiums VALUES ('Pokemon League Building', 'Sinnoh');
INSERT INTO Stadiums VALUES ('Death Star', 'Space');
INSERT INTO Stadiums VALUES ('Arasaka Tower', 'Night City');

INSERT INTO Tickets VALUES (123, 196596859, 19.99, '2022-04-20', 1, '2022-04-31');
INSERT INTO Tickets VALUES (456, 295060560, 19.99, '2010-01-19', 2, '2010-02-14');
INSERT INTO Tickets VALUES (789, 309450503, 31.99, '1988-12-31', 3, '1989-01-24');
INSERT INTO Tickets VALUES (007, 980850440, 31.99, '2044-03-03', 4, '2044-03-04');
INSERT INTO Tickets VALUES (420, 294454095, 49.99, '1999-11-14', 5, '1999-11-22');

INSERT INTO Matches VALUES (1, 'Piston Cup', 2001, 'Rogers Arena', 'Vancouver', '2022-04-31 16:00');
INSERT INTO Matches VALUES (2, 'NHL League', 2022, 'Some Place', 'Some City', '2010-02-14 15:00');
INSERT INTO Matches VALUES (3, 'Little League Hockey', 2024, 'Pokemon League Building', 'Sinnoh', '1989-01-24 08:00');
INSERT INTO Matches VALUES (4, 'Stanley Cup', 1998, 'Death Star', 'Space', '2044-03-04 18:00');
INSERT INTO Matches VALUES (5, 'Cyberpunk', 2077, 'Arasaka Tower', 'Night City', '1999-11-22 21:00');

INSERT INTO Oversee VALUES (1, 1);
INSERT INTO Oversee VALUES (2, 2);
INSERT INTO Oversee VALUES (3, 3);
INSERT INTO Oversee VALUES (4, 4);
INSERT INTO Oversee VALUES (5, 5);

INSERT INTO Watch VALUES (1, 196596859);
INSERT INTO Watch VALUES (2, 295060560);
INSERT INTO Watch VALUES (3, 309450503);
INSERT INTO Watch VALUES (4, 980850440);
INSERT INTO Watch VALUES (5, 294454095);
INSERT INTO Watch VALUES (2, 196596859);
INSERT INTO Watch VALUES (3, 196596859);
INSERT INTO Watch VALUES (4, 196596859);
INSERT INTO Watch VALUES (5, 196596859);
INSERT INTO Watch VALUES (1, 980850440);
INSERT INTO Watch VALUES (2, 980850440);
INSERT INTO Watch VALUES (3, 980850440);
INSERT INTO Watch VALUES (5, 980850440);

INSERT INTO Play VALUES ('Vancouver', 'Canucks', 1);
INSERT INTO PLAY VALUES ('Toronto', 'Maple Leafs', 1);
INSERT INTO Play VALUES ('Toronto', 'Maple Leafs', 2);
INSERT INTO Play VALUES ('Edmonton', 'Oilers', 2);
INSERT INTO Play VALUES ('Montreal', 'Canadians', 3);
INSERT INTO Play VALUES ('Calgary', 'Flames', 3);
INSERT INTO Play VALUES ('Edmonton', 'Oilers', 4);
INSERT INTO Play VALUES ('Montreal', 'Canadians', 4);
INSERT INTO Play VALUES ('Vancouver', 'Canucks', 5);
INSERT INTO Play VALUES ('Calgary', 'Flames', 5);