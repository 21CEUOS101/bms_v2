package com.projects.blog.Requests;

import java.util.List;

import lombok.Data;

@Data
@lombok.AllArgsConstructor
@lombok.NoArgsConstructor
public class BlogRequest {
    private String userId;
    private String title;
    private String content;
    private String slug;
    private String excerpt;
    private String category;
    private List<String> tags;
    private String image;
}
