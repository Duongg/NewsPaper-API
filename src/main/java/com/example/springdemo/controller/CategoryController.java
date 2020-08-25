package com.example.springdemo.controller;

import com.example.springdemo.entity.Account;
import com.example.springdemo.entity.Category;
import com.example.springdemo.service.CateogoryService;
import com.example.springdemo.util.URL;
import dto.ServiceResponseDTO;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(URL.API)
public class CategoryController {
    private static final Logger logger = Logger.getLogger(CategoryController.class);

    @Autowired
    CateogoryService cateogoryService;

    @GetMapping(URL.GET_CATEGORY)
    public ResponseEntity findAllCategory(){
        logger.info("Begin controller findAllCategory");
        ServiceResponseDTO response = new ServiceResponseDTO();
        try {
            List<Category> categoryList = cateogoryService.findAllCategory();
            response.setData(categoryList);
            return new ResponseEntity(response, HttpStatus.OK);
        } catch (Exception e) {
            logger.error(e);
            response.setMessage(e.getMessage());
            response.setStatus(ServiceResponseDTO.Status.FAILED);
            return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
        } finally {
            logger.info("End controller findAllCategory");
        }
    }

    @PostMapping(URL.CREATE_CATEGORY)
    public ResponseEntity createCategory(@RequestBody Category category) {
        logger.info("Begin service createCategory");
        ServiceResponseDTO response = new ServiceResponseDTO();
        try {
            cateogoryService.createCategory(category);
            response.setMessage("Created");
            return new ResponseEntity(response, HttpStatus.OK);
        } catch (Exception e) {
            logger.error(e);
            response.setMessage(e.getMessage());
            response.setStatus(ServiceResponseDTO.Status.FAILED);
            return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
        } finally {
            logger.info("End service createCategory");
        }
    }
}
