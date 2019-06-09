package com.example.spring_task.controller;

import com.example.spring_task.model.Comment;
import com.example.spring_task.model.Post;
import com.example.spring_task.model.enums.CategoryEnum;
import com.example.spring_task.repository.UserRepository;
import com.example.spring_task.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
public class PostsController {

    PostService postService;
    UserRepository userRepository;

    @Autowired
    public PostsController(PostService postService, UserRepository userRepository) {
        this.postService = postService;
        this.userRepository = userRepository;
    }

    @GetMapping("/")
    public String home(Model model){
        List<Post> posts = postService.showAllPosts();
        model.addAttribute("posts", posts);
        return "posts";
    }


    @GetMapping("/post/{post_id}")
    public String getPost(@PathVariable Long post_id, Model model){
        Post post = postService.showPost(post_id);
        model.addAttribute("post", post);
        model.addAttribute("comment", new Comment());
        return "selectedpost";
    }

    @PostMapping("/addComment/{post_id}/{user_id}")
    public String addComment(@ModelAttribute Comment comment,
                             @PathVariable Long post_id,
                             @PathVariable Long user_id){
        postService.addComment(comment, post_id, user_id);
        return "redirect:/post/" + post_id;
    }

    @GetMapping("/addpost")
    public String addPost(Model model){
        model.addAttribute("post", new Post());
        List<CategoryEnum> categories =
                new ArrayList<>(Arrays.asList(CategoryEnum.values()));
        model.addAttribute("categories", categories);
        return "addPost";
    }

    @PostMapping("/addpost")
    public String addPost(@ModelAttribute Post post){
        postService.savePost(post);
        return "redirect:/";
    }

}