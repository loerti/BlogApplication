package com.example.blogApplication;

import com.example.blogApplication.controller.BlogPostController;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.hamcrest.Matchers.equalTo;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class BlogApplicationTests {

	@Autowired
	BlogPostController blogPostController;

//	@Test
//	public void contextLoads() {
//		Assertions.assertThat(blogPostController).isNot(null);
//	}
}
