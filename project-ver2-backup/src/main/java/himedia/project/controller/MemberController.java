package himedia.project.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import himedia.project.domain.Member;
import himedia.project.domain.MemberForm;
import himedia.project.service.MemberService;

@Controller
// 클래스 레벨 매핑
@RequestMapping("/member")
public class MemberController {
	// MemberController는 MemberService에 의존한다
	// [방법 1] 직접 bean(객체) 생성한다.
	// [방법 2] [Dependency Injection] 의존성 주입
		// 생성자 주입(constructor injection) : 권장
		// 필드 주입 (field injection)
		// 세터 주입 (setter injection)
		
//	private final MemberService service = new MemberService();
	
	// - 생성자 자동주입
	private final MemberService service;
	
	// 생성자 주입 시 생성자가 한개 뿐이라면, 어노테이션 생략 가능
//	@Autowired
	public MemberController(MemberService service) {
		this.service = service;
	}
	
//	// - 필드 주입
//	// 필드주입과 세터주입은 final로 사용불가능. 객체 생성단계에 초기화 하지 않기 때문에.
//	@Autowired
//	private MemberService service;
	
	@GetMapping("/new")
	public String form() {
		return "member/new-form";
	}
		
	@PostMapping("/new")
	public String postNew(MemberForm form) {
		Member member = new Member();
		member.setName(form.getName());
		service.join(member);
		// 새로고침 시 post요청 반복을 막아준다.
		return "redirect:/";
	}
	
	@GetMapping("/list")
	public String memberList(Model model) {
		List<Member> members = service.findMember();
		model.addAttribute("members", members);
		return "member/member-list";
	}

}
