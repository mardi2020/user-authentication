# user-authentication
Smilegate dev camp 1인 프로젝트 저장소 - 인증 시스템

## 목차
1. [개요](#-개요)
2. [사용 기술](#-기술)
3. [아키텍처](#-아키텍처)
    - [Back-end diagram](#be-diagram)
    - [마이크로서비스 통신](#microservices)
    - [Database diagram](#db-diagrams)
4. [화면 구성](#화면)
5. [기록](#-기술)
6. [회고](#-회고)

### 👩🏻‍🔧 개요
사용자 인증 시스템은 회원가입부터 내정보 관리, 관리자의 사용자 관리를 지원하는 웹 기반 시스템입니다. 또한, 사용자들을 그룹으로 묶어 관리할 수 있습니다.

### 🖥️ 개발 환경
<img src="https://img.shields.io/badge/macOS-000000?style=flat&logo=macOS&logoColor=white"> <img src="https://img.shields.io/badge/GitHub-000000?style=flat&logo=GitHub&logoColor=white"> <img src="https://img.shields.io/badge/intellij-000000?style=flat&logo=intellij IDEA&logoColor=white"> <img src="https://img.shields.io/badge/WebStorm-000000?style=flat&logo=WebStorm&logoColor=white">

### 🛠 기술
| category  | name                                                                                                                                                                                            |
|-----------|-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| language  | <img src="https://img.shields.io/badge/java 11-007396?style=flat&logo=java&logoColor=white"> <img src="https://img.shields.io/badge/javascript-F7DF1E?style=flat&logo=javascript&logoColor=black"> |
| framework | <img src="https://img.shields.io/badge/springboot 2.7.6-6DB33F?style=flat&logo=springboot&logoColor=white"> <img src="https://img.shields.io/badge/react-61DAFB?style=flat&logo=react&logoColor=black"> |
| database  | <img src="https://img.shields.io/badge/mysql 8.0-4479A1?style=flat&logo=mysql&logoColor=white"> <img src="https://img.shields.io/badge/redis-DC382D?style=flat&logo=redis&logoColor=white">                                                                                              |
| devOps    | <img src="https://img.shields.io/badge/docker-2496ED?style=flat&logo=docker&logoColor=white"> <img src="https://img.shields.io/badge/Apache Kafka-231F20?style=flat&logo=Apache Kafka&logoColor=white">                                                                                                                                                                         |

### 💡 아키텍처
#### BE Diagram
![스크린샷 2022-12-29 오후 8 35 02](https://user-images.githubusercontent.com/58351498/209945648-64451685-e3fb-4d4e-aeb8-570c41c8a113.png)

#### Microservices
![스크린샷 2022-12-29 오후 8 50 54](https://user-images.githubusercontent.com/58351498/209947227-3308091f-ed6c-425e-b458-fdf16bf9a20d.png)

#### DB Diagrams
![users](https://user-images.githubusercontent.com/58351498/208630845-87e4ac8e-e7eb-4157-8a58-1d1a4112db87.png)
![diagram](https://user-images.githubusercontent.com/58351498/208630856-d5966521-0287-4e8a-ae7a-1e90b095928d.png)

### 화면

| ![스크린샷 2022-12-23 오후 9 11 35](https://user-images.githubusercontent.com/58351498/209353236-f38f010f-8777-4409-a9d3-3309e553ac30.png) | ![스크린샷 2022-12-23 오후 9 11 45](https://user-images.githubusercontent.com/58351498/209353233-f1701eed-c2d6-40a1-be79-7b9b722470d6.png) |
|:--------------------------------------------------------------------------------------------------------------------------------------------:|:--------------------------------------------------------------------------------------------------------------------------------------------:|
 |                                                                                                     메인 페이지                                   |                                                                    아이디 찾기                                                                    |

|  ![스크린샷 2022-12-23 오후 9 11 40](https://user-images.githubusercontent.com/58351498/209353235-f4a1600b-856a-4bd8-a8a0-b6e0880137ff.png)  | ![스크린샷 2022-12-23 오후 9 11 53](https://user-images.githubusercontent.com/58351498/209353231-0d4e8eea-72e7-4445-9204-f1f3c3be1f5a.png) |
|:----------------------------------------------------------------------------------------------------------------------------------------------:|:--------------------------------------------------------------------------------------------------------------------------------------------:|
|                                                                    로그인 페이지                                                                     |                                                                     회원가입                                                                     |

|  ![스크린샷 2022-12-23 오후 9 12 55](https://user-images.githubusercontent.com/58351498/209353222-cfaba590-3ff9-4964-88c4-e1f321cef327.png)  |  ![스크린샷 2022-12-23 오후 9 12 11](https://user-images.githubusercontent.com/58351498/209353224-995b9167-5f81-4a2d-a4f6-7d4332763373.png)  |
|:----------------------------------------------------------------------------------------------------------------------------------------------:|:----------------------------------------------------------------------------------------------------------------------------------------------:|
|                                                                     그룹 페이지                                                                     | 내 정보 페이지                                                                                                                                     |

| ![스크린샷 2022-12-23 오후 9 12 08](https://user-images.githubusercontent.com/58351498/209353229-181fec35-45c6-42a0-badc-875a36d7a209.png) | ![스크린샷 2022-12-23 오후 11 47 10](https://user-images.githubusercontent.com/58351498/209354867-e5dc5d85-7988-4908-b77f-457ca68f453e.png) |
|:--------------------------------------------------------------------------------------------------------------------------------------------:|:---------------------------------------------------------------------------------------------------------------------------------------------:|
|                                                             그룹 페이지(속한 그룹이 없을 때)                                                              |                                                              사용자 관리 페이지(관리자 전용)                                                               |
### 📃 기록
개발하면서 알게된 내용과 느낀점을 정리했습니다.

[Cache 적용하기](https://velog.io/@mardi2020/Cache-%EC%A0%81%EC%9A%A9%ED%95%98%EA%B8%B0)

[Kafka 적용하기](https://velog.io/@mardi2020/kafka-%EC%A0%81%EC%9A%A9%ED%95%98%EA%B8%B0)

[Refresh token 적용하기](https://velog.io/@mardi2020/JWT-access-token-refresh-token)

[Dockerfile 작성하기(1)](https://velog.io/@mardi2020/Dockerfile-%EC%9E%91%EC%84%B1%ED%95%98%EA%B8%B0)

[Dockerfile 작성하기(2)](https://velog.io/@mardi2020/Docker-image-%ED%81%AC%EA%B8%B0-%EC%A4%84%EC%97%AC%EB%B3%B4%EA%B8%B0)


### ⏳ 회고
[프로젝트 회고](https://velog.io/@mardi2020/%ED%9A%8C%EA%B3%A0)
