package com.example.spring_task.model;

import com.example.spring_task.model.enums.CategoryEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String postName;
    @Type(type = "text")
    private String content;
    @Enumerated
    private CategoryEnum categoryEnum;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User postOwner;

    private int like_no;
    private int dislike_no;

    public Post(String postName, String content, CategoryEnum categoryEnum, User postOwner) {
        this.postName = postName;
        this.content = content;
        this.categoryEnum = categoryEnum;
        this.postOwner = postOwner;
    }
    @JsonIgnore
    //fetch type eager - powoduje ze wraz z obiektem posta masz dostep do wsyztskich jego powiazań
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "post")
    List<Comment> comments = new ArrayList<>();
}
