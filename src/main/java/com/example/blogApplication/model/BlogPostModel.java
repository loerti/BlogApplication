package com.example.blogApplication.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "blogpost")
@JsonFormat
public class BlogPostModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer blogId;

    @Column(unique = true)
    @JsonProperty("title")
    private String title;

    @JsonProperty("content")
    private String content;

    @JsonProperty("dateStamp")
    private LocalDateTime dateStamp = LocalDateTime.now();

    @JsonProperty("authorName")
    private String authorName;

    @JsonProperty("imagePath")
    private String imagePath;


    @ManyToOne
    @JoinColumn(name = "categoryId", referencedColumnName = "categoryId")
    private BlogCategoryModel blogCategoryModel;


    // we will use dependency injection later on to initialize this class using the empty constructor
    public BlogPostModel() {

    }

    public Integer getBlogId() {
        return blogId;
    }

    // Used for testing purposes in the controller
    public void setBlogId(Integer blogId) {
        this.blogId = blogId;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getAuthorName() {
        return authorName;
    }

    @JsonIgnore
    public Integer getBlogCategoryModelId() {
        return blogCategoryModel.getCategoryId();
    }

    @JsonIgnore
    public String getBlogCategoryModelName() {
        return blogCategoryModel.getCategoryName();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getDateStamp() {
        return dateStamp;
    }

    public void setDateStamp(LocalDateTime dateStamp) {
        this.dateStamp = dateStamp;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public void setBlogCategoryModel(BlogCategoryModel blogCategoryModel) {
        this.blogCategoryModel = blogCategoryModel;
    }

    public BlogCategoryModel getBlogCategoryModel() {
        return blogCategoryModel;
    }

}
