각각의 마이크로서비슨느 동적으로 배포되기에 이를 쉽게 발견하고 통신할 수 있도록 돕는 <br>
중앙 디렉터리가 필요하다. 그리고 이 역할을 하는 것이 Eureka 이다. <br>
각각의 마이크로서비스를 Service Registry 에 등록하면 각 서비스가 서로를 쉽게 찾을 수 있다

### Eureka Server

우선 eureka-server를 등록한다. eureka 사용 시 spring cloud 관련 dependency도 필요하니 잘 확인해야한다 <br>
register-with-eureka 와 fetch-registry 는 서비스를 등록할지에 대한 부분이다 <br>
Eureka 서버는 자기 자신은 등록할 필요가 없기에 false 를 통해 등록에서 제외한다
```text
// build.gradle
implementation 'org.springframework.cloud:spring-cloud-starter-netflix-eureka-server'

@SpringBootApplication
@EnableEurekaServer
public class EurekaServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(EurekaServiceApplication.class, args);
    }
}

// application.yml
eureka:
  client:
    register-with-eureka: false
    fetch-registry: false
```

### 그 외 서버
```text
// build.gradle
implementation 'org.springframework.cloud:spring-cloud-starter-netflix-eureka-client'

// application.yml
// 없으면 Eureka 에 서비스의 이름이 unknown 으로 등록됨
spring:
  application:
    name: review-service
   
// 없어도 eureka 에 자동으로 등록되지만 설정하는 법을 알아두기 위함
eureka:
  client:
    serviceUrl:
      defaultZone: http://localhost:8761/eureka/
    fetch-registry: true
    register-with-eureka: true
```

### @LoadBalanced
서비스가 나누어졌기에 하드코딩된 경로로 전달하면 서버의 호스트가 변경될 시 장애가 발생한다 <br>
@LoadBalanced 를 사용하면 host 명이 아닌 유레카에 저장된 서비스의 명으로 호출이 가능하다
```text
Company company = restTemplate.getForObject("http://COMPANY-SERVICE:8081/companies/" + job.getCompanyId(), Company.class);
```

