package com.example.blogApplication;

import com.example.blogApplication.CustomException.APIRequestException;
import com.example.blogApplication.model.BlogCategoryModel;
import com.example.blogApplication.model.BlogPostModel;
import com.example.blogApplication.service.BlogPostService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class BlogPostControllerTest {
    @Autowired
    MockMvc mockMvc;


    List<BlogPostModel> blogPostsList = initializeBlogPost();
    BlogPostModel blogPostModel = blogPostsList.get(0);
    BlogPostModel blogPostModelToGetAll = blogPostsList.get(1);
    BlogPostModel blogPostModelToReturnUpdated = blogPostsList.get(2);
    LocalDateTime dateTime = LocalDateTime.now();
    @MockBean
    private BlogPostService blogPostService;


    @Test
    @WithMockUser(username = "user", password = "user")
    @DisplayName("GET /api/posts/5 - Success")
    public void getBlogPostById_success() throws Exception {
        doReturn(Optional.of(blogPostModel)).when(blogPostService).getBlogPostById(5);
        mockMvc.perform(get("/api/posts/{id}", 5))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.blogId").value(is(5)))
                .andExpect(jsonPath("$.title", is("Agile Workshop")))
                .andExpect(jsonPath("$.content", is("This friday we finished our agile workshop...")))
//                .andExpect(jsonPath("$.dateStamp").value(is(String.valueOf(dateTime))))
                .andExpect(jsonPath("$.imagePath", is("images/HomePageImage/Agile-ScrumWorkshop.png")));
    }

    @Test
    @WithMockUser(username = "user", password = "user")
    @DisplayName("GET /api/posts - Success")
    public void getAllBlogs_success() throws Exception {
        List<BlogPostModel> blogs = Arrays.asList(
                blogPostModelToGetAll,
                blogPostModel);
        when(blogPostService.getBlogPosts()).thenReturn(blogs);
        mockMvc.perform(get("/api/posts"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))

                .andExpect(jsonPath("$[0].blogId").value(is(1)))
                .andExpect(jsonPath("$[0].title", is("Team Work")))
                .andExpect(jsonPath("$[0].content", is("New Trainees are working on their first project...")))
//                .andExpect(jsonPath("$[0].dateStamp").value(is(String.valueOf(dateTime))))
                .andExpect(jsonPath("$[0].imagePath", is("images/HomePageImage/Traineet e vjeter.png")))

                .andExpect(jsonPath("$[1].blogId").value(is(5)))
                .andExpect(jsonPath("$[1].title", is("Agile Workshop")))
                .andExpect(jsonPath("$[1].content", is("This friday we finished our agile workshop...")))
//                .andExpect(jsonPath("$[1].dateStamp").value(is(String.valueOf(dateTime))))
                .andExpect(jsonPath("$[1].imagePath", is("images/HomePageImage/Agile-ScrumWorkshop.png")));
        verify(blogPostService, times(1)).getBlogPosts();
        verifyNoMoreInteractions(blogPostService);
    }

    @Test
    @WithMockUser(username = "user", password = "user")
    @DisplayName("GET /api/posts/100 - Not Found")
    void testGetPostsByIdNotFound() throws Exception {
        doReturn(Optional.empty()).when(blogPostService).getBlogPostById(400);
        mockMvc.perform(get("/api/posts/{id}", 400))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username = "user", password = "user")
    @DisplayName("POST /api/posts/ - Success")
    public void createBlogPost_Succes() throws Exception {
        doReturn(blogPostModel).when(blogPostService).saveBlogPost(any());
        mockMvc.perform(post("/api/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(blogPostModel)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.blogId", is(5)))
                .andExpect(jsonPath("$.title", is("Agile Workshop")))
                .andExpect(jsonPath("$.content", is("This friday we finished our agile workshop...")))
//                .andExpect(jsonPath("$.dateStamp").value(is(String.valueOf(dateTime))))
                .andExpect(jsonPath("$.imagePath", is("images/HomePageImage/Agile-ScrumWorkshop.png")));
    }

    @Test
    @DisplayName("PUT /api/posts/")
    @WithMockUser(username = "user", password = "user")
    void testUpdateBlogPost_Success() throws Exception {
        doReturn(Optional.of(blogPostModel)).when(blogPostService).getBlogPostById(5);
        doReturn(blogPostModelToReturnUpdated).when(blogPostService).updateBlogPost(any());
        mockMvc.perform(put("/api/posts/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(blogPostModel)))

                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.blogId").value(is(5)))
                .andExpect(jsonPath("$.title", is("Agile Workshop 2")))
                .andExpect(jsonPath("$.content", is("This friday we finished our agile workshop...")))
//                .andExpect(jsonPath("$.dateStamp").value(is(String.valueOf(dateTime))))
                .andExpect(jsonPath("$.imagePath", is("images/HomePageImage/Agile-ScrumWorkshop.png")));
    }

    @Test
    @DisplayName("DELETE /api/posts/5 ")
    @WithMockUser(username = "user", password = "user")
    public void deleteBlogPostById_success() throws Exception {
        doReturn(Optional.of(blogPostModel)).when(blogPostService).getBlogPostById(5);
        mockMvc.perform(
                        delete("/api/posts/{id}", 5)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("DELETE /posts - Failed")
    @WithMockUser(username = "user", password = "user")
    void deletePostFailed() throws Exception {
        doReturn(null).when(blogPostService).getBlogPostById(100);
        doThrow(new APIRequestException("The Blog Post Could Not Be Found !")).when(blogPostService).deleteBlogPost(400);
        mockMvc.perform(delete("/api/posts/{id}", 400))
                .andExpect(status().isNotFound())
                .andExpect(content().string("{\"httpStatus\":\"NOT_FOUND\",\"message\":\"The Blog Post Could Not Be Found !\"}"));
    }


    @Before("")
    static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Before("")
    public List<BlogPostModel> initializeBlogPost() {
        List<BlogPostModel> blogs = new ArrayList<>();
        BlogPostModel blogPostModel = new BlogPostModel();
        BlogPostModel blogPostModelToGetAll = new BlogPostModel();
        BlogPostModel blogPostModelToReturnUpdated = new BlogPostModel();

//        String dateTime = "1986-04-08 12:30";
        BlogCategoryModel blogCategoryModel = new BlogCategoryModel();

        blogPostModel.setTitle("Agile Workshop");
        blogPostModel.setBlogId(5);
        blogPostModel.setAuthorName("Grasjela");
        blogPostModel.setContent("This friday we finished our agile workshop...");
        blogPostModel.setImagePath("images/HomePageImage/Agile-ScrumWorkshop.png");
        blogPostModel.setDateStamp(dateTime);
        blogCategoryModel.setCategoryName("TeamWork");
        blogCategoryModel.setCategoryId(2);
        blogPostModel.setBlogCategoryModel(blogCategoryModel);

        blogPostModelToGetAll.setTitle("Team Work");
        blogPostModelToGetAll.setBlogId(1);
        blogPostModelToGetAll.setAuthorName("Grasjela");
        blogPostModelToGetAll.setContent("New Trainees are working on their first project...");
        blogPostModelToGetAll.setImagePath("images/HomePageImage/Traineet e vjeter.png");
//        blogPostModelToGetAll.setDateStamp(dateTime);
        blogCategoryModel.setCategoryName("TeamWork");
        blogCategoryModel.setCategoryId(2);
        blogPostModel.setBlogCategoryModel(blogCategoryModel);


        blogPostModelToReturnUpdated.setTitle("Agile Workshop 2");
        blogPostModelToReturnUpdated.setBlogId(5);
        blogPostModelToReturnUpdated.setAuthorName("Grasjela");
        blogPostModelToReturnUpdated.setContent("This friday we finished our agile workshop...");
        blogPostModelToReturnUpdated.setImagePath("images/HomePageImage/Agile-ScrumWorkshop.png");
//        blogPostModelToReturnUpdated.setDateStamp(dateTime);
        blogCategoryModel.setCategoryName("TeamWork");
        blogCategoryModel.setCategoryId(2);
        blogPostModelToReturnUpdated.setBlogCategoryModel(blogCategoryModel);

        blogs.add(blogPostModel);
        blogs.add(blogPostModelToGetAll);
        blogs.add(blogPostModelToReturnUpdated);
        return blogs;
    }
}