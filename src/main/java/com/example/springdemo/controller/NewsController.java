package com.example.springdemo.controller;

import com.example.springdemo.entity.Account;
import com.example.springdemo.entity.News;
import com.example.springdemo.service.NewsService;
import com.example.springdemo.util.URL;
import dto.ServiceResponseDTO;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(URL.API)
public class NewsController {
    private static final Logger logger = Logger.getLogger(NewsController.class);

    @Autowired
    NewsService newsService;

    @GetMapping(URL.GET_NEWS)
    public ResponseEntity findAllNews(){
        logger.info("Begin controller findAllNews");
        ServiceResponseDTO response = new ServiceResponseDTO();
        try{
            List<News> newsList = newsService.findAllNews();
            response.setData(newsList);
            return new ResponseEntity(response, HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            response.setMessage(e.getMessage());
            response.setStatus(ServiceResponseDTO.Status.FAILED);
            return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
        }finally {
            logger.info("End controller findAllNews");
        }
    }
    @GetMapping(URL.GET_NEWS + "/page")
    public ResponseEntity findAllNews(@RequestParam int page, @RequestParam int size){
        logger.info("Begin controller findAllNews Page");
        ServiceResponseDTO response = new ServiceResponseDTO();
        try{
            Page<News> newsList = newsService.findAllNews(page, size);
            response.setData(newsList);
            return new ResponseEntity(response, HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            response.setMessage(e.getMessage());
            response.setStatus(ServiceResponseDTO.Status.FAILED);
            return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
        }finally {
            logger.info("End controller findAllNews Page");
        }
    }
    @PostMapping(URL.CREATE_NEWS)
    public ResponseEntity createNews(@RequestBody News news){
        logger.info("Begin controller createNews");
        ServiceResponseDTO response = new ServiceResponseDTO();
        try{
            newsService.createNews(news);
            response.setMessage("Created");
            return new ResponseEntity(response, HttpStatus.OK);
        }catch (Exception e){
            logger.error(e);
            response.setMessage(e.getMessage());
            response.setStatus(ServiceResponseDTO.Status.FAILED);
            return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
        }finally {
            logger.info("End controller createNews");
        }
    }

    @PutMapping(URL.UPDATE_NEWS)
    public ResponseEntity updateNews(@RequestBody News news) {
        logger.info("Begin service updateNews");
        ServiceResponseDTO response = new ServiceResponseDTO();
        try {
            newsService.updateNews(news);
            response.setMessage("Updated");
            return new ResponseEntity(response, HttpStatus.OK);
        } catch (Exception e) {
            logger.equals(e);
            response.setMessage(e.getMessage());
            response.setStatus(ServiceResponseDTO.Status.FAILED);
            return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
        } finally {
            logger.info("End service updateNews");
        }
    }

    @PutMapping(URL.DISABLE_NEWS + "/{id}")
    public ResponseEntity disableNews(@PathVariable int id) {
        logger.info("Begin controller disableNews");
        ServiceResponseDTO response = new ServiceResponseDTO();
        try {
            newsService.disableNews(id);
            response.setMessage("Disabled");
            return new ResponseEntity(response, HttpStatus.OK);
        } catch (Exception e) {
            logger.error(e);
            response.setMessage(e.getMessage());
            response.setStatus(ServiceResponseDTO.Status.FAILED);
            return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
        } finally {
            logger.info("Begin controller disableNews");
        }
    }

    @PutMapping(URL.ENABLE_NEWS + "/{id}")
    public ResponseEntity enableNews(@PathVariable int id) {
        logger.info("Begin controller enableNews");
        ServiceResponseDTO response = new ServiceResponseDTO();
        try {
            newsService.enableNews(id);
            response.setMessage("Enable News");
            return new ResponseEntity(response, HttpStatus.OK);
        } catch (Exception e) {
            logger.error(e);
            response.setMessage(e.getMessage());
            response.setStatus(ServiceResponseDTO.Status.FAILED);
            return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
        } finally {
            logger.info("Begin controller enableNews");
        }
    }
}
