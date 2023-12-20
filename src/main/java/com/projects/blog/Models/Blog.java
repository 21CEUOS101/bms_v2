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
    private String bId;

    private String bTitle;

    private String bContent;

    private String bImage;

    private String bCategory;

    private List<String> bTags = new ArrayList<>();

    private String bSlug;

    private String bExcerpt;

    @JsonIgnore
    @DBRef
    private User bAuthor;

    private String bCreatedOn;

    private String bUpdatedOn;

    @JsonIgnore
    @DBRef
    private List<Likes> bLikes = new ArrayList<>();

    @JsonIgnore
    @DBRef
    private List<Comments> bComments = new ArrayList<>();

}