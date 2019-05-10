# Spring boot: Monorithic application

## Requirements

* Redis(localhost:6379)
* Rabbbit MQ(localhost:5672)

## How to run

### Docker requirements

#### Redis

```bash
$ docker run -d -p 6379:6379 redis
```

#### Rabbit MQ

```bash
$ docker run -d --hostname my-rabbit --name rabbit -p 15672:15672 -p 5672:5672 rabbitmq:3-management

## Examples

### 1. Web Application

localhost:8080/mono로 접속

### 2. Messaging Queue

1. Rabbit MQ(localhost:5672) 실행
2. Consumer 애플리케이션 실행
  1. mq-receiver
  2. mono: Web application 실행하면 fanout subscriber 기본으로 연결
3. Producer 애플리케이션 실행: 1,000,000 건 fanout패턴으로 생성
