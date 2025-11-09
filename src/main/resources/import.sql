insert into pessoa(email, nome) values ('bruno@gmail.com', 'Bruno');
insert into pessoafisica(id, cpf) values (1, '078.603.371-18');
insert into cliente (id) values (1);

insert into pessoa(email, nome) values ('sony@gmail.com', 'Sony');
insert into pessoajuridica(id, cnpj) values (2, '07.354.235/1845-03');
insert into fabricante (id) values (2);

insert into pessoa(email, nome) values ('microsoft@gmail.com', 'Microsoft');
insert into pessoajuridica(id, cnpj) values (3, '07.354.384/1845-03');
insert into fabricante (id) values (3);

insert into pessoa(email, nome) values ('nintendo@gmail.com', 'Nintendo');
insert into pessoajuridica(id, cnpj) values (4, '07.018.235/1845-03');
insert into fabricante (id) values (4);

insert into telefone (numero) values 
    ('(63) 99999-9999'),
    ('(63) 98888-8888'),
    ('(63) 97777-7777');

insert into endereco (pessoa_id, rua, numero, bairro, cidade, estado, cep)
values 
    (1, 'Rua A', '123', 'Bairro A', 'Cidade A', 'SP', '77001-234'),
    (2, 'Rua B', '456', 'Bairro B', 'Cidade B', 'RJ', '77002-345'),
    (1, 'Rua C', '789', 'Bairro C', 'Cidade C', 'MG', '77003-456');


insert into plataforma (nome) values ('PC'), ('PlayStation'), ('Xbox'), ('Nintendo');

insert into lote (quantidade, descricao) values 
    (100, 'Estoque Central'),
    (50, 'Estoque Secundário'),
    (200, 'Estoque Terciário'),
    (75, 'Estoque Quaternário'),
    (150, 'Estoque Quinário'),
    (80, 'Estoque Extra'),
    (60, 'Estoque Reserva'), 
    (90, 'Estoque Adicional');

