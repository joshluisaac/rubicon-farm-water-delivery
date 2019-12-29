[![Build Status](https://travis-ci.org/joshluisaac/FarmWaterDelivery.svg?branch=master)](https://travis-ci.org/joshluisaac/FarmWaterDelivery)
[![Coverage Status](https://coveralls.io/repos/github/joshluisaac/FarmWaterDelivery/badge.svg?branch=master)](https://coveralls.io/github/joshluisaac/FarmWaterDelivery?branch=master)



# Farm Water Delivery - Water Ordering API

This application is a water ordering API which provides a set of endpoints
covering the specification of the requirements described [here](RubiconCodingChallenge.pdf).

The application features a couple of endpoints for ordering water, querying existing orders and cancelling an order. These operations has side effects on the data structure of an in-memory [PersistenceMechanism](src/main/java/com/rubiconwater/codingchallenge/joshluisaac/infrastructure/PersistenceMechanism.java) which internally uses a `Map<K,​V>` data structure while keeping track of these changes.
These changes are also flushed/written to disk to prevent lost updates on next application restart and to keep both the in-memory cache and dataset in a eventually consistent state.
The dataset path is located here [here](data/DeliveryOrderDataSet.json). On startup, the application gets preloaded and initialized with a set of delivery orders from the same JSON dataset.


## Package structure

The project source code is partitioned into 3 packages as follows:

1. **businessactivities**: This package contains all business domain and API related source files
2. **infrastructure**
3. **sharedkernel**:

![alt text][codeCoverage_Ide2]
Figure 1: Project package structure

## Prerequisites

- Adopt/Open/Oracle JDK 11+ or higher (One of Adopt, Open or Oracle JDK)
- Apache Maven v3.6.1 or Gradle v6.0.1 (SDKMAN makes it very easy to set up these build tools).

You can follow the steps here on how to setup [SDKMAN](https://sdkman.io/install) and to install gradle using SDKMAN refer to this [link](https://gradle.org/install/)

## Gradle and gradle wrapper

The application comes bundled with Gradle wrapper which makes it easy to compile, test, build and run the application without having to worry about downloading Gradle. The gradle wrapper scripts [*nix](gradlew) or on [Windows](gradlew.bat) will take care of this.

## Framework stack

The following libraries and dependencies were used to develop this project

1. **Hibernate validator**: For validating HTTP request body and cascading validation constraints.
1. **Lombok** : Used to auto generate getters, setters, constructors and builders for entity and value objects.
1. **FasterXML Jackson**: For deserialization of request body and serialization of response.
1. **Google Guava**:

## Maven/Gradle - Running the test suite

Running this command will compile as well as run all tests

```bash
mvn compile test
```

or using Gradle

```bash
./gradlew test
```

Executing this command using maven will yield the following console output


![alt text][testSummary]
Figure 2: Gradle test summary

```log
[INFO] Tests run: 13, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 1.284 s - in com.rubiconwater.codingchallenge.joshluisaac.businessactivities.deliverymanagement.api.WaterDeliveryApiControllerTest
[INFO] Running com.rubiconwater.codingchallenge.joshluisaac.businessactivities.deliverymanagement.api.CancelOrderRequestTest
[INFO] Tests run: 2, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.002 s - in com.rubiconwater.codingchallenge.joshluisaac.businessactivities.deliverymanagement.api.CancelOrderRequestTest
2019-12-27 23:49:30,099 INFO  Shutting down ExecutorService 'applicationTaskExecutor'
2019-12-27 23:49:30,099 INFO  Shutting down ExecutorService 'applicationTaskExecutor'
[INFO] 
[INFO] Results:
[INFO] 
[INFO] Tests run: 54, Failures: 0, Errors: 0, Skipped: 0
[INFO] 
[INFO] 
[INFO] --- jacoco-maven-plugin:0.8.2:report (report) @ farm-water-delivery ---
[INFO] Loading execution data file /media/joshua/martian/jobs/WaterDelivery/target/jacoco.exec
[INFO] Analyzed bundle 'Farm Water Delivery' with 37 classes
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time: 15.113 s
[INFO] Finished at: 2019-12-27T23:49:30+11:00
[INFO] ------------------------------------------------------------------------
```

## Maven/Gradle - Building the source

The following commands will download all the required dependencies and create an executable JAR file in the target directory.
The executable JAR was created using [Spring Boot Maven Plugin](https://docs.spring.io/spring-boot/docs/current/maven-plugin/index.html)

```bash
mvn clean install
```

or using Gradle

```bash
./gradlew clean build
```

## Maven/Gradle - Building and running the app from terminal in one command

Execute the below command to build and execute the app from terminal.

```bash
mvn clean install && java -jar target/farm-water-delivery-0.0.1-SNAPSHOT.jar
```

or using Gradle

```bash
./gradlew clean build && java -jar build/libs/FarmWaterDelivery-0.0.1-SNAPSHOT.jar
```

## Maven/Gradle - Running the app from spring boot

Execute `mvn spring-boot:run` from terminal.

Or using Gradle `./gradlew bootRun`

These commands would run the application in-place without actually building a target JAR file

You should see the following logged to console.

```log
2019-12-27 23:58:38,459 INFO  Starting FarmWaterRequestApplication on xubuntuVostro with PID 21847 (/media/joshua/martian/jobs/
...........
2019-12-27 23:58:40,696 INFO  Completed initialization in 7 ms
2019-12-27 23:58:40,742 INFO  Started ServerConnector@16c63f5{HTTP/1.1,[http/1.1]}{0.0.0.0:8887}
2019-12-27 23:58:40,744 INFO  Jetty started on port(s) 8887 (http/1.1) with context path '/'
2019-12-27 23:58:40,749 INFO  Started FarmWaterRequestApplication in 2.782 seconds (JVM running for 3.606)
```

## Accessing the application

You an access the app using this URL **`http://localhost:8887`** running on default port **8887**.

Port number is configurable. Just in case you have another app/service running on port 8887, you can update the port number in
[application.properties](src/main/resources/application.properties) using this property

**`server.port=YOUR_NEW_PORT_NUMBER`**


## Code coverage

### Jacoco code coverage
While the goal of the test harness is to cover as much edge and corner cases, that naturally led to a wider coverage of over 85%.
Code coverage was both executed as part of maven build cycle using [JaCoCo](https://github.com/jacoco/jacoco)  and from IDE


![alt text][codecoverage]

![alt text][codeCoverageJacoco]


### Coverall report
Executing the following command will generate Jacoco and [coveralls coverage reports](https://coveralls.io/github/joshluisaac/FarmWaterDelivery?branch=master).

```bash
mvn clean test jacoco:report coveralls:report
```

```bash
./gradlew build jacocoTestReport
```


![alt text][coverallReport]


## Code formatting
Source code was formatted using [google-java-format](https://github.com/google/google-java-format)



[codecoverage]: screenshots/codeCoverage_Ide.png "codeCoverage_Ide"
[codeCoverage_Ide2]: screenshots/codeCoverage_Ide2.png "codeCoverage_Ide2"
[codeCoverageJacoco]: screenshots/codeCoverageJacoco.png "codeCoverageJacoco"
[coverallReport]: screenshots/coverallReport.png "coverallReport"
[testSummary]: screenshots/testSummary.png "testSummary"