#!/usr/bin/env bash

echo "MySql Server:"
read server

echo "Database User (Should be root):"
read dbuser

echo "Database Password:"
read dbpassword

echo "Application port (fx: 8080):"
read port


echo spring.jpa.hibernate.ddl-auto=update > src\main\resources\application.properties
echo spring.datasource.url=jdbc:mysql://${server}/moviesdb?useSSL=false > src\main\resources\application.properties
echo spring.datasource.username=${dbuser} > src\main\resources\application.properties
echo spring.datasource.password=${dbpassword} > src\main\resources\application.properties
echo server.port=${port} > src\main\resources\application.properties


echo Enter Mysql password again
mysql -u ${dbuser} -p${dbpassword} < sqlinstall/createDb.sql

./mvnw install

java -jar target/movie-db-api-1.0.jar