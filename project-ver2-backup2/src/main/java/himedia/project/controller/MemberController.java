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
// 둘 다 입력했을 때는 id로 검색.
//	@GetMapping("/search")
////	public String search(Long id, String name, Model model) {
//		//@RequestParam을 생략했더니 자동으로 required=false 가 됐다
//		// 원래 이게 맞다
//	public String search(@RequestParam(name="id", required=false) Long id, String name, Model model) {
//		System.out.println(id);
//		Optional<Member> member;
//		if (id == null) {
//			if (name == "")
//				return "redirect:/";
//			else 
//				member = service.findName(name);
//		} else 
//			member = service.findId(id);
//
//		if (!member.isEmpty()) {
//			
//		//model.addAttribute("member", member.get());로 
//		//하나만 보내고 뷰에서 member로 둘 다 사용 할 수도 있음. 이방법이 프로퍼티 사용할 수 있어서 더 깔끔한듯		
//			model.addAttribute("id", member.get().getId());
//			model.addAttribute("name", member.get().getName());
//		} else {
//			System.out.println("일치하는 값이 없다");
//		}
//		return "/member/search";
//	}

	@GetMapping("/search")
	// 둘 다 같은 의미. 하지만 보통 생략하고 쓰겠지
//	public String search(@RequestParam(value = "id") Long id) {
//	public String search(@RequestParam(name = "id") Long id) {
//	public String search(@RequestParam() Long id, Model model) {
// 입력하지 않고 요청할 때 Long이 빈 문자열을 받는데, 자동으로 null로 바꿔주지 않는다.
// required = false 처리를 해줘야함. 또는 기본값을 설정해 줄 수 있다.
	public String search(@RequestParam(name = "id", required = false /* defaultValue = "0" */) Long id,
			@RequestParam("name") String name, Model model) {
//		Optional<Member> member = service.findId(id);
//		Optional<Member> member = service.findName(name);
//		Optional<Member> member;
//		if (id == null) {
////			if(name.equals(""))
//			if (name.isEmpty())
//				return "/member/search";
//			member = service.findName(name).get();
//		} else
//			member = service.findId(id).get();
//		
//		if (member != null)
//			model.addAttribute("id", member.getId());
//			model.addAttribute("name", member.getName());
		
		Member member = new Member();
		if(id != null) 
			member = service.findId(id).get();
		else if (!name.isEmpty())
			member = service.findName(name).get();
		System.out.println(member);
		model.addAttribute("member", member);

		return "/member/search";
	}

}
