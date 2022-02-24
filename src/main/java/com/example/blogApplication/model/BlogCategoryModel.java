package com.example.blogApplication.model;

import javax.persistence.*;

@Entity
@Table(name = "blogcategory")
public class BlogCategoryModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer categoryId;

    private String categoryName;

    public BlogCategoryModel() {
    }


    public BlogCategoryModel(Integer categoryId, String categoryName) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
    public void setCategoryId(Integer categoryId){
        this.categoryId=categoryId;}
    }

