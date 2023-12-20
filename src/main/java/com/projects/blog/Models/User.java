package com.projects.blog.Models;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "users")
public class User implements UserDetails{
    
    @Id
    private String uId;

    private String uName;

    private String uEmail;

    private String uPassword;

    private String uRole;

    private String uImage;

    private String uAbout;

    private String uGender;

    private String createdOn;

    @JsonIgnore
    @DBRef
    private List<Blog> blogs = new ArrayList<>();

    @JsonIgnore
    @DBRef
    private List<Likes> uLikes  = new ArrayList<>();

    @JsonIgnore
    @DBRef
    private List<Comments> uComments = new ArrayList<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null ;
    }

    @Override
    public String getPassword() {
        return this.uPassword;
    }

    @Override
    public String getUsername() {
        return this.uId ;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true ;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true ;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true ;
    }

    @Override
    public boolean isEnabled() {
        return true ;
    }

}
