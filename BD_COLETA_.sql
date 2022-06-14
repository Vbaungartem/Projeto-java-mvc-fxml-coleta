-- CRIAÇÃO DO BANCO

CREATE DATABASE coleta
    WITH
    OWNER = postgres
    ENCODING = 'UTF8'
    CONNECTION LIMIT = -1;


-- CRIAÇÃO DAS TABELAS

CREATE TABLE motorista(
   id serial      NOT NULL,
   nome varchar(50) NOT NULL,
   cpf varchar(50) NOT NULL,
   placa_caminhao varchar(50) NOT NULL,
   saldo float,   
   CONSTRAINT pk_motorista
      PRIMARY KEY(id)
);

CREATE TABLE tanque(
	id serial NOT NULL,
	nome_tanque varchar(50) NOT NULL,
	capacidade int NOT NULL,
	CONSTRAINT pk_tanque
		PRIMARY KEY(id)
);

CREATE TABLE coleta(
   id serial      NOT NULL,
   origem_leite varchar(50) NOT NULL,
   litragem float     NOT NULL,
   pesagem_entrada int     NOT NULL,
   nota_coleta varchar(50),
   repasse_valor_litro float,
   motorista_id int     NOT NULL,
   tanque_id int 		NOT NULL,
   data date NOT NULL, 
   CONSTRAINT pk_coleta
      PRIMARY KEY(id),
   CONSTRAINT fk_coleta_motorista
      FOREIGN KEY(motorista_id)
      REFERENCES motorista(id),
   CONSTRAINT fk_coleta_tanque
	  FOREIGN KEY (tanque_id)
	  REFERENCES tanque(id)
);

-- INSERT MOTORISTAS INICIAIS

INSERT INTO motorista(nome, cpf, placa_caminhao, saldo) VALUES('Ernesto','111.111.111-11','ABCD-111', 1000.0);
INSERT INTO motorista(nome, cpf, placa_caminhao, saldo) VALUES('Nelson','222.222.222-22', 'ABCD-222', 1000.0);
INSERT INTO motorista(nome, cpf, placa_caminhao, saldo) VALUES('Marcos','333.333.333-33', 'ABCD-333', 1000.0);
INSERT INTO motorista(nome, cpf, placa_caminhao, saldo) VALUES('Lucas','444.444.444-44', 'ABCD-444', 1000.0);


-- INSERT TANQUES INICIAIS

INSERT INTO tanque(nome_tanque, capacidade) VALUES('Tanque 01', 5000);
INSERT INTO tanque(nome_tanque, capacidade) VALUES('Tanque 02', 5000);



-- INSERTS COLETAS INICIAIS 

-- COLETA -> MOTORISTA 01
INSERT INTO coleta(origem_leite, litragem, pesagem_entrada, nota_coleta, repasse_valor_litro, motorista_id, tanque_id, data) VALUES('Fanzeda Feliz', 100, 15000, 'B', 250, 1, 1, '01/01/2022');
INSERT INTO coleta(origem_leite, litragem, pesagem_entrada, nota_coleta, repasse_valor_litro, motorista_id, tanque_id, data) VALUES('Fanzeda Feliz', 100, 15000, 'B', 250, 1, 1, '10/02/2022');
INSERT INTO coleta(origem_leite, litragem, pesagem_entrada, nota_coleta, repasse_valor_litro, motorista_id, tanque_id, data) VALUES('Fanzeda Feliz', 100, 15000, 'B', 250, 1, 1, '12/02/2022');
INSERT INTO coleta(origem_leite, litragem, pesagem_entrada, nota_coleta, repasse_valor_litro, motorista_id, tanque_id, data) VALUES('Fanzeda Feliz', 100, 15000, 'D', 75, 1, 1, '05/02/2022');

-- COLETA -> MOTORISTA 02
INSERT INTO coleta(origem_leite, litragem, pesagem_entrada, nota_coleta, repasse_valor_litro, motorista_id, tanque_id, data) VALUES('Fazenda Mococa', 100, 18000, 'A',  300, 2, 1, '03/01/2022');
INSERT INTO coleta(origem_leite, litragem, pesagem_entrada, nota_coleta, repasse_valor_litro, motorista_id, tanque_id, data) VALUES('Fazenda Mococa', 100, 18000, 'C',  150, 2, 1, '08/01/2022');
INSERT INTO coleta(origem_leite, litragem, pesagem_entrada, nota_coleta, repasse_valor_litro, motorista_id, tanque_id, data) VALUES('Fazenda Mococa', 100, 18000, 'C',  150, 2, 1, '13/02/2022');
INSERT INTO coleta(origem_leite, litragem, pesagem_entrada, nota_coleta, repasse_valor_litro, motorista_id, tanque_id, data) VALUES('Fazenda Mococa', 100, 18000, 'B',  250, 2, 1, '20/02/2022');
INSERT INTO coleta(origem_leite, litragem, pesagem_entrada, nota_coleta, repasse_valor_litro, motorista_id, tanque_id, data) VALUES('Fazenda Mococa', 100, 18000, 'B',  250, 2, 1, '25/02/2022');

-- COLETA -> MOTORISTA 03
INSERT INTO coleta(origem_leite, litragem, pesagem_entrada, nota_coleta, repasse_valor_litro, motorista_id, tanque_id, data) VALUES('Rancho do Sol', 100, 23000, 'A', 300, 3, 1, '06/05/2022');
INSERT INTO coleta(origem_leite, litragem, pesagem_entrada, nota_coleta, repasse_valor_litro, motorista_id, tanque_id, data) VALUES('Rancho do Sol', 100, 23000, 'A', 300, 3, 1, '09/05/2022');
INSERT INTO coleta(origem_leite, litragem, pesagem_entrada, nota_coleta, repasse_valor_litro, motorista_id, tanque_id, data) VALUES('Rancho do Sol', 100, 23000, 'C', 150, 3, 1, '10/05/2022');
INSERT INTO coleta(origem_leite, litragem, pesagem_entrada, nota_coleta, repasse_valor_litro, motorista_id, tanque_id, data) VALUES('Rancho do Sol', 100, 23000, 'C', 150, 3, 1, '15/09/2022');

-- COLETA -> MOTORISTA 04
INSERT INTO coleta(origem_leite, litragem, pesagem_entrada, nota_coleta, repasse_valor_litro, motorista_id, tanque_id, data) VALUES('Fazenda Marivalda', 100, 23000, 'A', 300, 4, 1, '01/06/2022');
INSERT INTO coleta(origem_leite, litragem, pesagem_entrada, nota_coleta, repasse_valor_litro, motorista_id, tanque_id, data) VALUES('Fazenda Marivalda', 100, 23000, 'A', 300, 4, 1, '05/06/2022');
INSERT INTO coleta(origem_leite, litragem, pesagem_entrada, nota_coleta, repasse_valor_litro, motorista_id, tanque_id, data) VALUES('Fazenda Marivalda', 100, 23000, 'B', 250, 4, 1, '10/06/2022');
INSERT INTO coleta(origem_leite, litragem, pesagem_entrada, nota_coleta, repasse_valor_litro, motorista_id, tanque_id, data) VALUES('Fazenda Marivalda', 100, 23000, 'B', 250, 4, 1, '12/06/2022');


-- QUERY DO RELATÓRIO
SELECT motorista.nome, 
	SUM (coleta.litragem) AS "Litragem Total", 
	SUM (coleta.repasse_valor_litro) AS "Repasse Total"
FROM coleta 
INNER JOIN motorista 
	ON coleta.motorista_id = motorista.id
GROUP BY motorista.nome;





















