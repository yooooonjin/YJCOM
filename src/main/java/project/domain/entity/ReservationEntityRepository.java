package project.domain.entity;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.util.Streamable;

public interface ReservationEntityRepository extends JpaRepository<ReservationEntity, Long> {



	Page<ReservationEntity> findAllByMember_email(String email, Pageable pageable);


	List<ReservationEntity> findByHome_hno(long hno);




}
