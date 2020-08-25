package com.example.springdemo.repositories;

import com.example.springdemo.entity.News;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NewsRepository extends JpaRepository<News, Integer> {

    List<News> findAll();

    Page<News> findAll(Pageable pageable);

    News findById(int id);

    News findByTitle(String title);

}
