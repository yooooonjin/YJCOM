package project.domain.entity;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface HomeEntityRepository extends JpaRepository<HomeEntity, Long> {


	List<HomeEntity> findAllByMember_email(String email);

	@Query("SELECT h.hno FROM HomeEntity h WHERE h.homeAddress like %:location% AND h.maximumNumber >= :guests")
	List<Long> selectHome(@Param("location") String location, @Param("guests") int guests);
	

}
