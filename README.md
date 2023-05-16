## 환경설정

spring boot 3.0.5

gradle

jdk 17

---

### Dependencies

spring web

spring boot devtools

lombok

mysql driver

thymeleaf

spring data jpa

---

**mysql port: 3390**  

## MySQL 데이터베이스 생성 및 사용자 생성

`create user 'cafeAdminister'@'%' identified by 'bitc1234';
GRANT ALL PRIVILEGES ON *.* TO 'cafeAdminister'@'%';
create database cafe;`

---

### ****Create the `application.properties` File**

서버포트 : 8085


--
## 릴레이션 스키마 수정해야 할 부분
creatDate 추가 - 
