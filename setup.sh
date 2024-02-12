docker pull mysql:latest
docker run -d --name nibssnipdb -e MYSQL_ROOT_PASSWORD=nibssnipdbpassword -p 3306:3306 mysql:latest
docker logs nibssnipdb

