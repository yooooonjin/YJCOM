package project.util;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class PageInfo {
	
	int pageTotal; //페이지 총 개수
	int from; //페이지 시작 번호
	int to; //페이지 끝 번호
	int page;//페이지 번호
	int pageGroup;//한 화면에 표현할 페이지 번호 개수
	
	/**
	 * 
	 * @param pageTotal : 총 페이지 수
 	 * @param page : 현실 페이지 시작 번호 1~
	 * @param pageGroup : 한 화면 표현할 페이지 번호 개수
	 */

	public PageInfo(int pageTotal, int page, int pageGroup) {
		this.pageGroup=pageGroup;
		this.pageTotal=pageTotal;
		this.page=page;
		//{1,2,3,4, 5} : pageGroupNo=1 : 1/5(0), 2/5(0), 3/5(0), 4/5(0), 5/5(1)
		//{6,7,8,9,10} : pageGroupNo=2 : 6/5(1), 7/5(1), 8/5(1), 9/5(1), 10/5(2)
		int pageGroupNo=page/pageGroup;
		if(page%pageGroup>0) pageGroupNo++;
		
		//페이지 마지막 번호 계산 : pageGroupNo * pageGroup
		this.to=pageGroupNo*pageGroup; //마지막번호를 페이지 번호 개수의 배수만큼씩 증가
		this.from=to-pageGroup+1; //마지막 번호에서 페이지 개수만큼 빼고 +1
		
		if(to>pageTotal)to=pageTotal;
	}

}
