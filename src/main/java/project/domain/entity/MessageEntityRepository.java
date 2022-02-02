package project.domain.entity;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageEntityRepository extends JpaRepository<MessageEntity, Long> {



	List<MessageEntity> findBySender_emailOrReceiver_email(String member, String member2);

	List<MessageEntity>  findBySender_emailAndReceiver_email(String sender, String member);



}
