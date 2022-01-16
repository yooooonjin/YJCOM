package project.domain.entity;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationEntityRepository extends JpaRepository<ReservationEntity, Long> {

	List<ReservationEntity> findAllByMember_email(String email);

}
