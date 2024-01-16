package com.projects.blog.Models;
import java.util.ArrayList;
import java.util.List;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Document(collection = "blogs")
@AllArgsConstructor
@NoArgsConstructor
public class Blog {
    
    @Id
    private String id;

    private String title;

    private String content;

    private String image;

    private String category;

    private List<String> tags;

    private String slug;

    private String excerpt;

    private String author;

    private String createdOn;

    private String updatedOn;

    private List<String> likes;

    private List<String> comments;

}