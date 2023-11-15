// 에러만 없애고 공부용으로 남겨놓은 파일
package himedia.project.controller;

import java.util.List;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import himedia.project.domain.Member;
import himedia.project.domain.MemberForm;
import himedia.project.service.MemberService;

//@Controller
// 클래스 레벨 매핑
@RequestMapping("/member")
public class MemberControllerEx {
	//repository 사이에 service 추가
//	private final MemberRepository repository = new MemoryMemberRepository();

	//에러나서
//	private final MemberService service = new MemberService();
	private final MemberService service = null;
	
	@GetMapping("/new")
	public String form() {
		return "member/new-form";
	}
		
	//원래 post는 RequestParam으로 데이터를 받지 않음. 
	//domain/Member로 도메인(타입)을 만들어 사용
	// member의 field가 private인데 setter를 자동으로 실행해 접근한다. =>프로퍼티가 실행된것
	// 그러려면 setter의 이름이 set필드명 이어야함. 자동생성할때 그 이름.
	@PostMapping("/new")
//	public String postNew(@RequestParam String name) {
	public String postNew(MemberForm form) {
		System.out.println("/member/new 요청 : " + form.getName());
		Member member = new Member();
		member.setName(form.getName());
		
		// [service] code...
		
		// [repository] code...
//		repository.save(member);
		service.join(member);
		
		//post요청에 응답을받고 난 후 새로고침을 했을 때 작업을 반복하는 일을 막기위해
		//get으로 재요청 -> 새로고침해도 get
		//post 후엔 redirect!
		//return "home";
		return "redirect:/";
	}
	
	@GetMapping("/join")
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
	@PostMapping("/join")
	@ResponseBody
	public String postJoin(@RequestBody String name) {
		System.out.println("이름 >> " + name);
		return "감사";
	}
	
	@GetMapping("/list")
	public String memberList(Model model) {
		// [service] code...
		List<Member> members = service.findMember();
		// [repository] code...
//		List<Member> members = repository.findAll();
		model.addAttribute("members", members);
		return "member/member-list";
	}

}
