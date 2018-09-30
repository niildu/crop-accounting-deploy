package com.cropaccounting.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cropaccounting.models.Signup;
import com.cropaccounting.repository.ReportDataRepository;

@Controller
public class DefaultController {
	
	@Autowired
	private ReportDataRepository repo;
	
	@GetMapping("/")
	public String home1() {
		return "index";
	}

	@GetMapping("/index")
	public String home() {
		return "index";
	}
	
	@GetMapping("/home")
	public String dashboard(Model model) {
		return "home";
	}

	@GetMapping("/admin")
	public String admin() {
		return "admin";
	}

	@RequestMapping("/signup")
	public String user(Model model) {
		model.addAttribute("signup", new Signup());
		return "signup/signup";
	}

	@GetMapping("/about")
	public String about() {
		return "about";
	}

	@GetMapping("/login")
	public String login() {
		return "Secure/login";
	}

	@GetMapping("/403")
	public String error403() {
		return "/errors/403";
	}
	
	@GetMapping("/reports/cropvsland")
	public String cropvsland(Model model) {
		return "/reports/cropvsland";
	}
	
	@GetMapping("/reports/laborcomparison")
	public String laborcomparison(Model model) {
		return "/reports/laborcomparison";
	}
	
	@GetMapping("/reports/landownership")
	public String landownership(Model model) {
		return "/reports/landownership";
	}
	
	@GetMapping("/reports/production")
	public String production(Model model) {
		return "/reports/production";
	}
}
