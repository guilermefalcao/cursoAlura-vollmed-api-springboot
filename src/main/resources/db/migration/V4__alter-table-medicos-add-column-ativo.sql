

alter TABLE medicos add ativo tinyint(1) DEFAULT 1 NOT NULL;

/*
1 = ativo 
0 = inativo
Todos os médicos existentes ficam ativos por padrão
*/



/*as migrations sao imutaveis, quando executadas no banco nao sao executadas novamente*
    por isso criamos uma nova migration para adicionar a coluna ativo */
