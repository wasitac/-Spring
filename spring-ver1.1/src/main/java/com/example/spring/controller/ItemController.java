package com.example.spring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ItemController {
	@GetMapping("/")
	public String hello() {
		return "hello";
	}
	
	@GetMapping("/item-list")
	public String item() {
		return "item";
	}
	
	//뷰에 데이터를 보내줌
	@GetMapping("/item-view")
	public String itemView(Model model) {
		model.addAttribute("title", "새우깡");
		model.addAttribute("price", 1500);
		return "item-view";
	}
}
