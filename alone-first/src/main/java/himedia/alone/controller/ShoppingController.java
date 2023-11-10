package himedia.alone.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ShoppingController {
	
	@GetMapping("/food")
	public String foodPage() {
		return "food";
	}
	
}
