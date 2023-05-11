package com.dongjin.board.mainController;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class MainController {

	@GetMapping(value= {"/", "/main"})
	public String main() throws Exception{

		return "main/main";
	}
	
	@PostMapping(value="/")
	public String redirectMain() {
		return "redirect:/";
	}
	
	
}
