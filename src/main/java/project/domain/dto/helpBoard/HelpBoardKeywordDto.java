package project.domain.dto.helpBoard;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class HelpBoardKeywordDto {

	private String condition;
	private String keyword;
}
