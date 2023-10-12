package wanted.recruit.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
@Table(name = "recruitments")
public class Recruit {

    @Id @GeneratedValue
    @Column(name = "recruit_id")
    private Long id;

    private String country;

    private String region;

    private String position;

    private String reward;

    private String tech;

    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id")
    private Company company;


    /** 연관관계 메서드 **/
    public void setCompany(Company company) {
        this.company = company;
        company.getRecruits().add(this);
    }

    /** 생성 메서드 **/
    public static Recruit createRecruit(Company company, String country, String region, String position, String reward, String tech, String content) {
        Recruit recruit = new Recruit();
        recruit.setCompany(company);

        recruit.setCountry(country);
        recruit.setRegion(region);
        recruit.setPosition(position);
        recruit.setReward(reward);
        recruit.setTech(tech);
        recruit.setContent(content);

        return recruit;
    }
}
