package com.example.TestProject.service;

import com.example.TestProject.entity.Blog;
import com.example.TestProject.repo.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AdminService {

	private final AdminRepository adminRepository;

	@Autowired
	public AdminService(AdminRepository adminRepository) {
		this.adminRepository = adminRepository;
	}


	public List<Blog> findAll() {
		Iterable<Blog> blogQuests = adminRepository.findAll();
		List<Blog>blogQuests1=new ArrayList<>();
		blogQuests.forEach(blogQuests1::add);
		return blogQuests1;
	}

	public Blog saveQuest(Blog blog) {
		return adminRepository.save(blog);
	}

	public Optional<Blog> findBlogId(Long id) {
		Optional<Blog> byId = adminRepository.findById(id);
		return  byId;
		
	}

	public Blog patchBlog(Long id, String topic,String text) {
		Blog blog1 = adminRepository.findById(id).orElse(null);
		blog1.setTopic(topic);
		blog1.setText(text);
		return adminRepository.save(blog1);
	}
	public void deleteBlog(Long id){
		Blog blog1 = adminRepository.findById(id).orElse(null);
		adminRepository.delete(blog1);

	}


}
