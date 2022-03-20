package com.example.TestProject;


import com.example.TestProject.entity.Blog;
import com.example.TestProject.repo.AdminRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class ControllerAdminTest extends TestProjectApplicationTests {

	@Autowired
	private MockMvc mvc;
	@Autowired
	private AdminRepository adminRepository;


	@AfterEach
	public void resetDb() {
		adminRepository.deleteAll();
	}

	private Blog creatBlog(String topic, String text) {
		Blog blog = Blog.builder()
				.topic(topic)
				.text(text).build();
		return adminRepository.save(blog);
	}


	@Test
	@WithMockUser(username = "admin", roles = {"USER", "ADMIN"})
	public void greetingSucceedWith200() throws Exception {
		mvc.perform(get("/").contentType(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isOk());
	}

	@Test
	@WithMockUser(username = "admin", roles = {"USER", "ADMIN"})
	public void loginSucceedWith200() throws Exception {
		mvc.perform(get("/login").contentType(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isOk());
	}

	@Test
	@WithMockUser(username = "admin", roles = "USER")
	public void mainSucceedWith200() throws Exception {
		mvc.perform(get("/main").contentType(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isOk());
	}

	@Test
	@WithMockUser(username = "admin", roles = "ADMIN")
	public void adminBlogsSucceedWith200() throws Exception {
		mvc.perform(get("/admin").contentType(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isOk());
	}

	@Test
	@WithMockUser(username = "admin", roles = "ADMIN")
	public void adminAddSucceedWith200() throws Exception {

		mvc.perform(get("/admin/add"))
				.andExpect(status().isOk())
				.andDo(print());

		mvc.perform(post("/admin/add")
						.param("topic", "topic_1")
						.param("text", "text_1"))
				.andExpect(status().is3xxRedirection())
				.andDo(print());
	}

	@Test
	@WithMockUser(username = "admin", roles = "ADMIN")
	public void detailSucceedWith200() throws Exception {
		Blog blog = creatBlog("1_top", "2_text");

		Long id = blog.getId();

		mvc.perform(get("/admin/{id}", id).contentType(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isOk());
	}


}
