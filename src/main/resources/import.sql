-- ============================
-- PESSOA + FABRICANTE
-- ============================
insert into pessoa (email,              nome)        values ('sony@gmail.com',        'Sony');
insert into fabricante (id, cnpj)                   values (1, '07.354.235/1845-03');

insert into pessoa (email,              nome)        values ('microsoft@gmail.com',   'Microsoft');
insert into fabricante (id, cnpj)                   values (2, '07.354.384/1845-03');

insert into pessoa (email,              nome)        values ('nintendo@gmail.com',    'Nintendo');
insert into fabricante (id, cnpj)                   values (3, '07.018.235/1845-03');

insert into pessoa (email,              nome)        values ('8bitdo@gmail.com',      'Xing-Xu');
insert into fabricante (id, cnpj)                   values (4, '82.018.105/2045-03');

insert into pessoa (email,              nome)        values ('labubu@gmail.com',      'La-Bu-Bu');
insert into fabricante (id, cnpj)                   values (5, '82.245.105/2045-03');


-- ============================
-- TELEFONE
-- ============================
insert into telefone (numero)
values 
    ('(63) 99999-9999'),
    ('(63) 98888-8888'),
    ('(63) 97777-7777');


-- ============================
-- ENDEREÇO
-- ============================
insert into endereco (pessoa_id, rua,     numero, bairro,     cidade,     estado, cep)
values
    (1, 'Rua A', '123', 'Bairro A', 'Cidade A', 'SP', '77001-234'),
    (2, 'Rua B', '456', 'Bairro B', 'Cidade B', 'RJ', '77002-345'),
    (1, 'Rua C', '789', 'Bairro C', 'Cidade C', 'MG', '77003-456');


-- ============================
-- PLATAFORMA
-- ============================
insert into plataforma (nome)
values 
    ('PC'),
    ('PlayStation'),
    ('Xbox'),
    ('Nintendo'),
    ('8BitDo');


-- ============================
-- LOTE
-- ============================
insert into lote (quantidade, descricao)
values
    (100, 'Estoque Central'),
    (50,  'Estoque Secundário'),
    (200, 'Estoque Terciário'),
    (75,  'Estoque Quaternário'),
    (150, 'Estoque Quinário'),
    (80,  'Estoque Extra'),
    (60,  'Estoque Reserva'),
    (90,  'Estoque Adicional');


