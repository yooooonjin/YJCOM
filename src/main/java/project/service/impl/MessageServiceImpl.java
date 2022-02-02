package project.service.impl;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import lombok.RequiredArgsConstructor;
import project.domain.entity.HomeEntityRepository;
import project.domain.entity.HomeReviewEntityRepository;
import project.domain.entity.MemberEntity;
import project.domain.entity.MemberEntityRepository;
import project.domain.entity.MessageEntity;
import project.domain.entity.MessageEntityRepository;
import project.domain.entity.ReservationEntityRepository;
import project.security.dto.SecurityDto;
import project.service.message.MessageService;

@RequiredArgsConstructor
@Service
public class MessageServiceImpl implements MessageService {
	
	private final MemberEntityRepository memberRepository;
	private final MessageEntityRepository messageRepository;
	
	
	//메세지 페이지
	@Override
	public String messagePage(SecurityDto securityDto, Model model) {
		String member=securityDto.getUsername();
		
		//user 아이디로 작성된 모든 메세지 데이터 가져오기(sender / receiver)
		List<MessageEntity> messageEntity= messageRepository.findBySender_emailOrReceiver_email(member,member);
		
		//user와 메세지를 주고 받은 모든 target
		Set<MemberEntity> target=new HashSet<MemberEntity>();
		
		
		messageEntity.forEach(e->{
			if(member.equals(e.getSender().getEmail())) { //user가 발신자이면
				target.add(e.getReceiver()); //조회된 수신자 target컬렉션에 담기
			}else if(member.equals(e.getReceiver().getEmail())) { //user가 수신자이면
				target.add(e.getSender()); //조회된 발신자 target컬렉션에 담기
			}
		});
		
		model.addAttribute("targets", target);
		
		/////////////////////////////////////////////
		
		//읽지않은 메세지가 있는 target set컬렉션에 담기
		Set<String> targetUnread=new HashSet<>();
		target.forEach(e->{
			messageRepository.findBySender_emailAndReceiver_email(e.getEmail(), member).forEach(r->{
				if(r.getOpendate()==null) {
					targetUnread.add(e.getEmail());
				}
			});
		});
	
		model.addAttribute("unread", targetUnread);
	
		return "message/message";
	}
	
	
	////메세지 확인
	@Transactional
	@Override
	public String messageDetail(SecurityDto securityDto, Model model, String targetEmail) {
		
		String member=securityDto.getUsername();
		
		//user 아이디로 작성된 모든 메세지 데이터 가져오기(sender / receiver)
		List<MessageEntity> messageEntity= messageRepository.findBySender_emailOrReceiver_email(member,member);
		
		//상대방 정보 가져오기
		MemberEntity target= memberRepository.findById(targetEmail).get();
		
		//user 아이디로 작성된 모든 메세지 중 target 아이디와 주고 받은 메세지 가져오기
		List<MessageEntity> targetMessages= messageEntity.stream().filter(e->e.isConnectTarget(target)).collect(Collectors.toList());
		
		model.addAttribute("targetName", target);
		model.addAttribute("targetMessages", targetMessages);
		
		/////////opendate 현재 시각////////////
		targetMessages.forEach(e->{
			if(e.getReceiver().getEmail().equals(member) && e.getOpendate()==null) {
				e.addOpendate(LocalDateTime.now());
			}
		});
		
		return "message/message-detail";
	}
	
	
	//메세지 보냈을 때 페이지 리로딩안됨
	//메세지 디테일 페이지에서 메세지 보내기
	@Override
	public void messageDetailWrite(SecurityDto securityDto, Model model, String targetName, String message) {
	
		MemberEntity receiver= memberRepository.findById(targetName).get();
		
		MessageEntity messageEntity=MessageEntity.builder()
									.sender(memberRepository.findById(securityDto.getUsername()).get())
									.receiver(receiver)
									.message(message)
									.build();
		
		messageRepository.save(messageEntity);		
	
	
	}

}
