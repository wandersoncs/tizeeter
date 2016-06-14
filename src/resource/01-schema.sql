CREATE TABLE `usuario` (
  `id` mediumint(8) unsigned NOT NULL auto_increment,
    `login` varchar(255) NOT NULL UNIQUE,
  `email` varchar(255) NOT NULL UNIQUE,
    `senha` TEXT default NULL,
  `nome` varchar(255) default NULL,
    `endereco` varchar(255) default NULL,
  `data_de_nascimento` varchar(255),
    `sexo` TEXT default NULL,
  `novidades` mediumint default NULL,
    PRIMARY KEY (`id`)
) AUTO_INCREMENT=1;
