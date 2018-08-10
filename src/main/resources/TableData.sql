USE BMTRS;

DELETE FROM Visitor;
DELETE FROM Administrator;
DELETE FROM Museum;
DELETE FROM Exhibit;
DELETE FROM Ticket;
DELETE FROM Review;
DELETE FROM Curator_Request;
INSERT INTO Visitor
VALUES      ('bobsmith@gmail.com', 'bobsmith123', 123, 1234123412341234, 2022, 11);
INSERT INTO Visitor
VALUES      ('manleyroberts@gatech.edu', 'iloveprem', 632, 1223334444555556, 2021, 1);
INSERT INTO Visitor
VALUES      ('groot@yahoo.com', 'iamgroot', 123, 6969696969696969, 2068, 12);
INSERT INTO Visitor
VALUES      ('sabirkhan@gatech.edu', 'iheartbarcelona', 999, 9876543210123456, 2023, 6);
INSERT INTO Visitor
VALUES      ('daniel@gatech.edu', 'bilingual', 123, 1234567812345670, 2020, 1);
INSERT INTO Visitor
VALUES      ('helen@gatech.edu', 'architecture4ever', 456, 8765432187654320, 2021, 2);
INSERT INTO Visitor
VALUES      ('zoe@gatech.edu', 'yogasister', 789, 2468135924681350, 2022, 3);
INSERT INTO Administrator
VALUES      ('alex@gatech.edu', 'iamadmin');
INSERT INTO Museum
VALUES      ('Picasso Museum', NULL, 50.00);
INSERT INTO Museum
VALUES      ('CCCB', 'helen@gatech.edu', 20.18);
INSERT INTO Museum
VALUES      ('MACBA', 'zoe@gatech.edu', 1.25);
INSERT INTO Museum
VALUES      ('Life of Sabir', 'sabirkhan@gatech.edu', 0.0);
INSERT INTO Exhibit
VALUES      ('Picasso: Early Life', 'Picasso Museum', 1969, NULL);
INSERT INTO Exhibit
VALUES      ('Geometric Shapes', 'Picasso Museum', 1900, 'www.picassomuseo.com/geo/');
INSERT INTO Exhibit
VALUES      ('Black Light 1', 'CCCB', 1985, 'www.cccb.com/bl1');
INSERT INTO Exhibit
VALUES      ('Black Light 2', 'CCCB', 1986, 'www.cccb.com/bl2');
INSERT INTO Exhibit
VALUES      ('Architecture 3135', 'CCCB', 2004, 'http://www.gatech.edu/');
INSERT INTO Exhibit
VALUES      ('Bird', 'MACBA', 2018, 'www.macba.es/bird/');
INSERT INTO Exhibit
VALUES      ('Plane', 'MACBA', 2018, 'www.macba.es/plane/');
INSERT INTO Exhibit
VALUES      ('Train', 'MACBA', 2018, 'www.macba.es/train/');
INSERT INTO Ticket
VALUES      ('bobsmith@gmail.com', 'MACBA', '2018-04-04 00:08:27', 50.00);
INSERT INTO Ticket
VALUES      ('sabirkhan@gatech.edu', 'Picasso Museum', '2014-02-14 14:38:00', 20.14);
INSERT INTO Ticket
VALUES      ('zoe@gatech.edu', 'MACBA', '2018-05-20', 5);
INSERT INTO Ticket
VALUES      ('helen@gatech.edu', 'Picasso Museum', '2018-06-11', 20);
INSERT INTO Ticket
VALUES      ('helen@gatech.edu', 'CCCB', '2018-06-29', 50);
INSERT INTO Ticket
VALUES      ('bobsmith@gmail.com', 'CCCB', '2016-07-04', 35);
INSERT INTO Review
VALUES      ('zoe@gatech.edu', 'MACBA', "Didn't get it", 1);
INSERT INTO Review
VALUES      ('helen@gatech.edu', 'Picasso Museum', 'So many shapes!', 5);
INSERT INTO Review
VALUES      ('helen@gatech.edu', 'CCCB', 'Scary, but cool', 3);
INSERT INTO Review
VALUES      ('bobsmith@gmail.com', 'CCCB', 'Was a good time! Lots of cool things!', 5);
INSERT INTO Curator_Request
VALUES      ('groot@yahoo.com', 'Picasso Museum', '2017-06-19', NULL);
INSERT INTO Curator_Request
VALUES      ('bobsmith@gmail.com', 'CCCB', '2018-07-08', FALSE);
INSERT INTO Curator_Request
VALUES      ('zoe@gatech.edu', 'Picasso Museum', '2017-06-19', NULL);