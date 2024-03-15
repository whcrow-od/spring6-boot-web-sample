package ua.od.whcrow.samples.spring6.boot_web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import ua.od.whcrow.samples.spring6.boot_web._commons.security.GeneralAccess;
import ua.od.whcrow.samples.spring6.boot_web._commons.security.GeneralAccessType;

@GeneralAccess(GeneralAccessType.ANYONE)
@Controller
class MainController {
	
	@GetMapping("/")
	public String hp() {
		return "hp";
	}
	
	@GetMapping("/login")
	public String login() {
		return "login";
	}
	
	@GetMapping("/access-denied")
	public String accessDenied() {
		return "access-denied";
	}
	
}
