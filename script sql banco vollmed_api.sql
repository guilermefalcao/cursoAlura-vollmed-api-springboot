

-- Criar o novo banco
CREATE DATABASE vollmed_api;

-- Copiar todas as tabelas (se houver)
-- Como é um banco novo, provavelmente está vazio, então só precisa:

-- Deletar o banco antigo
DROP DATABASE `vollmed.api`;

show tables;

select * from flyway_schema_history;
DELETE FROM flyway_schema_history WHERE version = '3';

desc medicos;

select * from medicos;

DESCRIBE medicos;



select * from pacientes;

DESCRIBE pacientes;
