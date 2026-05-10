## Project Overview

This project is a java framework that can be used to develop distributed systems. 
Specifically, it provides out-of-the-box API gateway, service discovery, load balancing solutions 
so that developers can focus on just developing business logic. The provided service discovery 
allows services to be registered with it. Then, it provides necessary API for the API Gateway to consume 
in order to get services' information. 

## How to Run?

### API Gateway
To run API gateway, download the .jar file in the release section and run it as follows:

```bash
java -jar APIGateway-1.0.0.jar --spring.config.additional-location=file:./your-config.yaml
```

How to write your-config.yaml is explained below. If configuration with a file does not satisfy your needs
you can add the following dependency to your .pom file. This allows you to configure your API gateway using Java.

```xml
<dependency>
    <groupId>com.eraytasay</groupId>
    <artifactId>waffle-framework-api-gateway</artifactId>
    <version>1.0.0</version>
</dependency>
```

### Service Discovery
To run service discovery, download the .jar file in the release section and run it as follows:

```bash
java -jar ServiceDiscovery-1.0.0.jar --spring.config.additional-location=file:./your-config.yaml
```

How to write your-config.yaml is explained below. If configuration with a file does not satisfy your needs
you can add the following dependency to your .pom file. This allows you to configure your service discovery using Java.

```xml
<dependency>
    <groupId>com.eraytasay</groupId>
    <artifactId>waffle-framework-discovery</artifactId>
    <version>1.0.0</version>
</dependency>
```
### Service Discovery Client
To register your services with service discovery you need to add the following dependency to your build file. 
Apart from that, it is a normal web service that implements business logic. 

```xml
 <dependency>
    <groupId>com.eraytasay</groupId>
    <artifactId>waffle-framework-discovery-client</artifactId>
    <version>1.0.0</version>
</dependency>
```

## How to Configure

### 1) Service Discovery
Service discovery, is basically a web service that provides an API for services to be registered with it. 
It resides on a static IP and port known by all other nodes.
Service discovery stores information of all services in the system and provides necessary endpoints so that API gateway fetch the service data. 

End Points
To register a service, you need to send a HTTP post request to /register endpoint. The expected body of the request is as follows:

```json
{
    "serviceName": "payment-service",
    "loadBalancingAlgorithm": "ROUND_ROBIN",
    "port": 8081 
}
```
