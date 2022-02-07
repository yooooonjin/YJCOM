package project.domain.entity;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface HelpBoardEntityRepository extends JpaRepository<HelpBoardEntity, Long> {

	List<HelpBoardEntity> findByMember_email(String email);

}
