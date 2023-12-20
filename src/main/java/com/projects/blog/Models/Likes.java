package com.projects.blog.Models;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "likes")
public class Likes {
    
    @Id
    private String lId;

    @JsonIgnore
    @DBRef
    private User lUser;

    @JsonIgnore
    @DBRef
    private Blog lBlog;

    private String lCreatedOn;

}