-- ============================
-- CONTROLE (dados alinhados)
-- ============================
insert into controle 
(nome,                               id_fabricante, cor, preco, conexao,   alimentacao, touchpad, gatilhosadaptaveis, estoque)
values
    ('DualSense PS5',                         1, 1,  449, 'Bluetooth', 'Bateria', true,  true,  40),
    ('DualShock 4',                           1, 2,  299, 'Bluetooth', 'Bateria', true,  false, 50),
    ('DualSense Edge',                        1, 3, 1299, 'Bluetooth', 'Bateria', true,  true,  15),
    ('Xbox Wireless Controller Series X',     2, 1,  359, 'Wireless',  'Pilhas',  false, false, 60),
    ('Xbox Elite Series 2',                   2, 4,  999, 'Wireless',  'Bateria', false, true,  20),
    ('Nintendo Switch Pro Controller',        3, 3,  469, 'Wireless',  'Bateria', false, false, 30),
    ('Joy-Con (Par) Neon',                    3, 2,  399, 'Bluetooth', 'Bateria', false, false, 70),
    ('Razer Wolverine V2',                    4, 5,  749, 'USB',       'Cabo',    false, false, 25),
    ('Razer Wolverine V2 Chroma',             4, 3,  999, 'USB',       'Cabo',    false, false, 18),
    ('Razer Raiju Tournament',                4, 1,  899, 'Bluetooth', 'Bateria', true,  false, 12),
    ('8BitDo Pro 2',                          5, 2,  349, 'Bluetooth', 'Bateria', false, false, 45),
    ('8BitDo Ultimate Bluetooth',             5, 3,  499, 'Bluetooth', 'Bateria', false, true,  22),
    ('8BitDo SN30 Pro+',                      5, 1,  319, 'Bluetooth', 'Bateria', false, false, 50),
    ('Nacon Revolution Unlimited',            1, 4,  699, 'Bluetooth', 'Bateria', true,  false, 20),
    ('Nacon Asymmetric Wireless',             1, 2,  399, 'Wireless',  'Bateria', false, false, 30),
    ('PDP Afterglow Wireless Deluxe',         2, 5,  249, 'Wireless',  'Bateria', false, false, 70),
    ('PDP Rematch Glow',                      2, 3,  199, 'USB',       'Cabo',    false, false, 40),
    ('Hori Fighting Commander OCTA',          3, 1,  399, 'USB',       'Cabo',    false, false, 35),
    ('Hori Split Pad Pro',                    3, 5,  349, 'Wireless',  'Bateria', false, false, 48),
    ('PowerA Enhanced Wired',                 2, 2,  199, 'USB',       'Cabo',    false, false, 50),
    ('PowerA Fusion Pro 3',                   2, 4,  699, 'Wireless',  'Bateria', false, true,  18),
    ('Scuf Impact',                           1, 1, 1299, 'Wireless',  'Bateria', true,  true,  12),
    ('Scuf Reflex Pro',                       1, 3, 1599, 'Bluetooth', 'Bateria', true,  true,  10),
    ('Scuf Instinct Pro',                     2, 4, 1299, 'Wireless',  'Bateria', false, true,  14),
    ('Astro C40 TR',                          1, 2, 1499, 'USB',       'Cabo',    true,  true,  8),
    ('Turtle Beach Recon Controller',         2, 1,  299, 'USB',       'Cabo',    false, false, 40),
    ('Turtle Beach Recon Cloud',              2, 3,  499, 'Wireless',  'Bateria', false, false, 22),
    ('Logitech F310',                         2, 5,  139, 'USB',       'Cabo',    false, false, 80),
    ('Logitech F710',                         2, 4,  399, 'Wireless',  'Pilhas',  false, false, 25),
    ('Razer Kishi V2',                        4, 1,  599, 'USB-C',     'Cabo',    false, false, 30),
    ('Razer Kishi V2 Pro',                    4, 4,  899, 'USB-C',     'Cabo',    false, false, 15),
    ('GameSir T4 Kaleid',                     5, 3,  379, 'USB-C',     'Cabo',    false, false, 33),
    ('GameSir T4 Mini',                       5, 2,  249, 'Wireless',  'Bateria', false, false, 50),
    ('GameSir G7 SE',                         5, 1,  399, 'USB-C',     'Cabo',    false, true,  20),
    ('8BitDo M30 Bluetooth',                  5, 4,  229, 'Bluetooth', 'Bateria', false, false, 40),
    ('8BitDo Arcade Stick',                   5, 3,  599, 'Bluetooth', 'Bateria', false, false, 12),
    ('Hori Fighting Stick Alpha',             3, 1,  899, 'USB',       'Cabo',    false, false, 10),
    ('PowerA Spectra Infinity',               2, 5,  219, 'USB',       'Cabo',    false, false, 60),
    ('PDP Victrix Pro BFG',                   2, 4, 1499, 'Wireless',  'Bateria', false, true,  9),
    ('PDP Afterglow Wave',                    2, 3,  279, 'USB',       'Cabo',    false, false, 55),
    ('Nacon Pro Compact',                     1, 1,  259, 'USB',       'Cabo',    false, false, 35),
    ('Nacon Revolution 5 Pro',                1, 4, 1199, 'Bluetooth', 'Bateria', true,  true,  14),
    ('Brook Wireless Fighting Board',         5, 2,  499, 'Bluetooth', 'Bateria', false, false, 20),
    ('Razer Junglecat',                       4, 5,  799, 'Bluetooth', 'Bateria', false, false, 8),
    ('8BitDo Zero 2',                         5, 3,  149, 'Bluetooth', 'Bateria', false, false, 60),
    ('8BitDo Lite SE',                        5, 1,  229, 'Bluetooth', 'Bateria', false, false, 45),
    ('PDP Rock Candy',                        2, 2,  129, 'USB',       'Cabo',    false, false, 70),
    ('Turtle Beach React-R',                  2, 3,  249, 'USB',       'Cabo',    false, false, 50),
    ('Hori Horipad Switch',                   3, 1,  199, 'Wireless',  'Bateria', false, false, 40),
    ('Tokoku Ardeno',                         2, 5,  502, 'Wireless',  'Bateria', false, false, 40);


-- ============================
-- CONTROLE_PLATAFORMA
-- ============================
insert into controle_plataforma (controle_id, plataforma_id) values
    (11, 1), (12, 2), (13, 3), (14, 4), (15, 1),
    (16, 2), (17, 3), (18, 4), (19, 1), (20, 2),
    (21, 3), (22, 4), (23, 1), (24, 2), (25, 3),
    (26, 4), (27, 1), (28, 2), (29, 3), (30, 4),
    (31, 1), (32, 2), (33, 3), (34, 4), (35, 1),
    (36, 2), (37, 3), (38, 4), (39, 1), (40, 2),
    (41, 3), (42, 4), (43, 1), (44, 2), (45, 3),
    (46, 4), (47, 1), (48, 2), (49, 3), (50, 4);

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
