-- This file allow to write SQL commands that will be emitted in test and dev.
-- The commands are commented as their support depends of the database
-- insert into myentity (id, field) values(1, 'field-1');
-- insert into myentity (id, field) values(2, 'field-2');
-- insert into myentity (id, field) values(3, 'field-3');
-- alter sequence myentity_seq restart with 4;

insert into pessoa(email, nome) values ('bruno@gmail.com', 'Bruno');
insert into pessoafisica(id, cpf) values (1, '078.603.371.18');
insert into cliente (id) values (1);

insert into pessoa(email, nome) values ('sony@gmail.com', 'Sony');
insert into pessoajuridica(id, cnpj) values (2, '07.354.235/1845-03');
insert into fabricante (id) values (2);

insert into endereco (rua, numero, bairro, cidade, estado, cep)
values 
    ('Rua A', '123', 'Bairro A', 'Cidade A', 'SP', '77001-234'),
    ('Rua B', '456', 'Bairro B', 'Cidade B', 'RJ', '77002-345'),
    ('Rua C', '789', 'Bairro C', 'Cidade C', 'MG', '77003-456');

insert into controle (nome, id_fabricante, cor, preco, conexao, alimentacao, touchpad, gatilhosadaptaveis) 
values
    ('Controle DualShock 4', 2, 1, 349, 'Bluetooth', 'Bateria', true, false),
    ('Controle Xbox One', 2, 2, 328, 'Wireless', 'Pilhas', false, false),
    ('Controle Pro Switch', 2, 3, 1450, 'USB-C', 'Bateria', true, true);

insert into usuario(username, senha, perfil, id_cliente) values ('BRUNO_SNO', '!Bruno1234', 1, 1);