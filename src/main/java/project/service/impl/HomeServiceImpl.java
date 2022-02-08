package project.service.impl;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import project.domain.dto.home.HomeImgRegDto;
import project.domain.dto.home.HomeListDto;
import project.domain.dto.home.HomeRegDto;
import project.domain.dto.home.HomeReviewDto;
import project.domain.dto.home.HomeSearchDto;
import project.domain.entity.HomeEntity;
import project.domain.entity.HomeEntityRepository;
import project.domain.entity.HomeImageEntity;
import project.domain.entity.HomeImageEntityRepository;
import project.domain.entity.HomeOption;
import project.domain.entity.HomeReviewEntity;
import project.domain.entity.HomeReviewEntityRepository;
import project.domain.entity.MemberEntityRepository;
import project.domain.entity.MemberRole;
import project.domain.entity.ReservationEntity;
import project.domain.entity.ReservationEntityRepository;
import project.security.dto.SecurityDto;
import project.service.HomeService;
import project.util.PageInfo;

@Log4j2
@RequiredArgsConstructor
@Service
public class HomeServiceImpl implements HomeService {
	
	final HomeEntityRepository homeRepository;
	final MemberEntityRepository memberRepository;
	final HomeReviewEntityRepository reviewRepository;
	final ReservationEntityRepository reservationRepository;
	final HomeImageEntityRepository homeImageRepository;
	
	//집 전체 불러오기 //페이징처리
	@Override
	public String homeList(Model model,int page) {
		
		//한 페이지당 집 3개씩
		Pageable pageable =PageRequest.of(page-1, 3, Direction.DESC, "hno");
		Page<HomeEntity> homesEntity =homeRepository.findAll(pageable);
		List<HomeListDto> homes =homesEntity.stream().map(HomeListDto::new).collect(Collectors.toList());
		model.addAttribute("homes",homes);
		
		//페이지 3개씩
		PageInfo paging=new PageInfo(homesEntity.getTotalPages(),page,3);
		model.addAttribute("paging",paging);
		return "home/homes";
	}
	
	//조건에 맞는 집 불러오기
	@Transactional
	@Override
	public String homesearch(Model model, int page, HomeSearchDto searchDto) {
		
		//지정 위치 중 자치구만 가져오기
		String[] locationArr= searchDto.getLocation().split("[ ]");
		log.info("자치구={}",locationArr[1]);
		String location=locationArr[1];
		
		//문자열로 넘어온 게스트 수를 정수로 변환
		String guestsStr=searchDto.getGuestsStr();
		String guestsStr_=guestsStr.replaceAll("[^0-9]", "");
		if(guestsStr_==null || guestsStr_.equals(""))guestsStr_="0";
		int guests = Integer.parseInt(guestsStr_);
		
		//해당 지역 & 최소인원 조건에 맞는 집
		List<Long> hno = homeRepository.selectHome(location,guests);
		
		//요청 기간중 예약 가능한 집
		LocalDate checkin= searchDto.getCheckin();
		LocalDate checkout=searchDto.getCheckout();
		
		List<HomeEntity> homeEntity=null;
		if(checkin!=null && checkout!=null) { //체크인, 체크아웃 날짜가 지정되었을 경우
			homeEntity= homeRepository.findAllById(hno).stream() 
				.filter(e->e.isreservations( checkin, checkout)) //검색된 집 정보중에서 기간중 예약 가능한 집만 필터링
				.collect(Collectors.toList());
		}else { //체크인, 체크아웃 날짜가 지정되지 않았을 경우
			LocalDate today=LocalDate.now();
			homeEntity= homeRepository.findAllById(hno).stream()
					.filter(e->e.isreservations( today.plusDays(1), today.minusDays(1)))
					.collect(Collectors.toList()); //일정 관계없이 지역,인원으로만 필터링된 집
		}
		
		List<HomeListDto> homes= homeEntity.stream().map(HomeListDto::new).collect(Collectors.toList());
		
		model.addAttribute("homes", homes);
		model.addAttribute("searchDto", searchDto);
		return "home/homes";
		
	}
	
	//집 디테일 페이지
		@Override
		public String homeDetail(Model model, long hno, HomeSearchDto searchDto) {
			
			System.out.println("체크인 : " +searchDto.getCheckin());
			
			//집 정보 가져오기
			HomeListDto homeDetails= homeRepository.findById(hno).map(HomeListDto::new).orElseThrow();
			model.addAttribute("homeDetails", homeDetails);
			
		
			//예약되어있는 날짜를 reservationDates Set컬렉션에 담기
			List<ReservationEntity> reservationEntity= reservationRepository.findByHome_hno(hno);
			
			Set<LocalDate> reservationDates = new HashSet<LocalDate>();
			
			reservationEntity.forEach(e->{
				LocalDate checkin= e.getCheckIn();
				LocalDate checkout= e.getCheckOut();
				checkin.datesUntil(checkout).forEach(date-> reservationDates.add(date));
			});
			model.addAttribute("reservationDates", reservationDates);

			
			//집에 등록된 후기
			List<HomeReviewDto> homeReview = reviewRepository.findAllByHome_hno(hno).stream().map(HomeReviewDto::new).collect(Collectors.toList());
			if(homeReview.isEmpty()) model.addAttribute("none", "후기가 존재하지 않습니다.");			
			model.addAttribute("homeReviews", homeReview);
			
			return "home/home-detail";
		}
		
