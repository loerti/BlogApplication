package com.example.blogApplication;

import com.example.blogApplication.model.BlogPostModel;
import com.github.database.rider.core.DBUnitRule;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.util.EntityManagerProvider;
import org.junit.Rule;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static com.github.database.rider.core.util.EntityManagerProvider.em;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(JUnit4.class)
@SpringBootTest
public class DatabaseIntegrationTest {

    @Rule
    public EntityManagerProvider emProvider = EntityManagerProvider.instance("riderDB");

    @Rule
    public DBUnitRule dbUnitRule = DBUnitRule.instance(emProvider.connection());

    @Test
    @DisplayName("Blog Post Search Keyword User - Successful")
    @DataSet(value="blogpost.yml")
    public void testSuccessfulSearch() {
        final String QUERY =    "SELECT b FROM BlogPostModel b " +
                                "WHERE b.title LIKE '%User%' " +
                                "OR b.content LIKE '%User%' " +
                                "OR b.authorName LIKE '%User%'";
        final int EXPECTED_RESULT = 3;

        List<BlogPostModel> blogPostResultList = em().createQuery(QUERY).getResultList();

        assertThat(blogPostResultList)
                .isNotNull()
                .isNotEmpty()
                .hasSize(EXPECTED_RESULT);
    }

    @Test
    @DisplayName("Blog Post Order By Date ASC - Successful")
    @DataSet("blogpost.yml")
    public void testSuccessfulOrderByDateOnASC() {

        final int TOTAL_BLOG_POST = 3;
        final String QUERY = "SELECT b FROM BlogPostModel b ORDER BY b.dateStamp ASC";

        List<BlogPostModel> blogPostResultList = em().createQuery(QUERY).getResultList();
        assertThat(blogPostResultList)
                .isNotNull()
                .isNotEmpty()
                .hasSize(TOTAL_BLOG_POST);
    }

    @Test
    @DisplayName("Blog Post Order By Date DESC - Successful")
    @DataSet("blogpost.yml")
    public void testSuccessfulOrderByDateOnDESC() {

        final int TOTAL_BLOG_POST = 3;
        final String QUERY = "SELECT b FROM BlogPostModel b ORDER BY b.dateStamp DESC";

        List<BlogPostModel> blogPostResultList = em().createQuery(QUERY).getResultList();
        assertThat(blogPostResultList)
                .isNotNull()
                .isNotEmpty()
                .hasSize(TOTAL_BLOG_POST);
    }

    @Test
    @DisplayName("Blog Post Find Records By Title Trainee Newest Blog - Successful")
    @DataSet("blogpost.yml")
    public void testSuccessfulFindByTitleUnique() {
        final String QUERY = "SELECT b FROM BlogPostModel b where b.title LIKE 'Trainee Newest Blog'";

        List<BlogPostModel> blogPostResultList = em().createQuery(QUERY).getResultList();
        assertThat(blogPostResultList)
                .isNotNull()
                .isNotEmpty()
                .hasSize(1);
    }

    @Test
    @DisplayName("Blog Post Search By Category ID 3 -> Event - Successful")
    @DataSet("blogpost.yml")
    public void testSuccessfulSearchByCategory() {
        final String QUERY = "SELECT b FROM BlogPostModel b  " +
                "JOIN BlogCategoryModel c ON b.blogCategoryModel.categoryId=c.categoryId " +
                "where c.categoryId=3";

        final int EXPECTED_RESULT = 1;
        List<BlogPostModel> blogPostResultList = em().createQuery(QUERY).getResultList();
        assertThat(blogPostResultList)
                .isNotNull()
                .isNotEmpty()
                .hasSize(EXPECTED_RESULT);
    }

}
