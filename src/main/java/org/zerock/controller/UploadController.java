package org.zerock.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.zerock.domain.AttachFileDTO;

import lombok.extern.log4j.Log4j;
import net.coobird.thumbnailator.Thumbnailator;

@Controller
@Log4j
public class UploadController {
	
	/*   파일 업로드시 고려해야 할 문제    */ 
	// 1. 동일한 이름으로 파일이 업로드 되었을 때 기존 파일이 사라지는 문제
	// 2. 이미지 파일의 경우에는 원본 파일의 용량이 큰 경우 섬네일 이미지를 생성해야 하는 문제
	// 3. 이미지 파일과 일반 파일을 구분해서 다운로드 혹은 페이지에서 조회하도록 처리하는 문제
	// 4. 첨부파일 공격에 대비하기 위한 업로드 파일의 확장자 제한
	
	
	
	// form 방식을 이용한 파일 업로드
	@GetMapping("/uploadForm")
	public void uploadForm() {
		
		log.info("upload form");
	}
	
	
	@PostMapping("/uploadFormAction")
	public void uploadFormPost(MultipartFile[] uploadFile, Model model) {
		
		String uploadFolder = "C:\\upload";
		
		for(MultipartFile multipartFile : uploadFile) {
			
			log.info("---------------------------------");
			log.info("Upload File Name : " + multipartFile.getOriginalFilename());
			log.info("Upload File Size : " + multipartFile.getSize());
			
			File saveFile = new File(uploadFolder, multipartFile.getOriginalFilename());  // File을 이용해서 업로드 폴더와 넘어온 파일을 저장한다.
			
			try {
				multipartFile.transferTo(saveFile);
			} catch (Exception e) {
				log.error(e.getMessage());
			}
			
		}
	}
	
	
	
	

	
	// ajax 방식을 이용한 파일 업로드
	@GetMapping("/uploadAjax")
	public void uploadAjax() {
		
		log.info("upload Ajax");
	}
	
	@PostMapping(value = "/uploadAjaxAction", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public ResponseEntity<List<AttachFileDTO>> uploadAjaxPost(MultipartFile[] uploadFile) {
		
		log.info("update ajax post ......... " );
		
		List<AttachFileDTO> list = new ArrayList<AttachFileDTO>();
		String uploadFolder = "C:\\upload";
		
		String uploadFolderPath = getFolder();
		// 년/월/일 폴더의 생성   make folder -----------
		File uploadPath = new File(uploadFolder, uploadFolderPath);
		log.info("upload path : " + uploadPath);
		
		if(uploadPath.exists() == false) {
			uploadPath.mkdirs();
		}
		// make yyyy/MM/dd folder
		
		for(MultipartFile multipartFile : uploadFile) {
			
			log.info("==========================================");
			log.info("Upload File Name : " + multipartFile.getOriginalFilename());
			log.info("Upload File Size : " + multipartFile.getSize());
			
			AttachFileDTO attachDTO = new AttachFileDTO();
			
			String uploadFileName = multipartFile.getOriginalFilename();
			
			// IE has file path
			uploadFileName = uploadFileName.substring(uploadFileName.lastIndexOf("\\") + 1);   // IE의 경우에서는 전체 파일 경로가 전소오디므로 마지막 \\를 기준으로 잘라낸 문자열이 실제 파일 이름이 된다.
			log.info("only file name : " + uploadFileName);
			attachDTO.setFileName(uploadFileName);   //  원래 파일명을 여기에 담는다
			
			// 중복 방지를 위한  UUID를 적용
			UUID uuid = UUID.randomUUID();
			
			uploadFileName = uuid.toString() + "_" + uploadFileName;
			
			
			try {
//				File saveFile = new File(uploadFolder, uploadFileName);    폴더 생성하기 전 방식
				File saveFile = new File(uploadPath, uploadFileName);
				
				multipartFile.transferTo(saveFile);

				attachDTO.setUuid(uuid.toString());
				attachDTO.setUploadPath(uploadFolderPath);
				
				
				// check image type file
				if(checkImageType(saveFile)) {
					
					attachDTO.setImage(true); //  여기에 들어오면 이미지가 맞다는 증거임
					
					FileOutputStream thumbnail = new FileOutputStream(new File(uploadPath, "s_" + uploadFileName));
					
					Thumbnailator.createThumbnail(multipartFile.getInputStream(), thumbnail, 100, 100);
					
					thumbnail.close();
				}
				
				// add to List
				list.add(attachDTO);
				
			} catch (Exception e) {
				log.error(e.getMessage());
			}
			
		}
		
		// ResponseEntity를 반환하는 형태로 수정하고, JSON 데이터를 반환하도록 변경된다
		return new ResponseEntity<List<AttachFileDTO>>(list, HttpStatus.OK);
		
	}
	
	
	
	// 년/월/일 폴더의 생성
	// 오늘 날짜의 경로를 문자열로 생성한다.
	private String getFolder() {
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		Date date = new Date();
		
		String str = sdf.format(date);
		
		return str.replace("-", File.separator);
		// 생성된 날짜 포멧에서 -을 뺀다
	}

	
	// 이미지 파일의 판단
	// 특정한 파일이 이미지 타입인지를 검사하는 메서드
	private boolean checkImageType(File file) {
		
		try {
			String contentType = Files.probeContentType(file.toPath());
			
			return contentType.startsWith("image");
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	
	
	
}


















