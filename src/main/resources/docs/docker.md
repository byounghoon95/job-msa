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