insert into controle (nome, id_fabricante, cor, preco, conexao, alimentacao, touchpad, gatilhosadaptaveis, estoque) 
values
    ('Controle DualShock 4', 2, 1, 349, 'Bluetooth', 'Bateria', true, false, 50),
    ('Controle Xbox One', 3, 2, 328, 'Wireless', 'Pilhas', false, false, 20),
    ('Controle Pro Switch', 4, 3, 1450, 'USB-C', 'Bateria', true, true, 78),
    ('Controle Elite Series 2', 3, 4, 999, 'Wireless', 'Bateria', false, true, 15),
    ('Controle Razer Wolverine', 3, 5, 799, 'USB', 'Cabo', false, false, 30),
    ('Controle Scuf Impact', 3, 2, 899, 'Wireless', 'Bateria', false, true, 25),
    ('Controle Nacon Revolution', 2, 1, 699, 'USB', 'Cabo', false, false, 40),
    ('Controle Hori Fighting Commander', 4, 3, 299, 'USB', 'Cabo', false, false, 60),
    ('Controle Astro C40', 3, 2, 1299, 'USB', 'Cabo', true, true, 10),
    ('Controle PDP Afterglow', 3, 5, 199, 'USB', 'Cabo', false, false, 70),
    ('Controle Extra 11', 2, 1, 260, 'USB', 'Cabo', false, false, 18),
    ('Controle Extra 12', 3, 2, 270, 'Wireless', 'Bateria', true, false, 22),
    ('Controle Extra 13', 4, 3, 310, 'Bluetooth', 'Bateria', false, true, 35),
    ('Controle Extra 14', 2, 4, 220, 'USB-C', 'Bateria', true, true, 12),
    ('Controle Extra 15', 3, 5, 199, 'USB', 'Cabo', false, false, 40),
    ('Controle Extra 16', 4, 1, 349, 'Wireless', 'Pilhas', false, false, 25),
    ('Controle Extra 17', 2, 2, 399, 'USB', 'Cabo', true, false, 30),
    ('Controle Extra 18', 3, 3, 459, 'Bluetooth', 'Bateria', false, true, 8),
    ('Controle Extra 19', 4, 4, 129, 'USB', 'Cabo', false, false, 50),
    ('Controle Extra 20', 2, 5, 189, 'USB-C', 'Bateria', false, false, 27),
    ('Controle Extra 21', 3, 1, 219, 'Wireless', 'Bateria', true, false, 33),
    ('Controle Extra 22', 4, 2, 259, 'USB', 'Cabo', false, true, 16),
    ('Controle Extra 23', 2, 3, 299, 'Bluetooth', 'Bateria', true, true, 14),
    ('Controle Extra 24', 3, 4, 349, 'USB', 'Cabo', false, false, 20),
    ('Controle Extra 25', 4, 5, 179, 'USB', 'Pilhas', false, false, 45),
    ('Controle Extra 26', 2, 1, 399, 'Wireless', 'Bateria', true, true, 9),
    ('Controle Extra 27', 3, 2, 429, 'USB-C', 'Bateria', false, false, 11),
    ('Controle Extra 28', 4, 3, 479, 'USB', 'Cabo', true, false, 7),
    ('Controle Extra 29', 2, 4, 529, 'Bluetooth', 'Bateria', false, true, 6),
    ('Controle Extra 30', 3, 5, 149, 'USB', 'Cabo', false, false, 60),
    ('Controle Extra 31', 4, 1, 199, 'Wireless', 'Pilhas', true, false, 28),
    ('Controle Extra 32', 2, 2, 239, 'USB', 'Cabo', false, false, 21),
    ('Controle Extra 33', 3, 3, 279, 'USB-C', 'Bateria', true, true, 13),
    ('Controle Extra 34', 4, 4, 319, 'Bluetooth', 'Bateria', false, false, 17),
    ('Controle Extra 35', 2, 5, 359, 'USB', 'Cabo', false, true, 19),
    ('Controle Extra 36', 3, 1, 399, 'Wireless', 'Bateria', true, false, 24),
    ('Controle Extra 37', 4, 2, 439, 'USB', 'Cabo', false, false, 15),
    ('Controle Extra 38', 2, 3, 479, 'USB-C', 'Bateria', true, true, 5),
    ('Controle Extra 39', 3, 4, 519, 'Bluetooth', 'Bateria', false, true, 4),
    ('Controle Extra 40', 4, 5, 559, 'USB', 'Cabo', false, false, 29),
    ('Controle Extra 41', 2, 1, 599, 'Wireless', 'Pilhas', true, false, 26),
    ('Controle Extra 42', 3, 2, 639, 'USB', 'Cabo', false, false, 23),
    ('Controle Extra 43', 4, 3, 679, 'USB-C', 'Bateria', true, true, 10),
    ('Controle Extra 44', 2, 4, 719, 'Bluetooth', 'Bateria', false, false, 6),
    ('Controle Extra 45', 3, 5, 759, 'USB', 'Cabo', false, true, 12),
    ('Controle Extra 46', 4, 1, 799, 'Wireless', 'Bateria', true, true, 3),
    ('Controle Extra 47', 2, 2, 839, 'USB', 'Cabo', false, false, 2),
    ('Controle Extra 48', 3, 3, 879, 'USB-C', 'Bateria', true, false, 1),
    ('Controle Extra 49', 4, 4, 919, 'Bluetooth', 'Bateria', false, true, 8),
    ('Controle Extra 50', 2, 5, 959, 'USB', 'Cabo', false, false, 20);

    insert into controle_plataforma (controle_id, plataforma_id) values
    (11, 1),
    (12, 2),
    (13, 3),
    (14, 4),
    (15, 1),
    (16, 2),
    (17, 3),
    (18, 4),
    (19, 1),
    (20, 2),
    (21, 3),
    (22, 4),
    (23, 1),
    (24, 2),
    (25, 3),
    (26, 4),
    (27, 1),
    (28, 2),
    (29, 3),
    (30, 4),
    (31, 1),
    (32, 2),
    (33, 3),
    (34, 4),
    (35, 1),
    (36, 2),
    (37, 3),
    (38, 4),
    (39, 1),
    (40, 2),
    (41, 3),
    (42, 4),
    (43, 1),
    (44, 2),
    (45, 3),
    (46, 4),
    (47, 1),
    (48, 2),
    (49, 3),
    (50, 4);

insert into controle_plataforma (controle_id, plataforma_id) values
(1, 2),
(2, 3),
(3, 1),
(3, 2),
(4, 3),
(5, 3),
(6, 3),
(7, 2),
(8, 1),
(9, 3),
(10, 3);

insert into usuario (username, senha, perfil, id_cliente) values ('BRUNO_SNO', '!Bruno1234', 1, 1);

INSERT INTO pedido (usuario_id, tipo_pagamento, endereco_id)
VALUES (1, 1, 2);

INSERT INTO pedido (usuario_id, tipo_pagamento, endereco_id)
VALUES (1, 2, 3);

INSERT INTO itempedido (quantidade, id_controle, id_pedido)
VALUES 
(2, 1, 1),
(1, 2, 1);

INSERT INTO itempedido (quantidade, id_controle, id_pedido)
VALUES 
(4, 2, 2),
(5, 3, 2);
