package himedia.project.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import himedia.project.domain.Member;
import himedia.project.domain.MemberForm;
import himedia.project.service.MemberService;

@Controller
@RequestMapping("/member")
public class MemberController {

	// 생성자 주입
	private final MemberService service;

//	@Autowired
	public MemberController(MemberService service) {
		this.service = service;
	}

	@GetMapping("/new")
	public String form() {
		return "member/new-form";
	}

	@PostMapping("/new")
	public String postNew(MemberForm form) {
		Member member = new Member();
		member.setName(form.getName());
		service.join(member);
		return "redirect:/";
	}

	@GetMapping("/list")
	public String memberList(Model model) {
		List<Member> members = service.findMember();
		model.addAttribute("members", members);
		return "member/member-list";
	}

// [문제] 목록에 없는 회원을 검색했을 때 오류가 발생하지 않게 수정하기,

	@GetMapping("/search")
	public String search(@RequestParam(required = false) Long id,
			@RequestParam("name") String name, Model model) {

		Member member = new Member();

		if(service.findId(id).isPresent()) 
			member = service.findId(id).get();
		else if (service.findName(name).isPresent())
			member = service.findName(name).get();
		model.addAttribute("member", member);

		return "/member/search";
	}

	// [방법2]
	// [리플렉션 기법] Reflection
	//  view -> controller
	// 자동으로 setter 실행 -> 데이터 자동 처리
	// controller -> view
	// 자동으로 getter 실행
	// [parameter에 사용되는 @ModelAttribute("member")와 Model의 차이
	// @ModelAttribute : view -> controller로 데이터 넘어올 때
	// Model 		   : controller -> view로 데이터 넘길 때
	
	//@ModelAttribute는 view로 넘길 때 타입명을 사용해서 넘긴다.
	// ModelAttribute와 Model은 자동으로 binding 되어있다
	// [문제] @ ModelAttribute : binding의 의미 - 문서주석 참고
	
//	public String memberSearch(@ModelAttribute("hello") Member member , 
//	public String memberSearch(@ModelAttribute(binding = false, name="hello") Member member , 
	
//	public String memberSearch(@ModelAttribute Member member , 
//			Model model) {
//	
//		return "member/search";
//	}
}
