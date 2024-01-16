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
    private String id;

    private String content;

    private String createdOn;

    private String updatedOn;

    private String user;

    private String blog;
    
}