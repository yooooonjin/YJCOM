package project.domain.dto.home;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.domain.entity.HomeEntity;
import project.domain.entity.HomeOption;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class HomeOptionDto {
	private HomeOption[] homeOption;

	public HomeOptionDto(HomeEntity entity) {
		this.homeOption=entity.getHomeOptionSet().toArray(new HomeOption[entity.getHomeOptionSet().size()]);
	}
	
	

}
