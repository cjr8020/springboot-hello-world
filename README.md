# springboot-hello-world

Sample Spring Boot based REST service supporting deployment and infrastructure services testing. 

1.x.x - supports SpringBoot 1.5.x built on Spring Security 4<br/>
2.x.x - supports SpringBoot 2.x.x built on Spring Security 5<br/>

**NOTE**: if you update your application to SB 2.x, you will need  to update `sfg-security-library` to version 2.x.x.

The service demonstrates the following aspects:

1. JWT-based authentication for application endpoints
2. Basic Authentication actuator endpoints
3. `auditable` log entry generation
4. Encryption of passwords using jasypt
4. separate management port (8081)
5. management endpoint with health checks and metrics
6. database backend with an exposed health check
7. TLS 

## `sfg-security-springboot` library

`sfg-security-springboot` library provides support for the first 3 items above.
See detailed description at
https://github.standard.com/SharedComponents/sfg-security-springboot
 
## database service

The database service is implemented using embedded H2 database such that you can run this sample application without the need of an operational database like Oracle. The sample application creates the table and loads the data at start-up.  

Future Enhancements:

* Connection to MQ

## Externalizing application properties

Examples of application properties that need to be externalized are credentials, resource URLs, that are different across 
environments.

For *server-based* deployment, these properties are contained in ${application.name}.yml file deployed
side-by-side with the application executable and startup script, and is populated from 
`configuration-template.yml` file located in the DAR module.  The DEV team is responsible
for providing content for the `configuration-template.yml` file.

For *local* testing (when applicable), `application.yml` containing externalized properties is placed in the `/config` subdirectory of the root project folder.  Since this location is outside the 
Maven modules, it will be excluded from being packaged into the executable JAR.

#### property loading and merging

Property loading and merging is based on the default Spring configuration.
It is important to ensure that the `external` and `internal` property files do not have any
overlapping properties. 

