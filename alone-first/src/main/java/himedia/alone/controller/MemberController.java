package himedia.alone.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import himedia.alone.domain.Member;

@Controller
public class MemberController {
//	@GetMapping("/member/join-form")
//	public String join(@RequestParam Map<String, Object> member,
////			@RequestParam("userName") String userName, 
////			@RequestParam("userAge") Integer userAge, 
//			Model model) {
//		model.addAttribute("userName", member.get("userName"));
//		model.addAttribute("userAge", member.get("userAge"));
//		
//		return "/member/join";
//	}
	@GetMapping("/member/join-form")
	public String join(@ModelAttribute Member member, Model model) {
		// return type이 model이니까 stream처럼 연결해서 쓸 수 있다
		model.addAttribute("userName", member.getUserName())
		.addAttribute("userAge", member.getUserAge());

		return "/member/join";
	}
}
