package com.example.blogApplication.controller;

import com.example.blogApplication.CustomException.APIRequestException;
import com.example.blogApplication.CustomException.DuplicatedTitleRequestException;
import com.example.blogApplication.model.BlogPostModel;
import com.example.blogApplication.service.BlogCategoryService;
import com.example.blogApplication.service.BlogPostService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api")
public class BlogPostController {
    private static final Logger LOGGER = LoggerFactory.getLogger(BlogPostController.class);

    @Autowired
    private BlogPostService blogPostService;

    @Autowired
    private BlogCategoryService blogCategoryService;

    @GetMapping("/posts")
    public List<BlogPostModel> getBlogPosts() {
        LOGGER.info("Getting all blog posts");
        return blogPostService.getBlogPosts();
    }

    @GetMapping("/posts/{id}")
    public ResponseEntity<BlogPostModel> getBlogPostById(@PathVariable int id){
        BlogPostModel blogPostModel = blogPostService.getBlogPostById(id)
                .orElseThrow( () ->
                    new APIRequestException("The Blog Post Could Not Be Found !")
                );
        LOGGER.info("Getting a blog post");
        return ResponseEntity.ok(blogPostModel);
    }

    @PostMapping("/posts")
    public ResponseEntity<BlogPostModel> saveBlogPost(@RequestBody BlogPostModel blogPostModel) {

        blogCategoryService.findById( blogPostModel.getBlogCategoryModelId() )
            .orElseThrow( () -> new APIRequestException("The Selected Category Could Not Be Found !"));

        if( !blogPostService.findByTitle(blogPostModel.getTitle()).isEmpty() )
            throw new DuplicatedTitleRequestException("The Title Is Already Present !");
        LOGGER.warn("You can't save a title which is already existing !");
        return ResponseEntity.ok(blogPostService.saveBlogPost(blogPostModel));
    }

    @PutMapping("/posts")
    public ResponseEntity<BlogPostModel> updateBlogPost(@RequestBody BlogPostModel blogPostModel) {
        blogCategoryService.findById( blogPostModel.getBlogCategoryModelId() )
                .orElseThrow( () -> new APIRequestException("The Selected Category Could Not Be Found !"));

        BlogPostModel existingPost = blogPostService.getBlogPostById(blogPostModel.getBlogId())
                .orElseThrow(()->new APIRequestException("Object with this ID could not be found"));
        LOGGER.error("BlogPost ID isn't available");
        if( !existingPost.getTitle().equals(blogPostModel.getTitle()) ){
            if ( !blogPostService.findByTitle(blogPostModel.getTitle()).isEmpty() )
                throw new DuplicatedTitleRequestException("The Title Is Already Present !");
        }
        LOGGER.error("You can't edit a post and save it with the same name");
        return ResponseEntity.ok(blogPostService.updateBlogPost(blogPostModel));
    }

    @DeleteMapping("/posts/{id}")
    public void deleteBlogPost(@PathVariable int id) {
        blogPostService.getBlogPostById(id)
                .orElseThrow( () ->
                        new APIRequestException("The Blog Post Could Not Be Found !")
                );
        blogPostService.deleteBlogPost(id);
    }

    @GetMapping("/search")
    public List<BlogPostModel> search(@RequestParam String keyword) {
        LOGGER.info("Searching for a specific keyword if it matches any title,content or author name");
        return blogPostService.search(keyword);
    }

    @GetMapping("/filter")
    public List<BlogPostModel> advancedFilter(@RequestParam String category, @RequestParam String dateOrder) {

        if (category.equals("")) {
            return blogPostService.orderByDate(dateOrder);
        }
        final Integer CATEGORY_ID = Integer.valueOf(category);

        if (dateOrder.equals("")) {
            return blogPostService.searchByCategory(CATEGORY_ID);
        }

        return blogPostService.searchByCategoryOrderByDate(CATEGORY_ID, dateOrder);
    }

}
