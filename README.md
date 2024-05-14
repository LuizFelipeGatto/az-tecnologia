# AZ Tecnologia

Projeto de gerenciamento de leilões com as seguintes tecnologias

  - Java
  - SpringBoot
  - Flyway
  - PostgreSQL
  - Vue.js

# Informações importantes

  - Backend

    Abra o backend em alguma IDE com suporte para Java, abra o aquivo pom.xml e coloque para baixar as dependências que contém nele.
    Após isso, crie um banco de dados postgreSQL e abra o arquivo ``application.properties`` e configure para o ``nome do banco de dados`` que criou, seu ``nome de usuário`` e ``senha`` do banco de dados. 
    
         spring.datasource.url = jdbc:postgresql://localhost:5432/az_tecnologia
         spring.datasource.username = postgres
         spring.datasource.password = postgres
    
    O flyway criará automaticamente o schema no banco de dados as tabelas e também os inserts automático da massa de dados solicitada. Feito isso, o backend estará configurado.

  - Frontend

  Abra o front em algum editor de texto, abra o terminal e coloque para baixar as dependências com o comando ``npm-install``.
  Após isso, utilize ``npm run dev`` para rodar o frontend. 

  OBS: Versão do node utilizada - v16.17.1
