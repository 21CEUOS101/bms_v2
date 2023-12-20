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
@Document(collection = "comments")
@NoArgsConstructor
@AllArgsConstructor
public class Comments {

    @Id
    private String cId;

    private String cContent;

    private String cCreatedOn;

    private String cUpdatedOn;

    @JsonIgnore
    @DBRef
    private User cUser;

    @JsonIgnore
    @DBRef
    private Blog cBlog;
    
}