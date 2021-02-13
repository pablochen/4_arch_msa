# Vacation Program 

## 개발목표
- AS-IS : 데이터베이스만 도커라이즈
- TO-BE : 
  - [x] 프론트엔드 분리
  - [x] 백엔드 분리 
  - [x] 서비스 별 하나의 도커컴포즈 파일로 실행(프론트+백+DB)

## 개발환경
- NodeJs + VueJs
- JDK8 + Jar
- MySql 
- Docker-compose

## 빌드 및 실행
- 유저 서비스 
  - dir : ./userService
  - cmd : docker-compose up
  - 실행 : localhost:3001

- 휴가 서비스
  - dir : ./vacationService
  - cmd : docker-compose up
  - 실행 : localhost:3002

## 기타
- 서비스 분리가 목표이기 때문에 운영환경 스태틱 빌드를 하지 않고 간단하게 구성(webpack-dev-server 활용)
- nginx 포트 포워딩에 대한 구현은 추후 쿠버네티스 or 도커스웜 프로젝트 구현 시, 구성 예정

## 아키텍처 이미지
![서비스_아키텍처_V0 2](https://user-images.githubusercontent.com/20297475/107861760-34c88c80-6e8b-11eb-853d-fbc98047faa4.jpg)

