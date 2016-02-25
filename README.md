# sicrc-pnef
Sistema de Cadastro de Representantes

#Tutorial para configurar o projeto Sicrc-Pnef:

## Requisitos:

	- Jboss-as-7.1.1.Final;
	- Postgresql-8.4-703.jdbc4.jar ou superior;
	- JDK 1.6.0_33

## Configurando o projeto:
	- Clicar nas propriedade do projeto e selecionar o seguite na aba "project facets":
		- Dynamic Web Module Versão - 3.0;
		- Java Versão - 6.0;
		- Javascript Versão 1.0;
		- Na aba Runtime, selecionar o Jboss as 7.1.
	- Em "Java Build Path" Adicionar os JARs do projeto localizado na pasta "lib";
	- Novamente em "Java Build Path" na aba "Source", alterar o "default output folder" para "sicrc-pnef/build";
	- Em "Deployment Assembly" clicar me "Java Build Path Entries" e adicionar todas as libs existentes;
	- Novamente em Deployment Assembly", caso exista uma pasta chamada "WebContent", romova e adicione outra com o nome de "Web"
	- Informar o local do Jboss na Biblioteca correspondente;
	- Informar o Java na Biblioteca correspondente.
	- Selecionar as seguintes pastas e clicar com o botão direito do mouse e em build path, marcar essas pastas como "Use as Source Folder":
		- src;
		- src-arq;
		- src-teste;
		- resources;
		- resource-teste.
		Obs.: As pastas deverão está nessa posição, caso não esteja, clique em "Build Path" >> "Order and Export" para organizar.
