package org.zerock.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.zerock.domain.BoardVO;
import org.zerock.domain.Criteria;
import org.zerock.domain.PageDTO;
import org.zerock.service.BoardService;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

// URL 분석된 내용들을 반영하는 메서드
/*
 *  servlet-context에 있는 <annotation-driven>에 의해 브라우저의 요청은 여기(*Controller)로 넘어오게 된다
 */

@Controller
@Log4j
@RequestMapping("/board/*")
@AllArgsConstructor
//java 설정에는   @componentScan을 이용
public class BoardController {
	
	private BoardService service;

	
//	전체 목록에 대한 처리와 테스트  
//	@GetMapping("/list")
//	public void list(Model model) {
//		
//		model.addAttribute("list", service.getList());
//	}
	
	
	@GetMapping("/list")
	public void list(Criteria cri, Model model) { 
		
//		String code = "003";
//		String code = "004";
//		model.addAttribute("role", code);
		
		System.out.println("cri ================== " + cri);
		model.addAttribute("list", service.getListWithPaging(cri));
//		model.addAttribute("pageMaker", new PageDTO(cri, 123));
		
		int total = service.getTotalCount(cri);
		
		System.out.println("total =========" + total);
		
		model.addAttribute("pageMaker", new PageDTO(cri, total));
		
		
	}
	
	
	
	@GetMapping("/register")
	public void register() {
		
	}

	@PostMapping("/register")
	public String register(BoardVO board, RedirectAttributes rttr) {
		service.register(board);
		
		rttr.addFlashAttribute("result", board.getBno());
		
		return "redirect:/board/list";
	}
	
	
	
	
	@GetMapping({"/get", "/modify"})
	public void get(@RequestParam("bno") Long bno, @ModelAttribute("cri") Criteria cri, Model model) {
		
		System.out.println("/get or modify " + bno);
		model.addAttribute("board", service.get(bno));
	}
	
	
	
	
	@PostMapping("/modify")
	public String modify(BoardVO board, @ModelAttribute("cri") Criteria cri, RedirectAttributes rttr) {
		
		System.out.println("/modify " + service.modify(board));
		if(service.modify(board)) {
			rttr.addFlashAttribute("result", "success");
		}
		
//		rttr.addAttribute("pageNum", cri.getPageNum());
//		rttr.addAttribute("amount", cri.getAmount());
//		rttr.addAttribute("type", cri.getType());
//		rttr.addAttribute("keyword", cri.getKeyword());
//		위에 요소들이   아래에 있는 cri.getListLink()로 들어가서 코드의 줄이 줄어든다.
		
		return "redirect:/board/list" + cri.getListLink();
	}
	
	
	
	@PostMapping("/remove")
	public String remove(@RequestParam("bno") Long bno, @ModelAttribute("cri") Criteria cri, RedirectAttributes rttr, BoardVO board) {
		
		
		System.out.println("/remove... " + service.remove(bno));
		if(service.remove(bno)) {
			rttr.addFlashAttribute("result", "success");
		}
		
//		rttr.addAttribute("pageNum", cri.getPageNum());
//		rttr.addAttribute("amount", cri.getAmount());
//		rttr.addAttribute("type", cri.getType());
//		rttr.addAttribute("keyword", cri.getKeyword());
		
//		UriComponentsBuilder로 생성괸 URL은 화면에서도 유용하게 사용될 수 있는데, 주로 Javascript를 사용할 수 없는 상황에서 링크를 처리해야 하는 상황에서 사용된다.
		return "redirect:/board/list" + cri.getListLink();
	}
	
	
	
	
	
	
}

