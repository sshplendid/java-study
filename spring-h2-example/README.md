# Spring-H2 example

Spring Framework 웹 애플리케이션과 H2 데이터베이스 연동예제

* [ ] Web App 생성
* [ ] /hello API
* [ ] Greeter 스키마 생성
* [ ] 테스트 코드 작성

## 웹 콘솔 설정

### properties 파일 설정

아래 설정을 application properties 파일에 추가한다.

```properties
spring.h2.console.enabled=true
spring.h2.console.path=/h2console
```

### Java Configuration

`H2Config.java` 파일 참고

## 참고 자료

* https://dodo4513.github.io/2018/02/11/spring_h2/
* https://gongzza.github.io/java/spring/spring-mybatis-h2-localtest/
