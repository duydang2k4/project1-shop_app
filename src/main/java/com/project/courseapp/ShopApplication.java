package com.project.courseapp;

import com.project.courseapp.dtos.CategoryDTO;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ShopApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShopApplication.class, args);
//		CategoryDTO categoryDTO = new CategoryDTO();
//		categoryDTO.setName("Mama");
	}
}
