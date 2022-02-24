package com.example.blogApplication.service;

import com.example.blogApplication.model.BlogCategoryModel;
import com.example.blogApplication.repository.BlogCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BlogCategoryService {

    @Autowired
    private BlogCategoryRepository blogCategoryRepository;

    public Optional<BlogCategoryModel> findById(Integer id) {
        return blogCategoryRepository.getByCategoryId(id);
    }

}
