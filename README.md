# 지리기반 맛집 추천 웹 서비스

![image](https://bow-hair-db3.notion.site/image/https%3A%2F%2Fprod-files-secure.s3.us-west-2.amazonaws.com%2F571a24a3-05f9-4ea5-b01f-cba1a3ac070d%2F77d8ee9c-7271-46f6-b4ea-02fda08cccf4%2Flogo.png?table=block&id=a9a2ec57-b655-45e4-be7d-a370c4649007&spaceId=571a24a3-05f9-4ea5-b01f-cba1a3ac070d&width=2000&userId=&cache=v2)
<img src="https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white"/></a>
<img src="https://img.shields.io/badge/Gradle-02303A?style=for-the-badge&logo=gradle&logoColor=white"/></a>
<img src="https://img.shields.io/badge/Spring Boot 3.1.5-6DB33F?style=for-the-badge&logo=spring&logoColor=white"/></a>
<img src="https://img.shields.io/badge/Spring Security-6DB33F?style=for-the-badge&logo=spring-security&logoColor=white"/></a>
<img src="https://img.shields.io/badge/Spring rest docs-6DB33F?style=for-the-badge"/></a>
<img src="https://img.shields.io/badge/JWT-000000?style=for-the-badge&logo=json-web-tokens&logoColor=white"/></a>
<img src="https://img.shields.io/badge/Spring Data JPA-gray?style=for-the-badge&logoColor=white"/></a>
<img src="https://img.shields.io/badge/QueryDSL-0078D4?style=for-the-badge&logo=Spring Data JPA&logoColor=white"/></a>
<img src="https://img.shields.io/badge/MySQL 8-4479A1?style=for-the-badge&logo=MySQL&logoColor=white"/></a>
<img src="https://img.shields.io/badge/Junit-25A162?style=for-the-badge&logo=JUnit5&logoColor=white"/></a>
<img src="https://img.shields.io/badge/Mockito-6DB33F?style=for-the-badge"/></a>

## 목차

1. [개발 기간](#개발-기간)
2. [코드 컨벤션](#코드-컨벤션)
3. [프로젝트 개요](#프로젝트-개요)
4. [프로젝트 동작 예시](#프로젝트-동작-예시)
5. [프로젝트 일정관리](#프로젝트-일정관리)
6. [구현 기능 목록](#구현-기능-목록)
7. [설계 및 의도](#설계-및-의도)
8. [ERD](#erd)
9. [구현 과정](#구현-과정)
10. [담당한 역할](#담당한-역할)
11. [API 명세](#api-명세)
12. [테스트](#테스트)
13. [TIL 및 회고](#TIL-및-회고)

## 개발 기간

2023-10-31 ~ 2023-11-09 (10일)

## 코드 컨벤션

코드 컨벤션 관한 노션 정리

https://mizuirohoshi7.notion.site/b298797bef954741bd7ab33a047ba01a

## 프로젝트 개요

사용자 위치 기반 맛집을 추천하여 더 나은 다양한 음식 경험을 제공하기 위한 서비스입니다.

맛집에 대한 평가를 할 수 있어 더 나은 사용자 경험을 제공합니다.

공공데이터를 활용하여, 데이터 파이프라인을 구축했습니다. 스케쥴러를 도입하여 지역 음식점 목록을 자동으로 업데이트합니다.

자주 변경되지 않는 시구군(지역) 데이터는 csv파일을 업로드를 통해 구현했습니다.

## 프로젝트 동작 예시
#### 사용자 위치 기반 점심 추천

![image](https://github.com/kawkmin/geoRecommendEats/assets/86940335/950a1db0-168d-4e69-86b6-d0c46690686e)


## 프로젝트 일정관리

**Git Projects 사용**

https://github.com/orgs/wanted-preonboarding-team-m/projects/4/views/1

## 구현 기능 목록


<details>
    <summary>자세히 (클릭)</summary>
   
* 유저
    * 회원가입
    * 로그인
    * 사용자 위치 설정
    * 사용자 정보 상세 조회


* 데이터파이프라인

    * 데이터 수집: Open API 연동 및 규격 분석
    * 데이터 전처리: 누락 값, 이상 값 및 표준화 처리
    * 데이터 저장: Raw 데이터 규격에 맞는 모델링
    * 자동화: 스케쥴러 도입
    * csv 데이터 업로드


* 맛집
    * 시구군 목록 조회
    * 사용자 위치 기반 맛집 목록 조회
    * 맛집 상세 정보 조회
    * 맛집 평가

</details>

## 설계 및 의도

<details>
    <summary>자세히 (클릭)</summary>

### 회원가입

- 비밀번호는 단방향 암호화 알고리즘인 `Bcrypt` 를 사용하여 암호화 합니다.
- 아이디는 중복이 불가능하도록, 중복 체크를 합니다.

### 로그인

- 아이디 비밀번호 일치를 `AuthenticationProvider` 를 사용하여 검증합니다.
- `Refresh Token`을 7일 , `Access Token`을 30분으로 설정합니다.

### 로그아웃

- `Header`에 담긴 토큰의 권한(회원은 자신만 로그아웃이 가능)을 검증합니다.

### open api 호출 시 고려

- 데이터 조회 개수 제한 극복
    
    경기도 공공데이터 포털에서 한 번에 조회할 수 있는 데이터의 양을 1000개로 제한했습니다. 하지만 전체 데이터의 개수는 1000개보다 많아서 모든 데이터를 조회하는 로직을 구현할 필요가 있었습니다.
    
    1부터 시작하는 인덱스 변수를 두고 반복문안에서 인덱스를 1씩 증가시키면서 조회를 계속했습니다. 조회를 하다가 현재 인덱스의 데이터 개수가 1000개보다 적으면 다음 페이지는 존재하지 않는 것이기에 반복문을 탈출하는 조건으로 설정했습니다.
    

- raw 데이터에 default 값 설정
    
    json으로 받아오는 데이터의 속성들은 대부분이 null 값이었습니다. DB에 저장할때 null인 속성의 default 값을 설정하기 위해 raw 데이터를 담는 dto 객체의 속성에 default 값을 명시했습니다.
    
    하지만 `RestTemplate.exchange` 메서드는 값이 null이어도 덮어 씌우기때문에 이를 방지하기 위해 `@JsonSetter(nulls = Nulls.*SKIP)*` 어노테이션을 사용했습니다. 이 어노테이션은 값이 null인 속성은 객체의 기본값을 유지하게끔 해줍니다.

### 리뷰 

식당에는 평균 점수가 필요합니다.

식당의 모든 리뷰의 점수 평균 계산을 매번 조회 마다 하는 것은 성능이 매우 안좋다고 판단하였습니다.

따라서 식당의 평균 점수리뷰의 작성,수정,삭제 마다 계산을 하여, 식당의 DB 컬럼에 넣어주기로 하였습니다.

계산을 하면 당연히 조금씩 오차가 생기는데, 정확한 계산을 위한 수학 알고리즘이나 매번 계산할 필요할까? 라는 생각이 들었습니다.

하지만 리뷰는 그렇게 많은 데이터가 쌓이지 않을 뿐더러, 특정 리뷰 수가 넘어가면, 다시한번 계산하는 로직이 더 좋을것이라 판단하였습니다.

### 리뷰 작성

- 점수는 1.0 1.5... 5.0 까지 0.5 단위로 1~5 사이여야만 합니다.
    - `@Constraint` 를 활용해서 커스텀 valid 어노테이션을 만들어 ReqDto 검증
- 식당의 평균 점수 계산
    - 총 평점 (원래 총 평점(원래 평점 * 원래 리뷰의 수) + 새로운 평점) / 총 리뷰 수 (원래 리뷰의 수 + 1)

### 리뷰 수정

- 식당의 평균 점수 계산
    - 총 평점 (원래 총 평점 (원래 평점 * 원래 리뷰의 수 ) - 이전 평점 + 새로운 평점 ) / 원래의 총 리뷰

### 리뷰 삭제

- 식당의 평균 점수 계산
    - 총 평점 (원래 총 평점 (원래 평점 * 원래 리퓨의 수) - 삭제된 평점) / 총 리뷰 수 (원래 리뷰의 수 -1)

</details>

## ERD

**Erd Cloud**

<details>
    <summary>자세히 (클릭)</summary>
   
![87D62F6B-6E38-4CC9-A76A-7D3489A9654E_1_201_a](https://github.com/wanted-preonboarding-team-m/02_geoRecommendEats/assets/57309311/5080cb4a-1d30-44d8-8a4c-f68453506643)

</details>

## 구현 과정


<details>
    <summary>자세히 (클릭)</summary>

1. [프로젝트 환경 설정](https://github.com/wanted-preonboarding-team-m/02_geoRecommendEats/issues/1)
    * application.yml 설정
    * P6Spy 설정
    * RestDocs 설정
    * Response Api Format 설정
    * 공통 예외 처리


2. 유저 기능 구현
    * [회원가입](https://github.com/wanted-preonboarding-team-m/02_geoRecommendEats/issues/13)
    * [로그인](https://github.com/wanted-preonboarding-team-m/02_geoRecommendEats/issues/16)
    * [사용자 위치 설정](https://github.com/wanted-preonboarding-team-m/02_geoRecommendEats/issues/28)
    * [사용자 정보 상세 조회](https://github.com/wanted-preonboarding-team-m/02_geoRecommendEats/issues/28)


3. [데이터파이프라인](https://github.com/wanted-preonboarding-team-m/02_geoRecommendEats/issues/4)
    * [스케쥴러 적용](https://github.com/wanted-preonboarding-team-m/02_geoRecommendEats/issues/38)
    * [csv 데이터 업로드](https://github.com/wanted-preonboarding-team-m/02_geoRecommendEats/issues/8)


4. 맛집 기능 구현
    * [시구군 목록 조회](https://github.com/wanted-preonboarding-team-m/02_geoRecommendEats/issues/19)
    * [사용자 위치 기반 맛집 목록 조회](https://github.com/wanted-preonboarding-team-m/02_geoRecommendEats/issues/21)
    * [맛집 상세정보 조회](https://github.com/wanted-preonboarding-team-m/02_geoRecommendEats/issues/25)
    * [맛집 평가](https://github.com/wanted-preonboarding-team-m/02_geoRecommendEats/issues/29)
  
</details>

## 담당한 역할

* [김정훈](https://github.com/jhva): csv 파일 업로드 구축
* [곽민성](https://github.com/kawkmin): 사용자 회원가입 및 로그인, 맛집 평가 구현
* [김선재](https://github.com/mizuirohoshi7): 데이터 파이프라인 구축
* [최소영](https://github.com/soyeong125): 프로젝트 환경 설정, 맛집 조회 기능 구현

* 코드 리뷰 및 리팩토링은 조원 모두가 함께 했습니다.

## API 명세

**Spring Rest Docs 기반 API 명세서**

<details>
    <summary>자세히 (클릭)</summary>

[API 명세서 보기 (클릭)](https://wanted-preonboarding-team-m.github.io/02_geoRecommendEats/src/main/resources/static/index.html)

![image](https://github.com/wanted-preonboarding-team-m/02_geoRecommendEats/assets/57309311/d5034d8c-975a-4e2c-af30-059f4c486f56)

</details>

## 테스트

### ✅ 24/24 (2초 33ms)

![image](https://github.com/wanted-preonboarding-team-m/02_geoRecommendEats/assets/57309311/c8265e01-9e0d-417f-865b-408e7e672322)

단위 테스트로 각 계층을 분리했습니다.

## TIL 및 회고

* [하나의 객체를 여러 테이블로 분리한다면?](https://www.notion.so/mizuirohoshi7/ec334759b596410c871d9ea1a3ce47b5?pvs=4)
* [RestTemplate로 json을 객체로 자동 변환](https://www.notion.so/mizuirohoshi7/RestTemplate-json-9402434b740042498a7f748dfc5e78f5?pvs=4)
* [시큐리티의 비밀번호 검증은 언제 일어나는걸까](https://www.notion.so/mizuirohoshi7/2651c325e42b44bab3241164c956f45a?pvs=4)
* [CaseCade로 인해 JPA Delete가 작동 안한 이유](https://www.notion.so/mizuirohoshi7/CaseCade-JPA-Delete-b96e3dfd88e94526a4c8813e6854520e?pvs=4)
* [JPA 사용시 N+1 문제를 해결하는 방법](https://www.notion.so/mizuirohoshi7/JPA-N-1-29737eba15de455aa5b2425a6cdde786?pvs=4)
* [JPQL vs QueryDsl](https://www.notion.so/mizuirohoshi7/JPQL-vs-QueryDsl-e7dbfdc69af24dffbd712a6a71bc5973?pvs=4)
* [CSV 파일 업로드 ](https://www.notion.so/mizuirohoshi7/CSV-b89d73846ab44e8e82feb352521d850d?pvs=4)
* [CSV 데이터를 서버 내 메모리 저장 ](https://www.notion.so/mizuirohoshi7/CSV-7eb427f6fee247e3bae82085bcedeb47?pvs=4)
