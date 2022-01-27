package project.util;

import java.io.File;
import java.io.IOException;

import org.springframework.core.io.ClassPathResource;
import org.springframework.web.multipart.MultipartFile;

public class FileUtils {
	
	private static String tempPath;
	private static String fileName;

	/**
	 * 
	 * @param fileImg : MultipartFile 객체
	 * @param path : 이미지 저장 위치 "static" 하위경로만
	 * @return 이미지 URL주소 리턴 or 파일사이즈가 클때 "fileSize" or 이미지파일이 아니면 "contentType" 
	 */
	public static String tempImgUpload(MultipartFile fileImg , String path) {
		tempPath=path;
		//파일사이즈
		long fileSize=fileImg.getSize();
		if(fileSize > 1024*1024*2)return "fileSize-error";
		String contentType=fileImg.getContentType();
		if(!contentType.contains("image"))return "contentType-error";
		///////////////////////////////////////////////////////////////
		
		fileName=fileImg.getOriginalFilename();
		//path+fileName : 이미지 url 주소 -->리턴하면되는 값
		//파일업로드 처리--bin폴더에//////////////////
		ClassPathResource cpr=new ClassPathResource("static"+path);
		try {
			File tempLocation=cpr.getFile();
			//for(File file : tempLocation.listFiles())file.delete(); //기존업로드파일 삭제
			
			fileImg.transferTo(new File(tempLocation, fileName));
			//temp폴더에 파일업로드 완료!
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return path+fileName;
		//정상적으로 처리되면 이미지의 temp 웹경로 /images/goods/temp/img_1.jpg
	}

	
	//   /images/goods/temp/파일 --> /images/goods/파일
	public static void moveTempToDest(String destPath) {
		
		ClassPathResource tempCpr=new ClassPathResource("static"+tempPath);//이전경로소스위치
		ClassPathResource destCpr=new ClassPathResource("static"+destPath);//새로운경로소스위치
		try {
			File tempFile=new File(tempCpr.getFile(), fileName);
			tempFile.renameTo(new File(destCpr.getFile(), fileName));
			tempFile.delete();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}
