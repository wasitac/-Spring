package himedia.alone.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ItemController {
	@PostMapping("/cart")
	public String cartPage() {
		return "cart";
	}
}
