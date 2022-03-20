package com.example.TestProject.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Blog {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;


	private String topic;
	private String text;


	public Blog(String topic, String text) {
		this.topic = topic;
		this.text = text;

	}

}
