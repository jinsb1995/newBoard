package org.zerock.controller;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.zerock.domain.SampleVO;
import org.zerock.domain.Ticket;

import lombok.extern.log4j.Log4j;

@RestController
@RequestMapping("/sample")
@Log4j
public class SampleController {
	
	@GetMapping(value = "/getText", produces = "text/plain; charset=UTF-8")
	public String getText() {
		
		System.out.println("MIME TYPE : " + MediaType.TEXT_PLAIN_VALUE);
		
		return "안녕하세요";
	}
												//
	@GetMapping(value = "/getSample", produces = {MediaType.APPLICATION_JSON_UTF8_VALUE,
													MediaType.APPLICATION_XML_VALUE})
	public SampleVO getSample() {
		
		return new SampleVO(112, "스타", "로드");
	}

	@GetMapping(value = "/getSample2")
	public SampleVO getSample2() {
		
		return new SampleVO(113, "로켓", "라쿤");
	}
	
	//여러 데이터를 한 번에 전송하기 위해 Array나 List, Map타입의 객체들을 전송하는 경우도 있다.
	@GetMapping(value = "/getList")
	public List<SampleVO> getList() {
		
		// intStream은 내부적으로 1부터 10미만까지의 루프를 처리하면서 SampleVO 객체를 만들어서 List<SampleVO>로 만들어 낸다.
		return IntStream.range(1, 10).mapToObj(i -> new SampleVO(i, i + "First", i + "Last")).collect(Collectors.toList());
	}
	
	@GetMapping(value = "/getMap")
	public Map<String, SampleVO> getMap() {
		
		Map<String, SampleVO> map = new HashMap<>();
		map.put("First", new SampleVO(111, "그루트", "주니어"));
		
		return map;
	}
	
	@GetMapping(value = "/check", params = { "height", "weight" })
	public ResponseEntity<SampleVO> check(Double height, Double weight) {
		
		SampleVO vo = new SampleVO(0, "" + height, "" + weight);
		
		ResponseEntity<SampleVO> result = null;
		
		if(height < 150) {
			result = ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(vo);
		}else {
			result = ResponseEntity.status(HttpStatus.OK).body(vo);
		}
		
		return result;
	}
	
	
	// @PathVariable url상에 경로의 일부를 파라미터로 사용함
	@GetMapping("/product/{cat}/{pid}")
	public String[] getPath(@PathVariable("cat") String cat, @PathVariable("pid") String pid) {
		
		return new String[] { "category : " + cat, "productId : " + pid };
	}
	
	
	// @RequestBody - request된 내용(body)을 이용해서 해당 파라미터의 타입으로 변환을 요구합니다.
	//  			   
	@PostMapping("/ticket")
	public Ticket convert(@RequestBody Ticket ticket) {
		System.out.println("convert.......... ticket" + ticket);
		
		return ticket;
	}
	
	
	
	





}












