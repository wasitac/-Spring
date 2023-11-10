package himedia.spring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * [view 저장 위치에 따른 웹 개발 종류]
 * 	  구분					파일 위치		처리
 * 1. static content		static			
 * 2. dynamic content		templates		ViewResolver
 * 3. API					x				HttpMessageConverter
 * 
 * 1. static content
 *  - 서버에서 파일을 그대로 웹 브라우저에 전달
 *  - 파일 위치 : resource/static
 *  
 * 2. dynamic content
 *  - 서버에서 html을 동적변경하여 웹 브라우저에 전달
 *  - 파일 위치 : resource/templates/{ViewName}.html
 *  - MVC		: Model(data), View(html), Controller(handler method)
 *  
 * 3. API
 *  - JSON 포맷으로 데이터 전송
 *  - 객체를 넘길 수 있음 : HttpMessageConverter가 JSON으로 변환
 *  
 */
@Controller
public class BasicController {
	
	/**
	 * 2-1.MVC
	 *  : handler level mapping
	 */
	@GetMapping("/first-mvc")
	public String firstMvc() {
		return "first"; //논리적인 뷰
	}
	
	@GetMapping("/bestseller")
	public String bestSeller() {
		return "./book/best-seller";
	}
	
	/**
	 * 2-2. MVC
	 * : view template을 통해 데이터 표현
	 * : template engine - thymeleaf
	 */
	@GetMapping("/second-mvc")
	public String secondMvc(Model model) {
		model.addAttribute("name", "홍길동");
		model.addAttribute("price", 1500);
		return "second";
	}
	
	/**
	 * 2-3. MVC
	 * : 쿼리 파라미터(query parameter) 처리
	 */
	@GetMapping("/third-mvc")
	public String thirdMvc(@RequestParam("fruit") String fruit, Model model) {
		model.addAttribute("fruit", fruit);	
		return "third"; // 응답 
	}
	
	/**
	 * [문제] http://localhost:8080/fruit?count=7
	 * 쿼리 파라미터로 받은 값을 quiz-fruit-count 뷰에 표현하시오.
	 */
	@GetMapping("fruit")
	//
//	public String fruitCount(@RequestParam("count") int count, Model model) {
//	public String fruitCount(@RequestParam int count, Model model) { //어노테이션의 매개변수가 매개변수명과 같으면, 매개변수 생략가능
	public String fruitCount(int count, Model model) { //어노테이션 자체도 생략가능. 근데 어노테이션은 적어주는게 좋다.
		model.addAttribute("count", count);
		return "quiz-fruit-count";
	}
	
	/**
	 * 3-1. API
	 * : String으로 전송
	 */
	@GetMapping("/api-one")
	@ResponseBody
	public String apiOne() {
		return "test";
	}
	
	/**
	 * 3-2. API
	 * : Json으로 전송
	 * 
	 * [문제] http://localhost:8080/api-two?name=박보검&age=32
	 * 
	 */


	@GetMapping("/api-two")
	@ResponseBody
	public Student apiTwo(@RequestParam String name, @RequestParam int age) {
		Student student = new Student();
		student.setName(name);
		student.setAge(age);
		return student;
	}
	
	class Student {
		private String name;
		private int age;
		public void setName(String name) {
			this.name = name;
		}
		public void setAge(int age) {
			this.age = age;
		}
		public String getName() {
			return name;
		}
		public int getAge() {
			return age;
		}
	}
}