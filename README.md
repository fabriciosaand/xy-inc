![xy-inc](https://user-images.githubusercontent.com/219049/27510449-f574ffe0-58e6-11e7-8a5f-67773faa26f2.png)

xy-inc
======

Plataforma da empresa XY Inc, baseada em serviços, como o objetivo de flexibilizar a integração de receptores GPS (Global Positioning System). A plataforma fornece toda a inteligência ao dispositivo inovador que promete auxiliar pessoas na localização de ponto de interesse (POIs).

Serviços
--------
- **Serviço para cadastrar pontos de interesse com 3 atributos:** Nome do POI, Coordenada X (inteiro não negativo) Coordenada Y (inteiro não negativo). Os POIs devem ser armazenados em uma base de dados.
- **Serviço para listar todos os POIs cadastrados.**
- **Serviço para listar POIs por proximidade.** Este serviço receberá uma coordenada X e uma coordenada Y, especificando um ponto de referência, em como uma distância máxima (dmax) em metros. O serviço deverá retornar todos os POIs da base de dados que estejam a uma distância menor ou igual a d-max a partir do ponto de referência. Exemplo:

  ### Base de Dados: ###
    - 'Lanchonete' (x=27, y=12)
    - 'Posto' (x=31, y=18)
    - 'Joalheria' (x=15, y=12)
    - 'Floricultura' (x=19, y=21)
    - 'Pub' (x=12, y=8)
    - 'Supermercado' (x=23, y=6)
    - 'Churrascaria' (x=28, y=2)
  
  Dado o ponto de referência (x=20, y=10) indicado pelo receptor GPS, e uma distância máxima de 10 metros, o serviço deve retornar os seguintes POIs:
    - Lanchonete
    - Joalheria
    - Pub
    - Supermercado
    
 Pré-requisitos
 --------------
 
 - [Java 1.8](https://www.java.com/pt_BR/download/)
 - [Apache Maven 3.5.0](https://maven.apache.org/download.cgi)
 - [Apache Tomcat 9](https://tomcat.apache.org/download-90.cgi)
 - [MySQL 5.7.18](https://dev.mysql.com/downloads/installer/)
 
 Configurações
 -------------
 
 - [Configurar a variável de ambiente JAVA_HOME](https://github.com/fabriciosaand/xy-inc/wiki/Configura%C3%A7%C3%B5es#configVarJavaHome)
 - [Configurar a variável de ambiente MAVEN_HOME](https://github.com/fabriciosaand/xy-inc/wiki/Configura%C3%A7%C3%B5es#configVarMavenHome)
 - [Configurar o Tomcat](https://github.com/fabriciosaand/xy-inc/wiki/Configura%C3%A7%C3%B5es#configTomcat)
 - [Configurar o Maven](https://github.com/fabriciosaand/xy-inc/wiki/Configura%C3%A7%C3%B5es#configMaven)
 - [Configurar o MySql](https://github.com/fabriciosaand/xy-inc/wiki/Configura%C3%A7%C3%B5es#configMySql)
    
 Instruções para execução
 ------------------------
 - Baixe o repositório como arquivo zip ou faça um clone;
 - Descompacte os arquivos em seu computador;
 - Navega até a pasta do projeto 
 - Execute o comando ```mvn tomcat7:run-war```
 
 Testando a aplicação
 -------------------
 Acesse os links abaixo para testar a aplicação.
 
 - [Serviço para cadastrar pontos de interesse](http://localhost:8080/xy-inc/services/poi/cadastrar?nome=Lanchonete&coordenadaX=27&coordenadaY=12)
```
(http://localhost:8080/xy-inc/services/poi/cadastrar?nome=Lanchonete&coordenadaX=27&coordenadaY=12)
```
 - [Serviço para listar todos os POIs cadastrados](http://localhost:8080/xy-inc/services/poi/listarTodos)
 ```
 (http://localhost:8080/xy-inc/services/poi/listarTodos)
 ```
 - [Serviço para listar POIs por proximidade](http://localhost:8080/xy-inc/services/poi/listarPorProximidade?coordenadaX=20&coordenadaY=10&dMax=10)
 ```
 (http://localhost:8080/xy-inc/services/poi/listarPorProximidade?coordenadaX=20&coordenadaY=10&dMax=10)
 ```
