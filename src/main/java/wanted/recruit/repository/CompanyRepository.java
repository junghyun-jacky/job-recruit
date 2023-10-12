package wanted.recruit.repository;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import wanted.recruit.domain.Company;

@Repository
@RequiredArgsConstructor
public class CompanyRepository {

    private final EntityManager em;

    public void save(Company company) {
        em.persist(company);
    }

    public Company findOne(Long id) {
        return em.find(Company.class, id);
    }

}
