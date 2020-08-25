package com.example.springdemo.serviceImp;

import com.example.springdemo.entity.Category;
import com.example.springdemo.repositories.CategoryRepository;
import com.example.springdemo.service.CateogoryService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class CategoryServiceImp implements CateogoryService {

    private static final Logger logger = Logger.getLogger(CategoryServiceImp.class);

    @Autowired
    CategoryRepository categoryRepository;

    @Override
    public List<Category> findAllCategory() throws Exception {
        logger.info("Begin service findAllCategory");
        try{
            List<Category> categoryList = new ArrayList<>();
            categoryList = categoryRepository.findAll();
            return categoryList;
        }finally {
            logger.info("End service findAllCategory");
        }
    }

    @Override
    public Category createCategory(Category category) throws Exception {
       logger.info("Begin service createCategory");
       try{
           if(categoryRepository.findByCategoryName(category.getCategoryName()) != null){
               throw new Exception("Category is exist");
           }else {
               category.setCategoryName(category.getCategoryName());
               category.setCreatedAt(new Timestamp(new Date().getTime()));
               category.setStatusActive(true);
               categoryRepository.save(category);
               return category;
           }
       }catch (Exception e){
           e.printStackTrace();
           return null;
       }finally {
           logger.info("End service createCategory");
       }
    }
}
