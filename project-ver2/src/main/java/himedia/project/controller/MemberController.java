package himedia.project.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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

	@GetMapping("/search")
	public String search(Long id, String name, Model model) {
		Optional<Member> member;
		if (name == "") {
			if (id == null) 
				return "redirect:/";
			else 
				member = service.findId(id);
		} else 
			member = service.findName(name);

		if (!member.isEmpty()) {
			model.addAttribute("id", member.get().getId());
			model.addAttribute("name", member.get().getName());
		} else {
			System.out.println("일치하는 값이 없다");
		}
		return "/member/search";
	}

}
