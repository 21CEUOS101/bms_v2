package com.projects.blog.Requests;

import lombok.Data;

@Data
@lombok.AllArgsConstructor
@lombok.NoArgsConstructor
public class CommentRequest {
    
    private String userId;
    private String comments;
}
