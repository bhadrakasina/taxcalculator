# taxcalculator
Imag employee tax calculator

The spring boot application is created with below resources and details
Java:8
Springboot : 2.7.5
build tool : maven
Data Base : MYSQL
Please create the schema in the Mysql data base with the below name to connect to the Spring data JPA.
Schema Name= dir
spring.datasource.url=jdbc:mysql://localhost:3306/dir
spring.datasource.username=root
spring.datasource.password=1310
Please replace the SQL username and password in the application.properties file with your system credentials.
spring.datasource.username= ***
spring.datasource.password= ***

Spring secuirty is also implemented with inmemory authentication so plese user Basic authentication while acessing API

username : admin
password : password


