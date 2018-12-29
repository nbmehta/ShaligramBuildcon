/**
 * Copyright 2015 - Harsh Panchal <panchal.harsh18@gmail.com>
 */
package hp.bootmgr.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ErrorPageProvider {
	
	@RequestMapping("/403")
	private String get403ErrorPage() {
		return "error_pages/403";
	}
	
	@RequestMapping("/404")
	private String get404ErrorPage() {
		return "error_pages/404";
	}
	
}
