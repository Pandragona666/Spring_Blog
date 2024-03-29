package com.example.spring_task.controller;

import com.example.spring_task.model.Comment;
import com.example.spring_task.model.Post;
import com.example.spring_task.model.enums.CategoryEnum;
import com.example.spring_task.repository.UserRepository;
import com.example.spring_task.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

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
    public String home(Model model, Authentication auth){
        if (auth != null){
            UserDetails userDetails = (UserDetails) auth.getPrincipal();
            //przekierowuję do widoku zalogowanego użytkownika
            model.addAttribute("loggedEmail", userDetails.getUsername());
            model.addAttribute("isAdmin", postService.isAdmin(userDetails));
        }
        List<Post> posts = postService.showAllPosts();
        // wydobycie listy kategorii
        Set<CategoryEnum> categories = new HashSet<>();
        for (Post post : posts) {
            categories.add(post.getCategoryEnum());
        }
        // przekazanie listy kategorii do widoku
        model.addAttribute("categories", categories);
        System.out.println("Categories: " + categories);
        // przekazanie obiektu do widoku
        // model.addAttribute(nazwa w html, obiekt przekazywany)
        model.addAttribute("posts", posts);
        return "posts";
    }

    @GetMapping("/filter_category{category}")
    public String filterCategories(@PathVariable CategoryEnum category, Model model){
        List<Post> posts = postService.filterByCategory(category);
        model.addAttribute("posts",posts);
        return "posts";
    }


    @GetMapping("/post/{post_id}")
    public String getPost(@PathVariable Long post_id, Model model, Authentication auth){
        if (auth != null){
            UserDetails userDetails = (UserDetails) auth.getPrincipal();
            //przekierowuję do widoku zalogowanego użytkownika
            model.addAttribute("loggedEmail", userDetails.getUsername());
            model.addAttribute("isAdmin", postService.isAdmin(userDetails));
        }
        Post post = postService.showPost(post_id);
        model.addAttribute("post", post);
        model.addAttribute("comment", new Comment());
        return "selectedpost";
    }

    @PostMapping("/addComment/{post_id}/{user_id}")
    public String addComment(@ModelAttribute Comment comment,
                             @PathVariable Long post_id,
                             @PathVariable Long user_id,
                             Authentication auth,
                             Model model){
        if (auth != null){
            UserDetails userDetails = (UserDetails) auth.getPrincipal();
            //przekierowuję do widoku zalogowanego użytkownika
            model.addAttribute("loggedEmail", userDetails.getUsername());
            model.addAttribute("isAdmin", postService.isAdmin(userDetails));
        }

        postService.addComment(comment, post_id, auth);
        return "redirect:/post/" + post_id;
    }

    @GetMapping("/addpost")
    public String addPost(Model model, Authentication auth){

        if (auth != null){
            UserDetails userDetails = (UserDetails) auth.getPrincipal();
            //przekierowuję do widoku zalogowanego użytkownika
            model.addAttribute("loggedEmail", userDetails.getUsername());
            model.addAttribute("isAdmin", postService.isAdmin(userDetails));
        }

        model.addAttribute("post", new Post());
        List<CategoryEnum> categories =
                new ArrayList<>(Arrays.asList(CategoryEnum.values()));
        model.addAttribute("categories", categories);
        return "addpost";
    }

    @PostMapping("/addpost")
    public String addPost(@ModelAttribute Post post, Authentication authentication){
        UserDetails loggedUserDetails = (UserDetails) authentication.getPrincipal();
        postService.savePost(post, loggedUserDetails.getUsername());
        return "redirect:/";
    }

    @DeleteMapping("/deletepost/{post_id}")
    public String deletePost(@PathVariable Long post_id){
        postService.deletePost(post_id);
        return "redirect:/";
    }

    @GetMapping("/updatepost/{post_id}")
    public String updatePost(@PathVariable Long post_id, Model model){
        Post post = postService.showPost(post_id);
        model.addAttribute("post", post);
        List<CategoryEnum> categories =
                new ArrayList<>(Arrays.asList(CategoryEnum.values()));
        System.out.println(categories);
        model.addAttribute("categories", categories);
        return "updatepost";
    }

    @PutMapping("/updatepost/{post_id}")
    public String updatePost(@PathVariable Long post_id, @ModelAttribute Post post) {
        postService.updatePost(post_id, post);
        return "redirect:/";
    }

    @DeleteMapping("/deletecomment/{comment_id}")
    public String deleteComment(@PathVariable Long comment_id){
        //wydobycie id posta
        Long post_id = postService.getPostByCommentId(comment_id);
        //usuwanie posta po id
        postService.deleteComment(comment_id);
        return "redirect:/post/" + post_id;
    }

    @GetMapping("/post_like/{post_id}")
    public String likePost(@PathVariable Long post_id){
        postService.likePost(post_id);
        return "redirect:/post/" + post_id;
    }

    @GetMapping("/post_dislike/{post_id}")
    public String dislikePost(@PathVariable Long post_id){
        postService.dislikePost(post_id);
        return "redirect:/post/" + post_id;
    }
}
