package project.domain.dto.helpBoard;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class HelpBoardListDto {
	
	private String category;
	private String subject;
	private String email;
	private LocalDateTime createdDate;

}
