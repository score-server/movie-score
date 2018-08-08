@Echo off

set /p server=MySql Server:
set /p dbuser=Database User (Should be root):
set /p dbpassword=Database Password:
set /p port=Application port (fx: 8080):

(
echo spring.jpa.hibernate.ddl-auto=update
echo spring.datasource.url=jdbc:mysql://%server%/moviesdb?useSSL=false
echo spring.datasource.username=%dbuser%
echo spring.datasource.password=%dbpassword%
echo server.port=%port%
) > src\main\resources\application.properties

C:\Program Files\MySQL\MySQL Server 8.0\bin\mysql.exe -u %dbuser% -p %dbpassword% < sqlinstall/createDb.sql

mvnw.cmd clean
mvnw.cmd install

start java -jar target/movie-db-api-1.0.jar

timeout 15 >nul

C:\Program Files\MySQL\MySQL Server 8.0\bin\mysql.exe -u %dbuser% -p %dbpassword% < sqlinstall/addUser.sql
