package himedia.project.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import himedia.project.domain.Member;
import himedia.project.domain.MemberForm;
import himedia.project.repository.MemberRepository;
import himedia.project.repository.MemoryMemberRepository;

@Controller
public class MemberController {
	
	private final MemberRepository repository = new MemoryMemberRepository();

	@GetMapping("/member/new")
	public String form() {
		return "member/new-form";
	}
		
	//원래 post는 requestparam으로 데이터를 받지 않음. 
	//domain/Member로 도메인(타입)을 만들어 사용
	// member의 field가 private인데 setter를 자동으로 실행해 접근한다. =>프로퍼티가 실행된것
	// 그러려면 setter의 이름이 set필드명 이어야함. 자동생성할때 그 이름.
	@PostMapping("/member/new")
//	public String postForm(@RequestParam String name) {
	public String postForm(MemberForm form) {
		System.out.println("/member/new 요청 : " + form.getName());
		Member member = new Member();
		member.setName(form.getName());
		
		// [service] code...
		
		// [repository] code...
		member = repository.save(member);
		
		return "home";
	}
	
	@GetMapping("/member/join")
	public String join(@RequestParam String name) {
		System.out.println("이름 >> " + name);
		return "home";
	}
	
	/*
	 * [문제] 
	 * 요청 URL	    : /member/join
	 * 응답 string  : 감사
	 * 이름 콘솔에 출력
	 */
	@PostMapping("/member/join")
	@ResponseBody
	public String postJoin(@RequestBody String name) {
		System.out.println("이름 >> " + name);
		return "감사";
	}
	

}
