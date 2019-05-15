# 스터디 자료

## 구성

* mono: 모노리틱 웹 애플리케이션
* mq-producer: rabbit mq producer
* mq-receiver: rabbit mq consumer
* kafka-sample: kafka producer/consumer

## 필요조건

* Redis: mono 애플리케이션 실행 시 필요 (local profile을 사용하면 임베디드 레디스 구동)
* Rabbit MQ
* Kafka

### Redis

```bash
$ docker run -d -p 6379:6379 redis
```

### Rabbit MQ

```bash
$ docker run -d --hostname my-rabbit --name rabbit -p 15672:15672 -p 5672:5672 rabbitmq:3-management
```

### Kafka

[Docker-Kafka github](https://github.com/wurstmeister/kafka-docker.git)를 받아서 docker-compose-single-broker.yml 파일을 수정해서 실행한다.
yml 파일 수정방법은 [여기](https://medium.com/@dokkl2323/kafka-47c7b785c65f)를 참고한다.

```bash
$ docker-compose -f ./docker-compose-single-broker.yml up
```

## 시나리오

### 1. Web Application

localhost:9090/mono로 접속

### 2. Messaging Queue by Rabbit MQ

1. Rabbit MQ(localhost:5672) 실행
2. Consumer 애플리케이션 실행
  1. mq-receiver
  2. mono: Web application 실행하면 fanout subscriber 기본으로 연결
3. Producer 애플리케이션 실행: 1,000,000 건 fanout패턴으로 생성

### 3. Messaging Queue by Kafka

localhost:8080/kf/send/{} 로 메시지를 생성한다.

topic1에 컨슈머 2개 연결 (kafka-sample, mono)

콘솔에서 확인