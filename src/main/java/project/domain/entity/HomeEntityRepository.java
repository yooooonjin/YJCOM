package project.domain.entity;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface HomeEntityRepository extends JpaRepository<HomeEntity, Long> {


	List<HomeEntity> findAllByMember_email(String email);


}
