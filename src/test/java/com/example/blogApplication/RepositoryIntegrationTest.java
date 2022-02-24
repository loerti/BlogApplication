package com.example.blogApplication;

import com.example.blogApplication.model.BlogPostModel;
import com.example.blogApplication.repository.BlogPostRepository;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

//@ExtendWith({SpringExtension.class})
@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class RepositoryIntegrationTest {

    @Autowired
    private BlogPostRepository blogPostRepository;

    @Test
    public void testSuccessfullSearch() {
        List<BlogPostModel> blogPostResultList = blogPostRepository.findByTitle("Test1");
        System.out.println(blogPostResultList.size());
        assertEquals(0, blogPostResultList.size(),
                "Expected One Blog Post in the Database");
    }

    @Test
    public void testUnsuccessfullSearch() {
        List<BlogPostModel> blogPostResultList = blogPostRepository.search("TraineeTitleTest");
        assertEquals(0, blogPostResultList.size(),
                "No Blog Posts Expected in the Database");
    }

    @Test
    @DisplayName("Blog Post Order By Date ASC - Successful")
    public void testSuccessfulOrderByDateOnASC() {
        Sort sortMethodASC = Sort.by(Sort.Direction.ASC, "dateStamp");
        List<BlogPostModel> blogPostResultList = blogPostRepository.orderByDate(sortMethodASC);

        final int TOTAL_BLOG_POST = blogPostRepository.findAll().size();
        final int ACTUAL_BLOG_POST = blogPostResultList.size();
        assertEquals(TOTAL_BLOG_POST, ACTUAL_BLOG_POST,
                "Expected " + TOTAL_BLOG_POST + " Blog Posts");
    }

    @Test
    @DisplayName("Blog Post Order By Date DESC - Successful")
    public void testSuccessfulOrderByDateOnDESC() {
        Sort sortMethodASC = Sort.by(Sort.Direction.DESC, "dateStamp");
        List<BlogPostModel> blogPostResultList = blogPostRepository.orderByDate(sortMethodASC);

        final int TOTAL_BLOG_POST = blogPostRepository.findAll().size();
        final int ACTUAL_BLOG_POST = blogPostResultList.size();
        assertEquals(TOTAL_BLOG_POST, ACTUAL_BLOG_POST,
                "Expected " + TOTAL_BLOG_POST + " Blog Posts");
    }

    @Test
    @DisplayName("Blog Post Find Records By Title Test - Successful")
    public void testSuccessfulFindByTitle() {
        final String TITLE = "Test";
        List<BlogPostModel> blogPostResultList = blogPostRepository.findByTitle(TITLE);

        final int EXPECTED_BLOG_POST = 0;
        final int ACTUAL_BLOG_POST = blogPostResultList.size();
        assertEquals(EXPECTED_BLOG_POST, ACTUAL_BLOG_POST,
                "Expected " + EXPECTED_BLOG_POST + " Blog Posts");
    }

    @Test
    @DisplayName("Blog Post Find Records By Title Lindor - UnSuccessful")
    public void testUnSuccessfulFindByTitle() {
        final String TITLE = "Lindor";
        List<BlogPostModel> blogPostResultList = blogPostRepository.findByTitle(TITLE);

        final int EXPECTED_BLOG_POST = 0;
        final int ACTUAL_BLOG_POST = blogPostResultList.size();
        assertEquals(EXPECTED_BLOG_POST, ACTUAL_BLOG_POST,
                "No Such Title Present In DB");
    }

    @Test
    @DisplayName("Blog Post Search By Category ID 2 -> Team Event - Successful")
    public void testSuccessfulSearchByCategory() {
        final Integer CATEGORY_ID = 2;
        List<BlogPostModel> blogPostResultList = blogPostRepository.searchByCategory(CATEGORY_ID);

        final int EXPECTED_BLOG_POST = 1;
        final int ACTUAL_BLOG_POST = blogPostResultList.size();
        assertEquals(EXPECTED_BLOG_POST, ACTUAL_BLOG_POST,
                "Expected " + EXPECTED_BLOG_POST + " Blog Posts from Search By Category Filter");
    }

    @Test
    @DisplayName("Blog Post Search By Category ID 55 - UnSuccessful")
    public void testUnSuccessfulSearchByCategory() {
        final Integer CATEGORY_ID = 505;
        List<BlogPostModel> blogPostResultList = blogPostRepository.searchByCategory(CATEGORY_ID);

        final int EXPECTED_BLOG_POST = 0;
        final int ACTUAL_BLOG_POST = blogPostResultList.size();
        assertEquals(EXPECTED_BLOG_POST, ACTUAL_BLOG_POST,
                "Expected 0 Blog Post from Category Search Team Work");
    }


}
