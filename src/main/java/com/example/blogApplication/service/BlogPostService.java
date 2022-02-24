package com.example.blogApplication.service;

import com.example.blogApplication.Security.UserAuthentication;
import com.example.blogApplication.model.BlogCategoryModel;
import com.example.blogApplication.model.BlogPostModel;
import com.example.blogApplication.repository.BlogPostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class BlogPostService {

    @Autowired
    UserAuthentication userAuthentication;

    @Autowired
    private BlogPostRepository blogPostRepository;

    @Autowired
    private BlogCategoryService blogCategoryService;

    public List<BlogPostModel> getBlogPosts() {
        return blogPostRepository.findAll();
    }

    public Optional<BlogPostModel> getBlogPostById(int id) {
        return blogPostRepository.findById(id);
    }

    public BlogPostModel saveBlogPost(BlogPostModel blogPostModel) {

        Authentication authentication = userAuthentication.getAuthentication();
        blogPostModel.setAuthorName(authentication.getName());

        return blogPostRepository.save(blogPostModel);
    }

    public List<BlogPostModel> findByTitle(String title) {
        return blogPostRepository.findByTitle(title);
    }

    public BlogPostModel updateBlogPost(BlogPostModel blogPostModel) {

        Optional<BlogCategoryModel> existingCategory = blogCategoryService.findById(blogPostModel.getBlogCategoryModelId());
        BlogPostModel existingPost = blogPostRepository.findById(blogPostModel.getBlogId()).orElse(null);

        existingPost.setTitle(blogPostModel.getTitle());
        existingPost.setContent(blogPostModel.getContent());
        existingPost.setDateStamp(blogPostModel.getDateStamp());
        // Author not to be updated !
        existingPost.setImagePath(blogPostModel.getImagePath());
        existingPost.setBlogCategoryModel(existingCategory.get());

        return blogPostRepository.save(existingPost);
    }

    public void deleteBlogPost(int id) {
        blogPostRepository.deleteById(id);
    }

    public List<BlogPostModel> search(String keyword) {
        return blogPostRepository.search(keyword);
    }

    private Sort orderByDateHelper(String dateOrder) {
        final String ASC = DateOrderConstant.ASCENDING.getValue();
        final String DATE_COL = DateOrderConstant.DATE_COLUMN.getValue();

        if (dateOrder.equals(ASC))
            return Sort.by(Sort.Direction.ASC, DATE_COL);

        return Sort.by(Sort.Direction.DESC, DATE_COL);
    }

    public List<BlogPostModel> orderByDate(String dateOrder) {
        return blogPostRepository.orderByDate(orderByDateHelper(dateOrder));
    }

    public List<BlogPostModel> searchByCategory(Integer category) {
        return blogPostRepository.searchByCategory(category);
    }

    public List<BlogPostModel> searchByCategoryOrderByDate(Integer category, String dateOrder) {
        return blogPostRepository.searchByCategoryOrderByDate(category, orderByDateHelper(dateOrder));
    }
}
