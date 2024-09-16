### 1. postgre 접속 문제
도커 컴포즈로 postgresql 과 pgadmin 을 띄우고 로컬로 실행해 테이블을 생성하려 했다 <br>
이 때, 접속 에러가 발생했는데 확인해보니 로컬에 postgre 가 있고 도커로 떠서 총 2개의 db가 떠있는 문제였다 <br>
```text
netstat -ano | findstr 5432

-- window
services.msc 열고 postgresql 찾아서 중지

-- linux
sudo systemctl stop postgresql
```
