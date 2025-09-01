-- This file allow to write SQL commands that will be emitted in test and dev.
-- The commands are commented as their support depends of the database
-- insert into myentity (id, field) values(1, 'field-1');
-- insert into myentity (id, field) values(2, 'field-2');
-- insert into myentity (id, field) values(3, 'field-3');
-- alter sequence myentity_seq restart with 4;

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

insert into endereco (pessoa_id, rua, numero, bairro, cidade, estado, cep)
values 
    (1, 'Rua A', '123', 'Bairro A', 'Cidade A', 'SP', '77001-234'),
    (2, 'Rua B', '456', 'Bairro B', 'Cidade B', 'RJ', '77002-345'),
    (1, 'Rua C', '789', 'Bairro C', 'Cidade C', 'MG', '77003-456');


insert into plataforma (id, nome) values (1, 'PC'), (2, 'PlayStation 5'), (3, 'Xbox Series X');

insert into controle (nome, id_fabricante, cor, preco, conexao, alimentacao, touchpad, gatilhosadaptaveis, estoque) 
values
    ('Controle DualShock 4', 2, 1, 349, 'Bluetooth', 'Bateria', true, false, 50),
    ('Controle Xbox One', 3, 2, 328, 'Wireless', 'Pilhas', false, false, 20),
    ('Controle Pro Switch', 4, 3, 1450, 'USB-C', 'Bateria', true, true, 78);

insert into controle_plataforma (controle_id, plataforma_id) values
(1, 2),
(2, 3),
(3, 1),
(3, 2);

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
