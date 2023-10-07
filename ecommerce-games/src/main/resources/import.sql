-- This file allow to write SQL commands that will be emitted in test and dev.
-- The commands are commented as their support depends of the database
-- insert into myentity (id, field) values(1, 'field-1');
-- insert into myentity (id, field) values(2, 'field-2');
-- insert into myentity (id, field) values(3, 'field-3');
-- alter sequence myentity_seq restart with 4;


INSERT INTO genero (nome) VALUES ('Terror');
INSERT INTO genero (nome) VALUES ('Action RPG');
INSERT INTO genero (nome) VALUES ('Luta');
INSERT INTO genero (nome) VALUES ('Puzzle');
INSERT INTO genero (nome) VALUES ('RPG de turno');


INSERT INTO fabricante (nome, anoFundacao) VALUES ('Nintendo', '1888-01-28');
INSERT INTO fabricante (nome, anoFundacao) VALUES ('Sony', '1977-11-09');
INSERT INTO fabricante (nome, anoFundacao) VALUES ('Microsoft', '1975-05-10');
INSERT INTO fabricante (nome, anoFundacao) VALUES ('SEGA', '1979-09-29');
INSERT INTO fabricante (nome, anoFundacao) VALUES ('Atari', '1978-02-11');



INSERT INTO plataforma (nome, descricao, anoLancamento, id_fabricante) 
       VALUES ('PlayStation 5', '', '2021-05-30', 2);
INSERT INTO plataforma (nome, descricao, anoLancamento, id_fabricante) 
       VALUES ('Nintendo Switch', '', '2017-01-22', 1);
INSERT INTO plataforma (nome, descricao, anoLancamento, id_fabricante) 
       VALUES ('XBox Series X', '', '2021-11-18', 3);
INSERT INTO plataforma (nome, descricao, anoLancamento, id_fabricante) 
       VALUES ('SEGA Dreamcast', '', '1998-04-17', 4);
INSERT INTO plataforma (nome, descricao, anoLancamento, id_fabricante) 
       VALUES ('Xbox One', '', '2013-01-05', 3);

