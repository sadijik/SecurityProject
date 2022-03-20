package com.example.TestProject.controller;

import com.example.TestProject.BlogQuestDTO.BlogDTO;
import com.example.TestProject.entity.Blog;
import com.example.TestProject.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Controller
public class ControllerMainUsers {


	private final AdminService adminService;

	@Autowired
	public ControllerMainUsers(AdminService adminService) {

		this.adminService = adminService;
	}

	@GetMapping("/")
	public String greeting() {
		return "greeting";
	}

	@GetMapping("/main")
	public String main(Model model) {
		Iterable<Blog> blogQuests = adminService.findAll();
		List<Blog> list = new ArrayList<>();
		blogQuests.forEach(list::add);
		List<BlogDTO> blogDTO = list.stream().map(BlogDTO::toBlog).collect(Collectors.toList());
		model.addAttribute("blog", blogDTO);
		return "main";
	}


}



