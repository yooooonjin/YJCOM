package project.domain.dto.helpBoard;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class HelpBoardWriteDto {
	
	private long bno;
	private String category;
	private String subject;
	private String content;
	private String email;

}
