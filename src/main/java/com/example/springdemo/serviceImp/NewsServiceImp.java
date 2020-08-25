package com.example.springdemo.serviceImp;

import com.example.springdemo.entity.Account;
import com.example.springdemo.entity.Category;
import com.example.springdemo.entity.News;
import com.example.springdemo.repositories.CategoryRepository;
import com.example.springdemo.repositories.NewsRepository;
import com.example.springdemo.service.NewsService;
import com.example.springdemo.util.Message;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class NewsServiceImp implements NewsService {

    private static final Logger logger = Logger.getLogger(NewsServiceImp.class);

    @Autowired
    NewsRepository newsRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Override
    public List<News> findAllNews() throws Exception {
        logger.info("Begin service findAllNews");
        try{
            List<News> newsList = new ArrayList<>();
            newsList = newsRepository.findAll();
            return newsList;
        }finally {
            logger.info("End service findAllNews");
        }
    }

    @Override
    public Page<News> findAllNews(int page, int size) throws Exception {
        logger.info("Begin service findAllNews");
        try{
            Pageable pageable = PageRequest.of(page, size);
            Page<News> newsPage = newsRepository.findAll(pageable);
            return newsPage;
        }finally {
            logger.info("End service findAllNews");
        }
    }

    @Override
    public News createNews(News news) throws Exception {
        logger.info("Begin service createNews");
        try{
            if(newsRepository.findByTitle(news.getTitle()) != null){
                throw new Exception(Message.NEWS_EXIST);
            }else {
                news.setTitle(news.getTitle());
                news.setShortDescription(news.getShortDescription());
                news.setImage(news.getImage());
                news.setDescription(news.getDescription());
                news.setCreatedAt(new Timestamp(new Date().getTime()));
                news.setStatusActive(true);
                Category category = new Category();
                List<Category> categoryList = categoryRepository.findAll();
                for (int i = 0; i < categoryList.size(); i++) {
                    category = categoryRepository.findByCategoryName(categoryList.get(i).getCategoryName());
                    if(category.getId() == news.getIdCategory()){
                        news.setIdCategory(category.getId());
                    }
                }
                newsRepository.save(news);
                return news;
            }
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }finally {
            logger.info("End service createNews");
        }
    }

    @Override
    public News updateNews(News news) throws Exception {
        logger.info("Begin service updateNews");
        try{
            if(newsRepository.findById(news.getId()) == null){
                throw new Exception(Message.NEWS_NOT_EXIST);
            }else {
                News newsUpdate = newsRepository.findById(news.getId());
                newsUpdate.setTitle(news.getTitle());
                newsUpdate.setShortDescription(news.getShortDescription());
                newsUpdate.setImage(news.getImage());
                newsUpdate.setDescription(news.getDescription());
                newsUpdate.setIdCategory(news.getIdCategory());
                newsUpdate.setModifiedAt(news.getModifiedAt());
                newsUpdate.setStatusActive(news.isStatusActive());
                newsRepository.save(newsUpdate);
                return news;
            }
        }catch (Exception e){
            logger.error(e);
            return null;
        }finally {
            logger.info("End service updateNews");
        }
    }
    private News changeStatus(News news){
        if(news.isStatusActive()){
            news.setStatusActive(false);
        }else {
            news.setStatusActive(true);
        }
        return news;
    }
    @Override
    public News disableNews(int id) throws Exception {
        logger.info("Begin service disableNews");
        try{
            News news = newsRepository.findById(id);
            if(news == null){
                throw new Exception("News is not exist");
            }else {
                if(news.isStatusActive()){
                    news = changeStatus(news);
                }
                newsRepository.save(news);
                return news;
            }
        }finally {
            logger.info("End service disableNews");
        }
    }

    @Override
    public News enableNews(int id) throws Exception {
        logger.info("Begin service disableNews");
        try{
            News news = newsRepository.findById(id);
            if(news == null){
                throw new Exception("News is not exist");
            }else {
                if(!news.isStatusActive()){
                    news = changeStatus(news);
                }
                newsRepository.save(news);
                return news;
            }
        }finally {
            logger.info("End service disableNews");
        }
    }
}
