# Mercedes Team Racing

an web-based application to sumulation racing team. 
this application demonstate how to accieve 1ms looping in java script with web worker
(by default javascript setInterval just can achive 10ms). every 1ms car simulation will send vehicle status throught websocket.
and in other side team racing can monitor the vehicle status in realtime. for public user, can get history data from database.

This application provide 3 component
1. Car Connector
2. Team Dashboard
3. Client

#### Car Connector
this component is used to create simulation car with speed, break, and gear position.

#### Team Dashboard
this component is used to monitor race data sending from car in realtime

#### Client
this component is used to see data by public

## How to Run

### Prepare Database

### Postgres
Ensure have access to PostgreSQL database in machine you will run this software
1. Create Database with name **mercedesteam**
2. Create Schema with name **mercedesteam**
3. Edit scr/main/resources/application.properties with your db_host
   ```
   spring.datasource.url=jdbc:postgresql://{db_host}/mercedesteam
   ```
4. Edit scr/main/resources/application.properties with your db credential
   ```text
   spring.datasource.username={your_username}
   spring.datasource.password={your_password}
   ``` 
5. Edit pmm.xml and add Postgres Dependency
   ```xml
   <dependency>
        <groupId>org.postgresql</groupId>
        <artifactId>postgresql</artifactId>
        <scope>runtime</scope>
   </dependency>
   ```

### MySQL
Ensure have access to MySQL database in machine you will run this software
1. Create Database/Schema with name **mercedesteam**
2. Edit scr/main/resources/application.properties and comment all Postgres configuration section :
   ```yaml
   ## Postgres
   #spring.datasource.driver-class-name = org.postgresql.Driver
   #spring.datasource.platform=postgres
   #spring.datasource.url=jdbc:postgresql://192.168.56.102:5432/mercedesteam
   #spring.datasource.username=postgres
   #spring.datasource.password=postgres
   #spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQL10Dialect
   ```
3. Edit scr/main/resources/application.properties and uncomment all MySQL configuration section :
   ```yaml
   # MySQL
   spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL5Dialect
   spring.jpa.properties.hibernate.dialect.storage_engine=myisam
   spring.datasource.url=jdbc:mysql://${MYSQL_HOST:localhost}:3306/mercedesteam
   spring.datasource.username=admin
   spring.datasource.password=Admin0011*
   spring.datasource.driver-class-name =com.mysql.cj.jdbc.Driver
   ```
4. Edit scr/main/resources/application.properties with your db_host
   ```yaml
   spring.datasource.url=jdbc:mysql://{db_host}:3306/mercedesteam
   ```
5. Edit scr/main/resources/application.properties with your db credential
   ```yaml
   spring.datasource.username={your_username}
   spring.datasource.password={your_password}
   ``` 
6. Edit pom.xml and add MySQL Dependency
   ```xml
   <dependency>
        <groupId>mysql</groupId>
        <artifactId>mysql-connector-java</artifactId>
        <scope>runtime</scope>
   </dependency>
   ```

#### Run webapp from command line
for Windows 10 user please use PowerShell

1. goto project root folder 
2. ./mvnw clean
3. ./mvnw install
4. ./mvnw spring-boot:run
5. Open localhost:7076 in your browser

## How to use the webapp
### Home
- At webapp home, you will have 3 button. Car Connector, Team Dashboard, and Client

### Create Car Simulation
- Open car connector by click at Home or open directly in browser at localhost:7076/car.
- Create a car simulation to connector by input vehicle id ex. **lewis**. 
- Click connect, and you will see car instrument will generate value for speed, break, and gear.

### Monitor a car
- Open Team Dashboard by click Team Dashboard at Home or open directly in browser at localhost:7076/dashboard
- Connect with a car you want monitor by input vehicle id and click connect
- after connect you will see car status in realtime.
- to see all data history, you can click history button.

### Get Data from database
- Open Client Component by Click Client at Home or open directly in browser at localhost:7076/client
- to get data history you need input vehicle id.
- Click get button
- *note this data will just can access 5 minutes history, and max 2 hours after data created.


### Reference Documentation
For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/2.7.0/maven-plugin/reference/html/)
* [Create an OCI image](https://docs.spring.io/spring-boot/docs/2.7.0/maven-plugin/reference/html/#build-image)
* [Thymeleaf](https://docs.spring.io/spring-boot/docs/2.7.0/reference/htmlsingle/#boot-features-spring-mvc-template-engines)
* [Spring Boot DevTools](https://docs.spring.io/spring-boot/docs/2.7.0/reference/htmlsingle/#using-boot-devtools)
* [Spring Web](https://docs.spring.io/spring-boot/docs/2.7.0/reference/htmlsingle/#boot-features-developing-web-applications)
* [Spring Data JPA](https://docs.spring.io/spring-boot/docs/2.7.0/reference/htmlsingle/#boot-features-jpa-and-spring-data)
* [WebSocket](https://docs.spring.io/spring-boot/docs/2.7.0/reference/htmlsingle/#boot-features-websockets)

### Guides
The following guides illustrate how to use some features concretely:

* [Handling Form Submission](https://spring.io/guides/gs/handling-form-submission/)
* [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
* [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)
* [Building REST services with Spring](https://spring.io/guides/tutorials/bookmarks/)
* [Accessing Data with JPA](https://spring.io/guides/gs/accessing-data-jpa/)
* [Accessing data with MySQL](https://spring.io/guides/gs/accessing-data-mysql/)
* [Using WebSocket to build an interactive web application](https://spring.io/guides/gs/messaging-stomp-websocket/)

