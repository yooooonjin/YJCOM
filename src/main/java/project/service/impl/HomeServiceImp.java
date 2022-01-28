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
import project.domain.dto.home.HomeListDto;
import project.domain.dto.home.HomeSearchDto;
import project.domain.dto.home.homeRegDto;
import project.domain.entity.HomeEntity;
import project.domain.entity.HomeEntityRepository;
import project.domain.entity.HomeOption;
import project.domain.entity.HomeReviewEntity;
import project.domain.entity.HomeReviewEntityRepository;
import project.domain.entity.MemberEntityRepository;
import project.domain.entity.MemberRole;
import project.domain.entity.ReservationEntity;
import project.domain.entity.ReservationEntityRepository;
import project.security.dto.SecurityDto;
import project.service.home.HomeService;
import project.util.FileUtils;
import project.util.PageInfo;

@Log4j2
@RequiredArgsConstructor
@Service
public class HomeServiceImp implements HomeService {
	
	final HomeEntityRepository homeRepository;
	final MemberEntityRepository memberRepository;
	final HomeReviewEntityRepository reviewRepository;
	final ReservationEntityRepository reservationRepository;
	
	//집 등록
	@Transactional
	@Override
	public String homeReg(homeRegDto regDto, SecurityDto securityDto) {
		HomeEntity entity= HomeEntity.builder().homeName(regDto.getHomeName()).homeAddress(regDto.getHomeAddress())
			.homePrice(regDto.getHomePrice()).maximumNumber(regDto.getMaximumNumber()).bedNumber(regDto.getBedNumber())
			.bedroomNumber(regDto.getBedroomNumber()).bathroomNumber(regDto.getBathroomNumber())
			.homeType(regDto.getHomeType())
			//.homeOptionSet(null)
			.homeIntro(regDto.getHomeIntro())
			.homePhoto(regDto.getHomePhoto()).useable("y")
			.member(memberRepository.findById(securityDto.getUsername()).get())
			.build();
		
		HomeOption[] homeOption = regDto.getHomeOption();
		
		for(int i=0;i<homeOption.length;i++) {
			entity.addHomeOption(homeOption[i]);
		}
		
		homeRepository.save(entity);
		
		memberRepository.findById(securityDto.getUsername()).get().addRole(MemberRole.HOST);
		
		return "redirect:/home/list";
	}
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
	@Override
	public String homesearch(Model model, int page, HomeSearchDto searchDto) {
		
		/////////////////////////////////////////////
		//지정 위치중 자치구만 가져오기
		String result= searchDto.getLocation();
		
		String[] locationArr= searchDto.getLocation().split("[ ]");
		log.info("자치구={}",locationArr[1]);
		String location=locationArr[1];
		/////////////////////////////////////////////
		//문자열로 넘어온 게스트 수를 정수로 변환
		String guestsStr=searchDto.getGuestsStr();
		String guestStr_=guestsStr.replaceAll("[^0-9]", "");
		if(guestStr_==null || guestStr_.equals(""))guestStr_="0";
		int guests = Integer.parseInt(guestStr_);
		/////////////////////////////////////////////
		//해당 지역 & 최소인원 조건에 맞는 집
		List<Long> hno = homeRepository.selectHome(location,guests);
		
		/////////////////////////////////////////////
		LocalDate checkin= searchDto.getCheckin();
		LocalDate checkout=searchDto.getCheckout();
		
		//TODO DTO에 담아서 전달
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
		
		model.addAttribute("homes", homeEntity);
		model.addAttribute("searchDto", searchDto);
		return "home/homes";
		
	}
	
	//집 디테일 페이지
		@Override
		public String homeDetail(Model model, long hno) {
			
			HomeListDto homeDetail= homeRepository.findById(hno).map(HomeListDto::new).orElseThrow();
			model.addAttribute("homeDetail", homeDetail);
			
		
			//예약되어있는 날짜를 reservationDates Set컬렉션에 담기
			List<ReservationEntity> reservationEntity= reservationRepository.findByHome_hno(hno);
			
			Set<LocalDate> reservationDates = new HashSet<LocalDate>();
			
			reservationEntity.forEach(e->{
				LocalDate checkin= e.getCheckIn();
				LocalDate checkout= e.getCheckOut();
				
				reservationDates.add(checkin);
				reservationDates.add(checkout);
				
				checkin.datesUntil(checkout).forEach(date-> reservationDates.add(date));
			});
			
			System.out.println(reservationDates);
			model.addAttribute("reservationDates", reservationDates);

			
			//후기//TODO dto에 담기
			List<HomeReviewEntity> homeReview = reviewRepository.findAllByHome_hno(hno);
			model.addAttribute("homeReviews", homeReview);
			return "home/home-detail";
		}
		
		//메인이미지 temp 폴더 업로드
		@Override
		public String tempMainImgUpload(MultipartFile fileImg) {
			String path="/image/temp/";
			//FileUtils.tempImgUpload(fileImg, tempPath);
			
			String fileName="main-img.jpg";
			//path+fileName : 이미지 url 주소 -->리턴하면되는 값
			//파일업로드 처리--bin폴더에//////////////////
			ClassPathResource cpr=new ClassPathResource("static"+path);
			try {
				File tempLocation=cpr.getFile();
				fileImg.transferTo(new File(tempLocation, fileName));
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			return path+fileName;
			
			//return tempPath+fileImg.getOriginalFilename();
		}
		@Override
		public String tempSubImgUpload(MultipartFile fileImg, String imageName) {
			String path="/image/temp/";
			//FileUtils.tempImgUpload(fileImg, tempPath);
			
			String fileName=imageName+".jpg";
			//path+fileName : 이미지 url 주소 -->리턴하면되는 값
			//파일업로드 처리--bin폴더에//////////////////
			ClassPathResource cpr=new ClassPathResource("static"+path);
			try {
				File tempLocation=cpr.getFile();
				fileImg.transferTo(new File(tempLocation, fileName));
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			return path+fileName;
		}

}
