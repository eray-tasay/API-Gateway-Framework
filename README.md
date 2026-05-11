## Project Overview
This project is a Java framework that can be used to develop distributed systems. 
Specifically, it provides out-of-the-box API gateway, service discovery, and load balancing solutions 
so that developers can focus just on developing business logic.

## How to Run?

### API Gateway
To run the API gateway, download the .jar file in the release section and run it as follows:

```bash
java -jar APIGateway-1.0.0.jar --spring.config.additional-location=file:./your-config.yaml
```

How to write your-config.yaml is explained below. If configuration with a file does not satisfy your needs,
you can add the following dependency to your .pom file. This allows you to configure your API gateway using Java.
Make sure you installed the project using Maven before because it is not pushed to any public Maven repository yet.

```xml
<dependency>
    <groupId>com.eraytasay</groupId>
    <artifactId>waffle-framework-api-gateway</artifactId>
    <version>1.0.0</version>
</dependency>
```

### Service Discovery
To run the service discovery, download the .jar file in the release section and run it as follows:

```bash
java -jar ServiceDiscovery-1.0.0.jar --spring.config.additional-location=file:./your-config.yaml
```

How to write your-config.yaml is explained below. If configuration with a file does not satisfy your needs,
you can add the following dependency to your .pom file. This allows you to configure your service discovery using Java.
Make sure you installed the project using Maven before because it is not pushed to any public Maven repository yet.

```xml
<dependency>
    <groupId>com.eraytasay</groupId>
    <artifactId>waffle-framework-discovery</artifactId>
    <version>1.0.0</version>
</dependency>
```

### Service Discovery Client
To register your services with service discovery, you need to add the following dependency to your build file. 
Apart from that, it is a normal web service that implements business logic. 
Make sure you installed the project using Maven before because it is not pushed to any public Maven repository yet.

```xml
 <dependency>
    <groupId>com.eraytasay</groupId>
    <artifactId>waffle-framework-discovery-client</artifactId>
    <version>1.0.0</version>
</dependency>
```
