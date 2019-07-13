package com.example.spring_task.repository;

import com.example.spring_task.model.Post;
import com.example.spring_task.model.enums.CategoryEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post  , Long> {
    // SELECT * FROM post WHERE category = ?
    List<Post> findAllByCategoryEnum(CategoryEnum category);
}
