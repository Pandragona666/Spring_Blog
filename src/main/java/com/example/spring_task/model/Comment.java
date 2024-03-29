package com.example.spring_task.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long commentId;
    private String message;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    // usuniecie komentarza nie bedzie powodowało usunięcie relacji (czyli naszego posta)
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    public Comment(String message, User user, Post post) {
        this.message = message;
        this.user = user;
        this.post = post;
    }
}
