package project.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import lombok.RequiredArgsConstructor;
import project.domain.dto.MemberUpdateDto;
import project.domain.dto.home.HomeListDto;
import project.domain.dto.home.HomeReserveDto;
import project.domain.entity.HomeEntity;
import project.domain.entity.HomeEntityRepository;
import project.domain.entity.HomeReviewEntity;
import project.domain.entity.MemberEntity;
import project.domain.entity.MemberEntityRepository;
import project.domain.entity.ReservationEntity;
import project.domain.entity.ReservationEntityRepository;
import project.security.dto.SecurityDto;
import project.service.member.MemberService;

@RequiredArgsConstructor
@Service
public class MemberServiceImpl implements MemberService {

	private final MemberEntityRepository memberRepository;
	private final HomeEntityRepository homeRepository;
	private final ReservationEntityRepository reservationRepository;
	
	//프로필 정보
	@Override
	public String memberInfo(Model model, SecurityDto securityDto) {
		//회원정보 데이터
		MemberEntity entity= memberRepository.findById(securityDto.getUsername()).get();
		model.addAttribute("member",entity);
		
		//내가 등록한 집 데이터
		List<HomeEntity> homeEntity = homeRepository.findAllByMember_email(entity.getEmail());
		//dto로 변환
		List<HomeListDto> hostHome= homeEntity.stream().map(HomeListDto::new).collect(Collectors.toList());
		
		model.addAttribute("hostHome",hostHome);
		
		
		//예약한 집 데이터
		List<ReservationEntity> reservationEntity = reservationRepository.findAllByMember_email(entity.getEmail());
		
		reservationEntity.forEach(e->{
			HomeListDto reservedHome =homeRepository.findById(e.getHome().getHno()).map(HomeListDto::new).orElseThrow();
			model.addAttribute("reservedHome",reservedHome);
		});
		
		
		
		
		return "member/member-info";
	}
	//회원정보 수정
	@Transactional
	@Override
	public void memberUpdate(MemberUpdateDto updateDto ,Model model) {
		
		memberRepository.findById(updateDto.getEmail()).map(entity->entity.updateNameAndPhonenumberAndAddress(updateDto)).get();
				
	}
	//숙소예약
	//Dto에서 Entity로 변환할지 결정
	@Override
	public String homeReserve(long hno, SecurityDto securityDto, HomeReserveDto reserveDto) {
		ReservationEntity entity = ReservationEntity.builder()
		.checkIn(reserveDto.getCheckIn()).checkOut(reserveDto.getCheckOut()).guests(reserveDto.getGuests())
		.totalPrice(reserveDto.getTotalPrice()).reserveStatus("ask")
		.member(memberRepository.findById(securityDto.getUsername()).get())
		.home(homeRepository.findById(hno).get())
		.build();
		
		reservationRepository.save(entity);
		
		return "redirect:/member/info";
	}

}
