package project.domain.entity;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface HomeReviewEntityRepository extends JpaRepository<HomeReviewEntity, Long>{

	List<HomeReviewEntity> findAllByMember_email(String email);

	List<HomeReviewEntity> findAllByHome_hno(long hno);

}
