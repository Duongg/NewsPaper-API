package com.example.springdemo.service;

import com.example.springdemo.entity.Category;

import java.util.List;

public interface CateogoryService {
    public List<Category> findAllCategory() throws Exception;

    public Category createCategory(Category category) throws Exception;
}
