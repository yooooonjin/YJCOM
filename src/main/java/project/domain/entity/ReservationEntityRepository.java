package project.domain.entity;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationEntityRepository extends JpaRepository<ReservationEntity, Long> {



	Page<ReservationEntity> findAllByMember_email(String email, Pageable pageable);


	List<ReservationEntity> findByHome_hno(long hno);


	List<ReservationEntity> findByMember_email(String username);




}
