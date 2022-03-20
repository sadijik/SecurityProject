package com.example.TestProject.controller;

import com.example.TestProject.BlogQuestDTO.BlogDTO;
import com.example.TestProject.entity.Blog;
import com.example.TestProject.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
public class ConrollerAdmin {
	private final AdminService adminService;

	@Autowired
	public ConrollerAdmin(AdminService adminService) {
		this.adminService = adminService;
	}

	@GetMapping("/admin")
	public String adminPage(Model model) {
		Iterable<Blog> blogQuests = adminService.findAll();
		List<Blog> list = new ArrayList<>();
		blogQuests.forEach(list::add);
		List<BlogDTO> blogDTO = list.stream().map(BlogDTO::toBlog).collect(Collectors.toList());
		model.addAttribute("blogAdmin", blogDTO);
		return "admin_blogs";
	}

	@GetMapping("/admin/add")
	public String blogAdd() {
		return "admin_blogs-add";
	}

	@PostMapping("/admin/add")
	public String blogPostAdd(@RequestParam String topic, @RequestParam String text) {
		Blog blog = new Blog(topic, text);

		adminService.saveQuest(blog);
		return "redirect:/admin";
	}


	@GetMapping("/admin/{id}")
	public String blogDetails(@PathVariable(value = "id") Long id, Model model) throws UsernameNotFoundException {
		if (adminService.findBlogId(id).isEmpty()) {
			return "redirect:/admin";
		}
		Optional<Blog> blogQuest = adminService.findBlogId(id);
		ArrayList<Blog> list = new ArrayList<>();
		blogQuest.ifPresent(list::add);
		List<BlogDTO> blogDTO = list.stream().map(BlogDTO::toBlog).collect(Collectors.toList());
		model.addAttribute("blogDetail", blogDTO);
		return "admin_blogs_details";
	}

	@GetMapping("/admin/{id}/edit")
	public String blogEdit(@PathVariable(value = "id") long id, Model model) {
		if (adminService.findBlogId(id).isEmpty()) {
			return "redirect:/blog";
		}
		Optional<Blog> blogQuest = adminService.findBlogId(id);
		ArrayList<Blog> list = new ArrayList<>();
		blogQuest.ifPresent(list::add);
		List<BlogDTO> blogDTO = list.stream().map(BlogDTO::toBlog).collect(Collectors.toList());
		model.addAttribute("blogUpdate", blogDTO);
		return "admin_blogs_edit";
	}

	@PostMapping("/admin/{id}/edit")
	public String blogPostUpdate(@PathVariable(value = "id") long id, @RequestParam String topic, @RequestParam String text) {
		Blog blog = adminService.patchBlog(id, topic, text);
		return "redirect:/admin";
	}

	@DeleteMapping("/admin/{id}/remove")
	public String deleteQuest(@PathVariable(value = "id") Long id) {
		adminService.deleteBlog(id);
		return "redirect:/admin";
	}


}
