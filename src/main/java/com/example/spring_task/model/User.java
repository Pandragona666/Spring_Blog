package com.example.spring_task.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity

public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Email(message = "Invalid email address")
    @NotBlank(message = "Email address is mandatory")
    private String email;
    @NotBlank(message = "Password is mandatory")
    @Size(min = 6, message = "your password need to be minimum 6 characters long")
    private String password;
    private LocalDateTime registrationDate = LocalDateTime.now();
    private boolean activityFlag = true;
    @Transient
    @NotBlank(message = "Password is mandatory")
    @Size(min = 6, message = "your password need to be minimum 6 characters long")
    private String password_confirm;

    @ManyToMany(cascade = CascadeType.ALL,
                fetch = FetchType.EAGER)
    @JoinTable(name = "user_role",
                joinColumns = @JoinColumn(name = "user_id"),
                inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    @JsonIgnore
    //fetch type eager - powoduje ze wraz z obiektem posta masz dostep do wsyztskich jego powiazań
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "postOwner")
    List<Post> posts = new ArrayList<>();

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public void addRole(Role role){
        this.roles.add(role);
    }

    public void subRole(Role role){
        this.roles.remove(role);
    }

}
