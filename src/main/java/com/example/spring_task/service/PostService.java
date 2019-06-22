package com.example.spring_task.service;

import com.example.spring_task.model.Comment;
import com.example.spring_task.model.Post;
import com.example.spring_task.model.User;
import com.example.spring_task.model.enums.CategoryEnum;
import com.example.spring_task.repository.CommentRepository;
import com.example.spring_task.repository.PostRepository;
import com.example.spring_task.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {

    PostRepository postRepository;
    UserRepository userRepository;
    CommentRepository commentRepository;

    @Autowired
    public PostService(PostRepository postRepository, UserRepository userRepository, CommentRepository commentRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.commentRepository = commentRepository;
    }

    public List<Post> showAllPosts(){
        return postRepository.findAll();
    }

    public Post showPost(Long postId) {
        return postRepository.getOne(postId);
    }


    public void addComment(Comment comment, Long post_id, Long user_id){
        User user = userRepository.getOne(user_id);
        Post post = postRepository.getOne(post_id);
        comment.setUser(user);
        comment.setPost(post);
        commentRepository.save(comment);
    }

    public void savePost(Post post, String email){
        User user = userRepository.getByEmail(email);
        post.setPostOwner(user);
        postRepository.save(post);
    }

    public void deletePost(Long postId) {
        postRepository.deleteById(postId);
    }

    public void updatePost(Long postId, Post updatedPost){
        Post post = postRepository.getOne(postId);
        post.setPostName(updatedPost.getPostName());
        post.setContent(updatedPost.getContent());
        post.setCategoryEnum(updatedPost.getCategoryEnum());
        postRepository.save(post);
    }
}
