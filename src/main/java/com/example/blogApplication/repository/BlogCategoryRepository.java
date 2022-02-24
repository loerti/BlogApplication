package com.example.blogApplication.repository;

import com.example.blogApplication.model.BlogCategoryModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface BlogCategoryRepository extends JpaRepository<BlogCategoryModel, Integer>, JpaSpecificationExecutor<BlogCategoryModel> {

    Optional<BlogCategoryModel> getByCategoryId(Integer id);
}
