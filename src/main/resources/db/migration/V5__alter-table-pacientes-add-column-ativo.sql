alter table pacientes add column ativo tinyint;
update pacientes set ativo = 1;
alter table pacientes modify ativo tinyint not null;

/*
Adiciona coluna ativo na tabela pacientes
1 = ativo, 0 = inativo
Todos os pacientes existentes ficam ativos por padrão
*/