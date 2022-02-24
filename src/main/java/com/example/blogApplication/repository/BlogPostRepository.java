package com.example.blogApplication.repository;

import com.example.blogApplication.model.BlogPostModel;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BlogPostRepository extends JpaRepository<BlogPostModel, Integer>, JpaSpecificationExecutor<BlogPostModel> {

    @Query(value = "SELECT b FROM BlogPostModel b " +
            "WHERE b.title LIKE %:keyword% " +
            "OR b.content LIKE %:keyword% " +
            "OR b.authorName LIKE %:keyword%")
    List<BlogPostModel> search(@Param("keyword") String keyword);

    @Query(value = "SELECT b FROM BlogPostModel b")
    List<BlogPostModel> orderByDate(Sort sort);

    @Query(value = "SELECT b FROM BlogPostModel b  " +
            "JOIN BlogCategoryModel c ON b.blogCategoryModel.categoryId=c.categoryId " +
            "where c.categoryId=:category")
    List<BlogPostModel> searchByCategory(@Param("category") Integer category);

    @Query(value = "SELECT b FROM BlogPostModel b " +
            "JOIN BlogCategoryModel c ON b.blogCategoryModel.categoryId=c.categoryId " +
            "where c.categoryId = :category ")
    List<BlogPostModel> searchByCategoryOrderByDate(@Param("category") Integer category, Sort sort);

    @Query(value = "SELECT b FROM BlogPostModel b where b.title LIKE :title")
    List<BlogPostModel> findByTitle(@Param("title") String title);

}

