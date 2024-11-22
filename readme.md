to start the app:

1. mvn clean install
2. mvn spring-boot:run or java -jar target/demo-0.0.1-SNAPSHOT.jar

to access the app:
1. http://localhost:8080/operator for operator page
2. http://localhost:8080/ambulance?ambulanceId=1 for ambulance 1 page
3. http://localhost:8080/ambulance for ambulance page
4. http://localhost:8080/hospital?hospitalId=1 for hospital 1 page

to access mysql:
1. mysql -u root