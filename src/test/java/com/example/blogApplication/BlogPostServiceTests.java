package com.example.blogApplication;

import com.example.blogApplication.model.BlogPostModel;
import com.example.blogApplication.repository.BlogPostRepository;
import com.example.blogApplication.service.BlogPostService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class BlogPostServiceTests {
    public static final int GIVEN_ID = 1;
    LocalDateTime date = LocalDateTime.now();
    BlogPostModel mockBlogPostModel;
    @Autowired
    BlogPostService blogPostService;
    @MockBean
    private BlogPostRepository blogPostRepository;

    @BeforeEach
    void initBlogPostModel() {
        mockBlogPostModel = new BlogPostModel();
        mockBlogPostModel.setBlogId(GIVEN_ID);
        mockBlogPostModel.setTitle("Find by id");
        mockBlogPostModel.setContent("This is a test to find the blog by a given id");
        mockBlogPostModel.setDateStamp(date);
        mockBlogPostModel.setImagePath("The image path for the image");
    }

    @Test
    @WithMockUser(username = "user", password = "user")
    @DisplayName("To test finding a blog by id")
    void testToFindById() {
        doReturn(Optional.of(mockBlogPostModel)).when(blogPostRepository).findById(GIVEN_ID);
        Optional<BlogPostModel> returnedBlogPostModel = blogPostRepository.findById(GIVEN_ID);
        assertTrue(returnedBlogPostModel.isPresent(), "The blog was not found");
    }

    @Test
    @DisplayName("Test saving the blog")
    @WithMockUser(username = "user", password = "user")
    void testSaveOnService() {
        // do return will assign the created mock in the repository so that it can be used later in testing
        doReturn(mockBlogPostModel).when(blogPostRepository).save(any());
        BlogPostModel returnedBlogPostModel = blogPostService.saveBlogPost(mockBlogPostModel);
        assertNotNull(returnedBlogPostModel, "The saved blog should not be null");
        assertEquals(GIVEN_ID, returnedBlogPostModel.getBlogId(), "The expected value should be 1");
    }

    @Test
    @DisplayName("Test on updating the post")
    @WithMockUser(username = "username", password = "user")
    void testUpdateOnService() {
        doReturn(mockBlogPostModel).when(blogPostRepository).save(any());
        BlogPostModel returnedBlogPostModel = blogPostService.saveBlogPost(mockBlogPostModel);
        assertNotNull(returnedBlogPostModel, "The updated blog should not be null");
        assertEquals(GIVEN_ID, returnedBlogPostModel.getBlogId(), "The expected value should be 1");
    }

    @Test
    @DisplayName("Test deleting the blog")
    @WithMockUser(username = "user", password = "user")
    void testDelete() {
        blogPostService.deleteBlogPost(mockBlogPostModel.getBlogId());
        verify(blogPostRepository, times(1)).deleteById(mockBlogPostModel.getBlogId());
    }
}