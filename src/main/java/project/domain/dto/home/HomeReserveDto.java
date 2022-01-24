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
public class HomeReserveDto {

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate checkIn;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate checkOut;
	private int days;
	private String guestsStr;
	private long totalPrice;
	
}