reference: [springboot application property files](https://docs.spring.io/spring-boot/docs/current/reference/html/boot-features-external-config.html)

`SpringApplication` will load properties from the following locations:

```
1. A /config subdirectory of the current directory.
2. The current directory
3. A classpath /config package
4. The classpath root
```


## Steps to enable OAuth2 resource server protection

Or, in other words, how to activate JWT-based authorization for RESTful appliation endpoints.

#### Step 1: include `sfg-security-springboot` library

add the following property to your POM

```
  <properties>
    ...
    <sfg-security-springboot.version>1.0.0-SNAPSHOT</sfg-security-springboot.version>
    ...
  </properties>
```

and the dependency

```
    <dependency>
      <groupId>com.standard.sfg.security</groupId>
      <artifactId>sfg-security-springboot</artifactId>
      <version>${sfg-security-springboot.version}</version>
    </dependency>
```

#### Step 2: import `SfgSecurityConfiguration` and `JwtAuthenticationContextFactory`

```
@SpringBootApplication
@EnableEncryptableProperties
@Import({
    SfgSecurityConfiguration.class,
    SfgJwtFactory.class,
    SfgAuditableFactory.class
})
public class SpringbootHelloWorldApplication {

  private static final Logger log = LoggerFactory.getLogger(SpringbootHelloWorldApplication.class);

  public static void main(String[] args) {
    log.info("SpringbootHelloWorldApplication::::Started Application");
    SpringApplication.run(SpringbootHelloWorldApplication.class, args);
  }
 
}
```

#### Step 3: add OAuth2 and JWT properties to your application.yml

For symmetric signingKey:

```
# ----------------------------------
# OAuth2/JWT properties
# ----------------------------------
oauth:
  signingKey: <must-match-token-server-signing-key>
```



## to run this application locally

build

```
$ mvn clean package
```

execute

```
java -jar springboot-hello-world\target\springboot-hello-world-1.0.2-SNAPSHOT.jar
```

Given Spring's property loading order, the application will be configured using 
`application.yml` contained in `${PROJECT_ROOT}/config` directory.


## Operations/Resources

When running `springboot-hello-world` application locally, you can interact with it using 
Postman project which can be found in `springboot-hello-world` module in
`src/test/resources/springboot-hello-world.postman_collection.json`


There are 3 operations exposed by this service:

* hello
* helloUpper
* listActors 

All resources exposed via application endpoint require Basic Authentication.
For development purposes, the credentials are:

```
User ID:  apiuser
Password: apipassword
```

If authentication is **unsuccessful**, the service will respond as follows:

```
HTTP/1.1 401 
X-Content-Type-Options: nosniff
X-XSS-Protection: 1; mode=block
Cache-Control: no-cache, no-store, max-age=0, must-revalidate
Pragma: no-cache
Expires: 0
X-Frame-Options: DENY
WWW-Authenticate: Basic realm=DeveloperStack
Content-Length: 35
Date: Wed, 12 Jul 2017 15:34:32 GMT

HTTP Status 401 - Bad credentials
```
--OR--
```
HTTP Status 401 - Full authentication is required to access this resource
```



### hello resource

Resource URI:  (Host, port, context TBD.)  For development purposes (locally), it is:

```
http://localhost:8080/hello

```

The service will respond with `Hello there!` 

sample response

```
HTTP/1.1 200 
X-Content-Type-Options: nosniff
X-XSS-Protection: 1; mode=block
Cache-Control: no-cache, no-store, max-age=0, must-revalidate
Pragma: no-cache
Expires: 0
X-Frame-Options: DENY
X-Application-Context: application:8080
Content-Type: application/json;charset=UTF-8
Transfer-Encoding: chunked
Date: Wed, 12 Jul 2017 15:21:01 GMT

{"hello":"Hello there!"}
```

### helloUpper resource

resource URI:

```
http://localhost:8080/helloUpper
```

The service will respond with `HELLO THERE`.

sample response

```
HTTP/1.1 200 
X-Content-Type-Options: nosniff
X-XSS-Protection: 1; mode=block
Cache-Control: no-cache, no-store, max-age=0, must-revalidate
Pragma: no-cache
Expires: 0
X-Frame-Options: DENY
X-Application-Context: application:8080
Content-Type: application/json;charset=UTF-8
Transfer-Encoding: chunked
Date: Wed, 12 Jul 2017 15:26:56 GMT

{"hello":"HELLO THERE!"}
```

### listActors resource

resource URI:
```
http://localhost:8080/listActors
```

If authentication is **successful**, the service will respond by listing the actors.

sample response

```
HTTP/1.1 200 
X-Content-Type-Options: nosniff
X-XSS-Protection: 1; mode=block
Cache-Control: no-cache, no-store, max-age=0, must-revalidate
Pragma: no-cache
Expires: 0
X-Frame-Options: DENY
Set-Cookie: JSESSIONID=C1625094E708595721F63FFBB149429F; Path=/; HttpOnly
X-Application-Context: application:8080
Content-Type: application/json;charset=UTF-8
Transfer-Encoding: chunked
Date: Thu, 17 Aug 2017 16:20:31 GMT

[ {
  "id" : 1,
  "username" : "denselw",
  "email" : "denselwashington@gmail.com"
}, {
  "id" : 2,
  "username" : "nicolek",
  "email" : "nicolekidman@demo.com"
} ]
```




## Run instruction (server-based environment)

To run this sample application - 
1. Create a system environment variable
	```
	Variable name: springhello_master_password
	Variable value: masterhelloworldpassword
	```
2. Create the log directory path: ```/apps/logs/SIC/springboot-hello-world/pdxlmicni001/```
3. Do maven install:  ```mvn clean install```
4. Before you run the application, copy ```/src/main/resources/application.yml`` to the path where you are executing the run command (example: c:/springboot-hello-world/springboot-hello-world/application.yml).
5. Edit the value of jasypt.encryptor.password in the application.yml that you just copied.
	
	From:  ```jasypt.encryptor.password: masterhelloworldpassword```
	
	To:    ```jasypt.encryptor.password: ${springhello_master_password}```
	
5. Run ```c:/springboot-hello-world/springboot-hello-world/java -Xmx1024M -jar target\springboot-hello-world-1.0.0-SNAPSHOT.jar```
6. To test: Open the browser and type ```http://localhost:8080/hello``` or ```http://localhost:8080/helloUpper``` or ```http://localhost:8081/actuator/health```

## Password Encryption

Password encryption is implemented using jasypt string encryptor along with PropertySourcesPlaceholderConfigure. See CommonConfig.java in how the configuration is coded.  Along with the configuration, you will need to add these annotations in your starting application.

	@SpringBootApplication
	@EnableEncryptableProperties

Jasypt dependency -

		<dependency>
			<groupId>com.github.ulisesbocchio</groupId>
			<artifactId>jasypt-spring-boot-starter</artifactId>
			<version>1.11</version>
		</dependency>

## Database Configuration

Since this is just a sample app, database is configured to use an embedded database (i.e. H2). See DatasourceConfiguration.java for how this is configured and also the required properties in application.yml.  This sample configuration should help you setup an embedded database for unit testing.

		<dependency>
		    <groupId>com.h2database</groupId>
		    <artifactId>h2</artifactId>
		    <scope>runtime</scope>
		</dependency>
 
For Oracle database configuration, application.yml has the commented configuration properties. The DatasourceConfiguration.java also has the commented lines. You will need to comment the use of h2 database. Uncomment this dependency in the pom.xml.

		<dependency>
		    <groupId>com.oracle</groupId>
		    <artifactId>ojdbc7</artifactId>
		    <scope>12.1.0.2</scope>
		</dependency>


## Service logging

The service writes its application to a shared filesystem available to Windows clients at

```
For local development:  sampleSpringBoot-service.log

After deployment to server: 
TBD, possibly:  /apps/logs/sampleSpringBoot/pdxlmicni001/sampleSpringBoot-service.log
```
## Management endpoints

#### info

```
http://localhost:8081/actuator/info
```

response:

```
{
  "app": {
    "encoding": "UTF-8",
    "version": "1.0.2-SNAPSHOT",
    "build": {
      "timestamp": "2018-02-02 22:30 UTC"
    },
    "java": {
      "source": "1.8.0_45",
       "target": "1.8.0_45"
    }
  }
}
```

#### health

```
http://localhost:8081/actuator/health
```

response:

```
{
  "status" : "UP",
  "diskSpace" : {
    "status" : "UP",
    "total" : 999887466496,
    "free" : 795960606720,
    "threshold" : 10485760
  },
  "db" : {
    "status" : "UP",
    "database" : "H2",
    "hello" : 1
  }
}```


