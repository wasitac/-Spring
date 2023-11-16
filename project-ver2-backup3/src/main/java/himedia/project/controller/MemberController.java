package himedia.project.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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

//	@GetMapping("/search")
//	public String search(@RequestParam(required = false) Long id,
//			@RequestParam("name") String name, Model model) {
//
//		Member member = new Member();
//
//		if(service.findId(id).isPresent()) 
//			member = service.findId(id).get();
//		else if (service.findName(name).isPresent())
//			member = service.findName(name).get();

//		model.addAttribute("member", member);
//
//		return "/member/search";
//	}

//	@GetMapping("/search")
//	public String search(@RequestParam(required = false) Long id,
//			@RequestParam("name") String name, Model model) {
//		
//		Optional<Member> member = Optional.empty();
//		
//		if(id != null) 
//			member = service.findId(id);
//		else if (!name.isEmpty())
//			member = service.findName(name);
//		
//		if(member.isPresent())
//			model.addAttribute("member", member.get());
////		else 
////			//빈 객체 넘기기
////			model.addAttribute("member", new Member());
//
//		return "/member/search";
//	}

	// [방법2]
	// [리플렉션 기법] Reflection
	// view -> controller
	// 자동으로 setter 실행 -> 데이터 자동 처리
	// controller -> view
	// 자동으로 getter 실행
	// [parameter에 사용되는 @ModelAttribute("member")와 Model의 차이
	// @ModelAttribute : view -> controller로 데이터 넘어올 때
	// Model : controller -> view로 데이터 넘길 때

	// @ModelAttribute는 view로 넘길 때 타입명을 사용해서 넘긴다.
	// ModelAttribute와 Model은 자동으로 binding 되어있다
	// [문제] @ ModelAttribute : binding의 의미 - 문서주석 참고
	// 뷰로 부터 받은 데이터를 자동으로 빈의 이름이 같은 필드와 같은 타입으로 변환하고 setter메서드를 사용하여 넣어줌.
//	public String memberSearch(@ModelAttribute("hello") Member member , 
//	public String memberSearch(@ModelAttribute(binding = false, name="hello") Member member , 

//	@GetMapping("/search")
//	public String memberSearch(@ModelAttribute(binding = false, name = "hello") Member member, 
//			Model model) {
//		System.out.println("[memberSearch]");
//		System.out.println("member >> " + member);
//		System.out.println("model.gA >> " + model.getAttribute("hello"));
//		System.out.println("model.gA id >> " + ((Member)model.getAttribute("hello")).getId());
//		System.out.println("model.gA name >> " + ((Member)model.getAttribute("hello")).getName());
//		
//		Member m = member;
//		m.setId(1L);
//		m.setName("홍길동");
//		model.addAttribute(m);
//		
//		return "member/search";
//	}
	
//위 코드 정리
//	@GetMapping("/search")
//	public String memberSearch(@ModelAttribute("member") Member member, Model model) {
//		
//		Member searchMember = new Member();
//		if (member.getId() != null) {
//			searchMember = service.findId(member.getId()).get();
//		}
//		else if(!member.getName().isEmpty()) {
//			searchMember = service.findName(member.getName()).get();
//		}
//
//		model.addAttribute("member", searchMember);
//			
//		return "member/search";
//	}	
	
	// [문제] 없는 회원의 아이디나 이름 검색 시 발생되는 error 처리
	@GetMapping("/search")
	public String memberSearch(@ModelAttribute Member member, Model model) {
		
		Optional<Member> searchMember = Optional.empty();
		if (member.getId() != null) {
			searchMember = service.findId(member.getId());
		}
		else if(!member.getName().isEmpty()) {
			searchMember = service.findName(member.getName());
		}
		if (searchMember.isPresent()) 
			model.addAttribute("member", searchMember.get());
		else {
			model.addAttribute("member", new Member());
		}
			
		return "member/search";
	}
}
