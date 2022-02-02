package project.domain.entity;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HomeImageEntityRepository extends JpaRepository<HomeImageEntity, Long> {

	List<HomeImageEntity> findByHome_hno(long hno);

}
