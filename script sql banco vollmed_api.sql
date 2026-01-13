

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


SELECT * FROM usuarios;

INSERT INTO usuarios (login, senha) VALUES ('ana.souza@voll.med', '$2a$10$Y50UaMFOxteibQEYLrwuHeehHYfcoafCopUazP12.rqB91YBYAMIm');



SELECT login, senha FROM usuarios WHERE login = 'ana.souza@voll.med';

UPDATE usuarios 
SET senha = '$2a$10$/MMNatx9nnu4aoW4ZxTOZOKCRyAvQXC0Yfe.JfvjL4h1wgDXMl33C' 
WHERE login = 'ana.souza@voll.med';