		//집이미지 temp 폴더 업로드
		@Override
		public String tempImgUpload(MultipartFile fileImg, String imageName) {
			String path="/image/temp/";
			
			String fileName=imageName+".jpg";
			ClassPathResource cpr=new ClassPathResource("static"+path);
			try {
				File tempLocation=cpr.getFile();
				fileImg.transferTo(new File(tempLocation, fileName));
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			return path+fileName;
		}
		
		//집 등록
		@Transactional
		@Override
		public String homeReg(HomeRegDto regDto, SecurityDto securityDto,HomeImgRegDto imgRegDto) {
			HomeEntity entity= HomeEntity.builder().homeName(regDto.getHomeName()).homeAddress(regDto.getHomeAddress())
				.homePrice(regDto.getHomePrice()).maximumNumber(regDto.getMaximumNumber()).bedNumber(regDto.getBedNumber())
				.bedroomNumber(regDto.getBedroomNumber()).bathroomNumber(regDto.getBathroomNumber())
				.homeType(regDto.getHomeType())
				//.homeOptionSet(null)
				.homeIntro(regDto.getHomeIntro())
				.useable("y")
				.member(memberRepository.findById(securityDto.getUsername()).get())
				.build();
			
			HomeOption[] homeOption = regDto.getHomeOption();
			
			for(int i=0;i<homeOption.length;i++) {
				entity.addHomeOption(homeOption[i]);
			}
			homeRepository.save(entity);
			memberRepository.findById(securityDto.getUsername()).get().addRole(MemberRole.HOST);
			
			///////////////////////////////////////////////////////////////////////
			ClassPathResource tempCpr=new ClassPathResource("static/image/temp/");//이전경로소스위치
			ClassPathResource destCpr=new ClassPathResource("static/image/home-img");//새로운경로소스위치
			try {
				File tempLocation=tempCpr.getFile();
				for(File file : tempLocation.listFiles()) {
					
					File tempFile=new File(tempCpr.getFile(), file.getName());
					tempFile.renameTo(new File(destCpr.getFile(), entity.getHno()+"_"+file.getName()));
					
					tempFile.delete();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			///////////////////////////////////////////////////////////////////////
			
				
			HomeImageEntity mainEntity = HomeImageEntity.builder().fileOrgName(imgRegDto.getMainImg().getOriginalFilename())
							.fileNewName(entity.getHno()+"_main-img.jpg").fileSize(imgRegDto.getMainImg().getSize())
							.orderNo(1).home(homeRepository.findById(entity.getHno()).get()).build();
			
			HomeImageEntity sub1Entity = HomeImageEntity.builder().fileOrgName(imgRegDto.getSub1Img().getOriginalFilename())
							.fileNewName(entity.getHno()+"_sub1-img.jpg").fileSize(imgRegDto.getSub1Img().getSize())
							.orderNo(2).home(homeRepository.findById(entity.getHno()).get()).build();
			
			HomeImageEntity sub2Entity = HomeImageEntity.builder().fileOrgName(imgRegDto.getSub2Img().getOriginalFilename())
							.fileNewName(entity.getHno()+"_sub2-img.jpg").fileSize(imgRegDto.getSub2Img().getSize())
							.orderNo(3).home(homeRepository.findById(entity.getHno()).get()).build();
			
			HomeImageEntity sub3Entity = HomeImageEntity.builder().fileOrgName(imgRegDto.getSub3Img().getOriginalFilename())
							.fileNewName(entity.getHno()+"_sub3-img.jpg").fileSize(imgRegDto.getSub3Img().getSize())
							.orderNo(4).home(homeRepository.findById(entity.getHno()).get()).build();
			
			HomeImageEntity sub4Entity = HomeImageEntity.builder().fileOrgName(imgRegDto.getSub4Img().getOriginalFilename())
							.fileNewName(entity.getHno()+"_sub4-img.jpg").fileSize(imgRegDto.getSub4Img().getSize())
							.orderNo(5).home(homeRepository.findById(entity.getHno()).get()).build();
			
			homeImageRepository.save(mainEntity);
			homeImageRepository.save(sub1Entity);
			homeImageRepository.save(sub2Entity);
			homeImageRepository.save(sub3Entity);
			homeImageRepository.save(sub4Entity);
			
			///////////////////////////////////////////////////////////////////////
			
			return "redirect:/home/list";
		}


}
