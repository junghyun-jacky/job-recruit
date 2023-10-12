package wanted.recruit.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import wanted.recruit.domain.Company;
import wanted.recruit.domain.Recruit;
import wanted.recruit.repository.RecruitRepository;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
class RecruitServiceTest {

    @PersistenceContext EntityManager em;
    @Autowired RecruitService recruitService;
    @Autowired RecruitRepository recruitRepository;

    @Test
    public void createRecruitment() throws Exception {

        Company company = new Company();
        company.setName("wanted");
        em.persist(company);

        Long orderId = recruitService.register(company.getId(), "한국", "서울", "백엔드 개발자", "500만원", "Java", "원티드에서 백엔드 개발자 채용을 진행합니다.");

        Recruit getRecruit = recruitRepository.findRecruit(orderId);

        assertEquals("wanted 채용 공고 업로드 성공", "wanted", getRecruit.getCompany().getName());
    }

    @Test
    public void updateRecruitment() throws Exception {

    }

    @Test
    public void deleteRecruitment() throws Exception {

    }

}