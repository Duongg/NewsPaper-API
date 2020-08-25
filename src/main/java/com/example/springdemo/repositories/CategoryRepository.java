package com.example.springdemo.repositories;

import com.example.springdemo.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
    List<Category> findAll();

    Category findById(int id);

    Category findByCategoryName(String categoryName);
}
