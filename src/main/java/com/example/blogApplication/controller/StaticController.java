package com.example.blogApplication.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class StaticController {

    @RequestMapping("/login")
    public String toLogIn() {
        return "login";
    }

    @RequestMapping("/home-page")
    public String toHome() {
        return "homePage";
    }

    @RequestMapping("/add-post")
    public String toAddPost() {
        return "addPost";
    }

    @RequestMapping("/blog-post")
    public ModelAndView toBlogPost(@RequestParam Integer id) {
        ModelAndView blogPostView = new ModelAndView("blogPost");
        blogPostView.addObject("postId", id);
        return blogPostView;
    }

    @RequestMapping("/search")
    public ModelAndView toSearchPost(@RequestParam String keyword) {
        ModelAndView searchPostView = new ModelAndView("homePage");
        searchPostView.addObject("keyword", keyword);
        return searchPostView;
    }

    @RequestMapping("/filter")
    public ModelAndView toFilterPost(@RequestParam String category, @RequestParam String dateOrder) {
        ModelAndView filterPostView = new ModelAndView("homePage");
        if (!category.equals(""))
            filterPostView.addObject("category", category);
        if (!dateOrder.equals(""))
            filterPostView.addObject("dateOrder", dateOrder);

        return filterPostView;
    }

    @RequestMapping("/edit-post")
    public ModelAndView toEditPost(@RequestParam Integer id) {
        ModelAndView editPostView = new ModelAndView("addPost");
        editPostView.addObject("postId", id);
        return editPostView;
    }


}
