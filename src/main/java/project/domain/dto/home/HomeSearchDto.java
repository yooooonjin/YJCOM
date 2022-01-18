package project.domain.dto.home;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class HomeSearchDto {

	private String location;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate checkin;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate checkout;
	private String guestsStr;
	
}
