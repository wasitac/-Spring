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
