package project.domain.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum HomeType {
	apartment("아파트"),
	house("주택"),
	outhouse("별채");
	
	final String homeType;

}
