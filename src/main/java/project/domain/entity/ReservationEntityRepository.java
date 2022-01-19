package project.domain.entity;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationEntityRepository extends JpaRepository<ReservationEntity, Long> {

	List<ReservationEntity> findAllByMember_email(String email);


	Page<ReservationEntity> findAllByMember_email(String email, Pageable pageable);

}
