package project.domain.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum HomeOption {
	
	wifi("무선인터넷"),
	tv("TV"),
	washer("세탁기"),
	parking("주차장"),
	pool("수영장"),
	kitchen("주방");
	
	final String homeOption;

}
