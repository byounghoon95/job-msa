Actuator 는 Spring Boot 애플리케이션의 운영 및 모니터링을 도와주는 기능을 제공하는 모듈이다.
애플리케이션의 상태, 성능, 메트릭, 설정 등을 쉽게 확인하고 관리할 수 있는 다양한 엔드포인트를 통해 운영 환경에서 필요한 정보를 수집할 수 있도록 지원한다.

### 자주 사용하는 Endpoint
- /health	
  - 애플리케이션의 상태를 보여준다. 데이터베이스 연결 상태, 디스크 공간, 사용자 정의된 상태 점검과 같은 정보를 확인하는 데 유용
- /info
  - 애플리케이션의 임의의 정보를 표시한다. 주로 애플리케이션 버전, git 커밋 정보 등을 표시하는 데 사용
- /metrics
  - 실행 중인 애플리케이션의 성능 및 동작을 이해할 수 있는 '메트릭' 정보를 보여준다
- /loggers
  - 애플리케이션의 로깅 수준을 조회하고 수정할 수 있다
- /beans
  - 애플리케이션에서 사용 중인 모든 Spring 빈 목록을 제공
- /shutdown
  - 애플리케이션을 정상적으로 종료할 수 있다

### /health
```json
{
  "status": "UP",
  "components": {
    "db": {
      "status": "UP",
      "details": {
        "database": "H2",
        "validationQuery": "isValid()"
      }
    },
    "diskSpace": {
      "status": "UP",
      "details": {
        "total": 52427747328,
        "free": 48994373632,
        "threshold": 10485760,
        "path": "D:\\spring\\job-msa\\.",
        "exists": true
      }
    },
    "ping": {
      "status": "UP"
    }
  }
}
```

### /info
아래 정보를 통해 외부에 노출하고 싶은 정보를 적을 수 있다 <br>
info.env.enabled=true 가 없으면 정보가 표출되지 않는다
```text
management:
  endpoints:
    web:
      exposure:
        include: "*"
  info:
    env:
      enabled: true

info:
  app:
    name: JobApplication
    description: Job App
    version: 1.0.0
    
```
```json
{
  "app": {
    "name": "JobApplication",
    "description": "Job App",
    "version": "1.0.0"
  }
}
```

### /metrics
metrics 에선 아래와 같은 정보를 저장하는데 실제론 훨씬 많은 데이터가 저장된다 <br>
/actuator/metrics/disk.total 와 같이 아래 metric 을 넣어 세부 값을 조회할 수 있다
```json
{
  "names": [
    "application.ready.time",
    "application.started.time",
    "disk.free",
    "disk.total",
    "executor.active",
    "executor.completed",
    "executor.pool.core",
    "executor.pool.max",
    "executor.pool.size"
  ]
}
```

### /loggers
```json
{
  "levels": [
    "OFF",
    "ERROR",
    "WARN",
    "INFO",
    "DEBUG",
    "TRACE"
  ],
  "loggers": {
    "ROOT": {
      "configuredLevel": "INFO",
      "effectiveLevel": "INFO"
    },
    "_org": {
      "effectiveLevel": "INFO"
    },
    "_org.springframework": {
      "effectiveLevel": "INFO"
    }
  }
}
```

### /beans
등록된 빈 정보를 노출한다
```json
{
  "contexts": {
    "application": {
      "beans": {
        "spring.jpa-org.springframework.boot.autoconfigure.orm.jpa.JpaProperties": {
          "aliases": [],
          "scope": "singleton",
          "type": "org.springframework.boot.autoconfigure.orm.jpa.JpaProperties",
          "dependencies": []
        },
        "applicationTaskExecutor": {
          "aliases": [
            "taskExecutor"
          ],
          "scope": "singleton",
          "type": "org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor",
          "resource": "class path resource [org/springframework/boot/autoconfigure/task/TaskExecutorConfigurations$TaskExecutorConfiguration.class]",
          "dependencies": [
            "org.springframework.boot.autoconfigure.task.TaskExecutorConfigurations$TaskExecutorConfiguration",
            "taskExecutorBuilder"
          ]
        }
      }
    }
  }
}
```

### /shutdown
자동으로 나오는 endpoint 가 아니므로 설정이 필요하다 <br>
해당 경로에 접근할 수 있으며 다른 사람도 서버를 내릴 수 있기 때문에 보안이 중요하다 <br>
Post 요청을 통해서만 사용가능하므로 Postman 으로 보내야한다 <br>
/actuator/shutdown 을 보내면 서버가 종료된다
```text
management:
  endpoint:
    shutdown:
      enabled: true
```
