package com.example.springdemo.service;

import com.example.springdemo.entity.News;
import org.springframework.data.domain.Page;

import java.util.List;

public interface NewsService {

    List<News> findAllNews() throws Exception;

    Page<News> findAllNews(int page, int size) throws Exception;

    News createNews(News news) throws Exception;

    News updateNews(News news) throws Exception;

    News disableNews(int id) throws Exception;

    News enableNews(int id) throws Exception;


}
