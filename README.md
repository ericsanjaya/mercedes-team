# Mercedes Team Racing v 1.0

Web-based application racing team simulation. 
this application demonstrate how to achieve **1ms** looping in JavaScript with web worker
(by default JavaScript setInterval just can achieve 10ms). every **1ms** car simulation will send vehicle status through websocket.

And in other side Team Racing can monitor the vehicle status in realtime (with 10ms refresh rate).

For public User/ Client, can get the last 5 minutes history data from database.

This web application can work for multi *car simulator* & *multi dashboard*

# 

This application consist of 3 component
1. Car Connector
2. Team Dashboard
3. Client

#### Car Connector
Components used to create a simulated car with random Speed, Brake, and Gear positions

#### Team Dashboard
Components used to Monitor race data sending from the car in realtime

#### Client
Components used to view data by Public/ Client

## How to Run

### Prepare Database

### PostgreSQL
Make sure the machine you want to use to run this web application has access to the PostgreSQL database
1. Create a Database with the name **mercedesteam**
2. Create a Schema with the name **mercedesteam**
3. Edit file scr/main/resources/application.properties with your Database Host
   ```
   spring.datasource.url=jdbc:postgresql://{db_host}/mercedesteam
   ```
4. Edit file scr/main/resources/application.properties with your Database credential
   ```text
   spring.datasource.username={your_username}
   spring.datasource.password={your_password}
   ``` 
5. Edit file pom.xml and add PostgreSQL Dependency
   ```xml
   <dependency>
        <groupId>org.postgresql</groupId>
        <artifactId>postgresql</artifactId>
        <scope>runtime</scope>
   </dependency>
   ```

### MySQL
Make sure the machine you want to use to run this web application has access to the MySQL database
1. Create a Database/Schema with the name **mercedesteam**
2. Edit file scr/main/resources/application.properties and comment all PostgreSQL configuration section :
   ```yaml
   ## Postgres
   #spring.datasource.driver-class-name = org.postgresql.Driver
   #spring.datasource.platform=postgres
   #spring.datasource.url=jdbc:postgresql://192.168.56.102:5432/mercedesteam
   #spring.datasource.username=postgres
   #spring.datasource.password=postgres
   #spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQL10Dialect
   ```
3. Edit file scr/main/resources/application.properties and uncomment all MySQL configuration section :
   ```yaml
   # MySQL
   spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL5Dialect
   spring.jpa.properties.hibernate.dialect.storage_engine=myisam
   spring.datasource.url=jdbc:mysql://${MYSQL_HOST:localhost}:3306/mercedesteam
   spring.datasource.username=admin
   spring.datasource.password=Admin0011*
   spring.datasource.driver-class-name =com.mysql.cj.jdbc.Driver
   ```
4. Edit file scr/main/resources/application.properties with your Database Host
   ```yaml
   spring.datasource.url=jdbc:mysql://{db_host}:3306/mercedesteam
   ```
5. Edit file scr/main/resources/application.properties with your Database credential
   ```yaml
   spring.datasource.username={your_username}
   spring.datasource.password={your_password}
   ``` 
6. Edit file pom.xml and add MySQL Dependency
   ```xml
   <dependency>
        <groupId>mysql</groupId>
        <artifactId>mysql-connector-java</artifactId>
        <scope>runtime</scope>
   </dependency>
   ```

### Run web-app from command line
*For Windows 10 user please use **PowerShell***

1. Goto project root folder
   ```text
   cd {your_directory}/mercedes-team
   ```
2. Clean java build directory
   ```text
   ./mvnw clean
   ```
3. Install & download all dependency
   ```text
   ./mvnw install
   ```
4. Run the web application
   ```text
   ./mvnw spring-boot:run
   ```
5. Open ***localhost:7076*** in your browser

## How to use the web-app
### Home
- You will have 3 button. *Car Connector*, *Team Dashboard*, and *Client*

### Create Car Simulation
- Open car connector by clicking Car Connector on Homepage or open directly in browser at ***localhost:7076/car***
- Make a car simulation to the connector by entering the vehicle id ex. **lewis**
- Click connect, and you will see the car instrument will generate value for speed, brake, and gear

### Monitor a car
- Open Team Dashboard by clicking Team Dashboard on Homepage or open directly in browser at ***localhost:7076/dashboard***
- Connect with the car you want to monitor by entering the vehicle id and click connect
- Once connected you will see the car status in realtime
- To view all data history, you can click history button

### Get Data from database
- Open Client Component by Click Client on Homepage or open directly in browser at ***localhost:7076/client***
- To get the data history you need to enter the vehicle id
- Click get button
- *note this will only be able to access the last 5 minutes history data, and max 2 hours after the data is created


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

