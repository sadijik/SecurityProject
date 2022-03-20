package com.example.TestProject.BlogQuestDTO;

import com.example.TestProject.entity.Blog;
import lombok.Data;

@Data
public class BlogDTO {

	private String topic;
	private String text;
	private Long id;

	public static BlogDTO toBlog(Blog blog) {
		BlogDTO blogDTO = new BlogDTO();
		blogDTO.setTopic(blog.getTopic());
		blogDTO.setText(blog.getText());
		blogDTO.setId(blog.getId());

		return blogDTO;
	}

}
