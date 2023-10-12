package wanted.recruit.repository;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import wanted.recruit.domain.Recruit;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class RecruitRepository {

    private final EntityManager em;

    // 공고 등록
    public void register(Recruit recruit) {
        em.persist(recruit);
    }

    // 공고 수정
    public void update(Recruit recruitParam) {

        Recruit findRecruit = em.find(Recruit.class, recruitParam.getId());
        findRecruit.setCompany(recruitParam.getCompany());
        findRecruit.setCountry(recruitParam.getCountry());
        findRecruit.setRegion(recruitParam.getRegion());
        findRecruit.setPosition(recruitParam.getPosition());
        findRecruit.setReward(recruitParam.getReward());
        findRecruit.setTech(recruitParam.getTech());
        findRecruit.setContent(recruitParam.getContent());
    }

    // 공고 삭제
    public void delete(Long id) {
        Recruit recruit = em.find(Recruit.class, id);
        if (recruit != null) {
            em.remove(recruit);
        }
    }

    // 공고 조회
    public Recruit findRecruit(Long id) {
        return em.find(Recruit.class, id);
    }

    // 모든 공고 조회
    public List<Recruit> findAllRecruit() {
        return em.createQuery("select r from Recruit r", Recruit.class)
                .getResultList();
    }

    // 공고 검색
    public List<Recruit> findRecruitByName(String name) {
        return em.createQuery("select r from Recruit r where r.company = :name", Recruit.class)
                .setParameter("name", name)
                .getResultList();
    }
}
