package project.service.impl;

import java.util.List;
import java.util.Vector;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import lombok.RequiredArgsConstructor;
import project.domain.dto.home.HomeListDto;
import project.domain.entity.HomeEntity;
import project.domain.entity.HomeEntityRepository;
import project.domain.entity.HomeImageEntityRepository;
import project.domain.entity.HomeReviewEntity;
import project.domain.entity.HomeReviewEntityRepository;
import project.domain.entity.MemberEntityRepository;
import project.domain.entity.ReservationEntity;
import project.domain.entity.ReservationEntityRepository;
import project.security.dto.SecurityDto;
import project.service.HostService;

@RequiredArgsConstructor
@Service
public class HostServiceImpl implements HostService {
	
	private final MemberEntityRepository memberRepository;
	private final HomeEntityRepository homeRepository;
	private final ReservationEntityRepository reservationRepository;
	private final HomeReviewEntityRepository homeReviewRepository;
	private final HomeImageEntityRepository homeImageRepository;
	
	
	//내가 등록한 집
	@Override
	public String hostPage(Model model, SecurityDto securityDto, int page) {
		
		//내가 등록한 집 데이터
		Pageable pageable =PageRequest.of(page-1, 4, Direction.DESC, "hno");
		Page<HomeEntity> homeEntity = homeRepository.findAllByMember_email(securityDto.getUsername(),pageable);
		
		//dto로 변환
		List<HomeListDto> hostHomes= homeEntity.stream().map(HomeListDto::new).collect(Collectors.toList());
		
		model.addAttribute("pageTot",homeEntity.getTotalPages());
		model.addAttribute("hostHomes",hostHomes);
		
		/////////////////////////////////////////////////////////////////////////////////
		
		return "host/host-info";
	}

	//내가 등록한 집 예약 정보
	@Override
	public String reservationPage(Model model, SecurityDto securityDto, int page) {
		
		//내가 등록한 집 데이터
		List<HomeEntity> homeEntity = homeRepository.findAllByMember_email(securityDto.getUsername());
		
		List<Long> resno= new Vector<>();
		homeEntity.forEach(e->{
			reservationRepository.findByHome_hno(e.getHno()).forEach(r->{
				resno.add(r.getResNo());
			});
		});
		
		Pageable pageable= PageRequest.of(page-1, 4, Direction.DESC, "createdDate");
		List<ReservationEntity> result= reservationRepository.findAllById(resno);
		
		
		//dto로 변환
		//List<HomeListDto> hostHomes= homeEntity.stream().map(HomeListDto::new).collect(Collectors.toList());
		
		model.addAttribute("reservations",result);
		
		return "host/host-reservation";
	}

	//내가 등록한 숙소 삭제
	@Override
	public void homeDelete(long hno) {
		
		homeReviewRepository.findByHome_hno(hno).forEach(e->homeReviewRepository.deleteById(e.getRno()));
		reservationRepository.findByHome_hno(hno).forEach(e->reservationRepository.deleteById(e.getResNo()));
		homeImageRepository.findByHome_hno(hno).forEach(e->homeImageRepository.deleteById(e.getIno()));
		homeRepository.deleteById(hno);
	}

}
