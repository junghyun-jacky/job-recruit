package wanted.recruit.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wanted.recruit.domain.Company;
import wanted.recruit.domain.Recruit;
import wanted.recruit.repository.CompanyRepository;
import wanted.recruit.repository.RecruitRepository;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RecruitService {

    private final RecruitRepository recruitRepository;
    private final CompanyRepository companyRepository;

    // 공고 등록
    @Transactional
    public Long register(Long companyId, String country, String region, String position, String reward, String tech, String content) {

        // 엔티티 조회
        Company company = companyRepository.findOne(companyId);

        // 공고 생성
        Recruit recruit = Recruit.createRecruit(company, country, region, position, reward, tech, content);

        // 공고 저장
        recruitRepository.register(recruit);
        return recruit.getId();
    }

    // 공고 수정
    @Transactional
    public void updateRecruit(Long recruitId) {
        Recruit recruit = recruitRepository.findRecruit(recruitId);
        recruitRepository.update(recruit);
    }

    // 공고 삭제
    @Transactional
    public void deleteRecruit(Long recruitId) {
        Recruit recruit = recruitRepository.findRecruit(recruitId);
        recruitRepository.delete(recruit.getId());
    }
}
