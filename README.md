# wanted-pre-onboarding-backend
회사는 채용공고를 생성하고 사용자는 이에 지원하는, 기업의 채용을 위한 웹 서비스입니다.

## 💡 요구사항 분석 및 구현과정
### 1. 기업 채용 공고 등록
- DB에 공고 데이터 등록
```
{
  "companyId" : 1,
  "country" : "한국",
  "region" : "서울",
  "position" : "백엔드 개발자",
  "reward" : 500만원,
  "tech" : "Java",
  "content" : "원티드랩에서 백엔드 주니어 개발자를 채용합니다. 자격요건은.."
}
```
### 2. 기업 채용 공고 수정
- 회사 id 이외 모두 수정 가능
### 3. 기업 채용 공고 삭제
- DB에서 공고 데이터 삭제
### 4. 기업 채용 공고 상세보기
- 특정 공고 데이터 조회
```
{
  "recruitId": 채용공고_id,
  "companyName":"원티드랩",
  "country":"한국",
  "region":"서울",
  "position":"백엔드 주니어 개발자",
  "reward":1500000,
  "tech":"Python",
  "content": "원티드랩에서 백엔드 주니어 개발자를 채용합니다. 자격요건은..",
}

```

### 5. 기업 채용 공고 목록보기
- DB에 등록된 모든 공고 데이터 조회
```
[
	{
		"recruitId": 채용공고_id,
	  "companyName":"원티드랩",
	  "country":"한국",
	  "region":"서울",
	  "position":"백엔드 주니어 개발자",
	  "rewward":1500000,
	  "tech":"Python"
	},
	{
		"recruitId": 채용공고_id,
	  "companyName":"네이버",
	  "country":"한국",
	  "region":"판교",
	  "position":"Django 백엔드 개발자",
	  "reward":1000000,
	  "tech":"Django"
	},
  ...
]
```

## 🔩 application.properties
```
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.datasource.url=jdbc:mysql://localhost:3306/...
spring.datasource.username=...
spring.datasource.password=...
spring.jpa.show-sql=true
spring.jpa.hibernate.ddl-auto=update
spring.jpa.properties.hibernate.format_sql=true
spring.sql.init.mode=always
spring.jpa.defer-datasource-initialization=true

```

## ⚙️ 단위 테스트
Junit5를 사용해 공고 CRUD에 대한 단위 테스트 진행
<details>
<summary>공고 등록</summary>
<div markdown="1">

```
    @Test
    public void createRecruitment() throws Exception {

        Company company = new Company();
        company.setName("wanted");
        em.persist(company);

        Long recruitId = recruitService.register(company.getId(), "한국", "서울", "백엔드 개발자", "500만원", "Java", "원티드에서 백엔드 개발자 채용을 진행합니다.");

        Recruit getRecruit = recruitRepository.findRecruit(recruitId);

        assertNotNull(getRecruit); // 조회한 공고가 null이 아닌지 확인

        assertEquals("wanted 채용 공고 업로드 성공", "wanted", getRecruit.getCompany().getName());
    }
```

</div>
</details>
<details>
<summary>공고 수정</summary>
<div markdown="1">

```
    @Test
    public void updateRecruitment() throws Exception {
        Company company = new Company();
        company.setName("wantedLab");
        em.persist(company);

        Long recruitId = recruitService.register(company.getId(), "한국", "서울", "백엔드 개발자", "500만원", "Java", "원티드에서 백엔드 개발자 채용을 진행합니다.");

        recruitService.updateRecruit(recruitId, "한국", "서울", "인프라 엔지니어", "300만원", "tech", "원티드에서 인프라 엔지니어 채용을 진행합니다.");

        Recruit updatedRecruit = recruitRepository.findRecruit(recruitId);

        assertEquals("Updated Position", "인프라 엔지니어", updatedRecruit.getPosition());
    }
```

</div>
</details>

<details>
<summary>공고 삭제</summary>
<div markdown="1">

```
    @Test
    public void deleteRecruitment() throws Exception {
        Company company = new Company();
        company.setName("Dwanted");
        em.persist(company);

        Long recruitId = recruitService.register(company.getId(), "중국", "베이징", "백엔드 개발자", "100만원", "Java", "Dwanted에서 백엔드 개발자 채용을 진행합니다. ...");

        recruitService.deleteRecruit(recruitId);

        Recruit deletedRecruit = recruitRepository.findRecruit(recruitId);
        assertNull("삭제된 채용 공고는 조회되지 않아야 합니다.", deletedRecruit);
    }
```

</div>
</details>

<details>
<summary>공고 상세보기</summary>
<div markdown="1">

```
    @Test
    public void testGetRecruitDetails() {
        // 공고 등록
        Company company = new Company();
        company.setName("wanted");
        em.persist(company);

        Long recruitId = recruitService.register(company.getId(), "한국", "서울", "백엔드 개발자", "500만원", "Java", "원티드에서 백엔드 개발자 채용을 진행합니다.");

        // 공고 상세보기 테스트
        Recruit recruit = recruitService.getRecruitDetails(recruitId);

        assertNotNull(recruit);
        assertEquals("한국", recruit.getCountry());
        assertEquals("서울", recruit.getRegion());
        assertEquals("백엔드 개발자", recruit.getPosition());
        assertEquals("500만원", recruit.getReward());
        assertEquals("Java", recruit.getTech());
        assertEquals("원티드에서 백엔드 개발자 채용을 진행합니다.", recruit.getContent());
        assertEquals("wanted", recruit.getCompany().getName());
    }
```

</div>
</details>

<details>
<summary>공고 목록보기</summary>
<div markdown="1">

```
    @Test
    public void testGetAllRecruits() {
        // 공고 등록
        Company company1 = new Company();
        company1.setName("wanted");
        em.persist(company1);

        Company company2 = new Company();
        company2.setName("companyA");
        em.persist(company2);

        recruitService.register(company1.getId(), "한국", "서울", "백엔드 개발자", "500만원", "Java", "원티드에서 백엔드 개발자 채용을 진행합니다.");
        recruitService.register(company2.getId(), "미국", "뉴욕", "프론트엔드 개발자", "700만원", "JavaScript", "companyA에서 프론트엔드 개발자 채용을 진행합니다.");

        // 공고 목록 보기 테스트 - 더미 데이터 포함 3개의 공고
        assertEquals(3, recruitService.getAllRecruits().size());
    }
```

</div>
</details>
<br>
   

## 🛠️ 사용 기술
- Java 17
- SpringBoot 3.1.4
- Spring Data JPA
- Gradle
- Junit5
- MySQL 5.7.43   
<br>
   

## 🔤 커밋 컨벤션
| 태그 이름 | 설명                                 |
| ---------- | -------------------------------------------------------------------- |
| [Feat]   | 새로운 기능 구현                           |
| [Fix]   | 버그, 오류 수정                           |
| [Hotfix]  | issue나 QA에서 급한 버그 수정                    |
| [Docs]   | 문서 수정                              |
| [Test]   | 테스트 코드 추가 및 업데이트                     |
| [Chore]  | 코드 수정, 내부 파일 수정                      |
| [Refactor] | 전면 수정                              |
| [Add]   | Feat 이외의 부수적인 코드 추가, 라이브러리 추가, 새로운 파일 생성 시 |
| [Style]   | 코드 포맷 변경, 세미 콜론 누락, 코드 수정이 없는 경우     |
| [Design]  | CSS 등 사용자 UI 디자인 변경                    |
| [Rename]  | 파일 이름 변경 시 사용                        |
| [Move]   | 프로젝트 내 파일이나 코드의 이동                   |
<br>
