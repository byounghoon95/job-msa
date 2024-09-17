스프링부트는 도커파일을 작성하지 않아도 이미지를 자동으로 만들어 줄 수 있다. <br>
따라서, 도커파일 없이 이미지를 만들어 볼 것이다

### 이미지 생성
- maven
  - ./mvnw spring-boot:build-image -Dspring-boot.build-image.imageName=<IMAGE-NAME>
- gradle
  - ./gradlew bootBuildImage
  - build.gradle 에 bootBuildImage 추가하고 이미지명 기입필요

```text
빌드하면 도커 이미지가 생성된다
도커 허브가 안켜져 있다면 도커 허브에 접속 후 login 한다
push 시 docker hub 의 저장소명을 적지 않으면 push 가 되지 않는다

./gradlew bootBuildImage
docker login
docker push <저장소명>/<이미지명>:버전
```

### postgre 컨테이너 생성
아래와 같이 컨테이너를 생성할 수 있다 <br>
하지만 항상 개별로 실행하면 작업이 많으므로 도커 컴포즈를 이용하자
```text
docker run -d --name db -e POSTGRES_PASSWORD=hoon postgres
docker run -d --name pgadmin -e PGADMIN_DEFAULT_EMAIL=admin@admin.com -e PGADMIN_DEFAULT_PASSWORD=hoon dpage/pgadmin4

아래 명령어는 pgadmib 에서 db로 ping 날리는 명령어인데 network 설정이 없어 에러 발생
docker exec -it pgadmin ping db
docker rm -f db pgadmin

네트워크 설정이 되어 ping 실행 시 정상 동작
docker network create my-network
docker run -d --name db --network my-network -e POSTGRES_PASSWORD=hoon postgres
docker run -d --name pgadmin --network my-network -e PGADMIN_DEFAULT_EMAIL=admin@admin.com -e PGADMIN_DEFAULT_PASSWORD=hoon dpage/pgadmin4

아래는 최종 명령어이다

docker run -d \
--name postgres_container \
-e POSTGRES_USER=hoon \
-e POSTGRES_PASSWORD=hoon \
-e PGDATA=/data/postgres \
-v postgres:/data/postgres \
-p 5432:5432 \
--network postgres \
--restart unless-stopped \
postgres

docker run -d \
--name pgadmin_container \
-e PGADMIN_DEFAULT_EMAIL=pgadmin4@pgadmin.org \
-e PGADMIN_DEFAULT_PASSWORD=admin \
-e PGADMIN_CONFIG_SERVER_MODE=False \
-v pgadmin:/var/lib/pgadmin \
-p 5050:80 \
--network postgres \
--restart unless-stopped \
dpage/pgadmin4

컨테이너를 데몬으로 띄울 시 로그를 보는 커맨드
docker logs postgres
```