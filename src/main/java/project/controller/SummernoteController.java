package project.controller;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.springframework.core.io.ClassPathResource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.JsonObject;

@Controller
public class SummernoteController {
	
	@ResponseBody
	@PostMapping(value="/uploadSummernoteImageFile", produces = "application/json; charset=utf8")
	public String uploadSummernoteImageFile(@RequestParam("file") MultipartFile multipartFile)  {
		JsonObject jsonObject = new JsonObject();
		
		String url="/image/summernote/";
        ClassPathResource cpr=new ClassPathResource("static"+url);
        
        String originalFileName = multipartFile.getOriginalFilename();	//오리지날 파일명
		String extension = originalFileName.substring(originalFileName.lastIndexOf("."));//파일 확장자
		String savedFileName = UUID.randomUUID() + extension;//저장될 파일 명
			
		try {
			File fileRoot = cpr.getFile();
			File targetFile = new File(fileRoot, savedFileName);
			multipartFile.transferTo(targetFile);
			
			
			jsonObject.addProperty("url", url+savedFileName); 
			// contextroot + resources + 저장할 내부 폴더명
			jsonObject.addProperty("responseCode", "success");
				
		} catch (IOException e) {
			jsonObject.addProperty("responseCode", "error");
			e.printStackTrace();
		}
		
		return jsonObject.toString();
	}
		
	/*
	1. 오리지널 파일명과 확장자를 분리합니다.
	2. UUID.randomUUID() 함수를 통해 고유의 이름으로 바꾸어 저장합니다.(추후 중복 이름 때문에 파일이 덮어 써지거나 저장이 안 되는 걸 방지하기 위해서)
	3. inputstream으로 파일을 저장합니다.
	4. 저장한 이미지를 보여줘야 하므로 json형태로 url : 파일 이름으로 jsonobject에 저장합니다.
	5. 파일 저장을 시도하는데 애러가 나면 파일을 삭제합니다.
	6. Responsebody를 통해 json 형태의 string이 성공적으로 return 되면 ajax의 success부분이 실행되고, editor.insertimage를 통해 서머 노트에 저장된 이미지가 불러와집니다.
	*/

}
