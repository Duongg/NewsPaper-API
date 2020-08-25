package com.example.springdemo.controller;

import com.example.springdemo.entity.Role;
import com.example.springdemo.service.RoleService;
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
public class RoleController {

    private static final Logger logger = Logger.getLogger(RoleController.class);

    @Autowired
    RoleService roleService;

    @GetMapping(URL.GET_ALL_ROLE)
    public ResponseEntity getAllRole(){
        logger.info("Begin controller getAllRole");
        ServiceResponseDTO response = new ServiceResponseDTO();
        try{
            List<Role> roleList = roleService.getAllRole();
            response.setData(roleList);
            return new ResponseEntity(response, HttpStatus.OK);
        }catch (Exception e){
            logger.error(e);
            response.setMessage(e.getMessage());
            response.setStatus(ServiceResponseDTO.Status.FAILED);
            return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
        }finally {
            logger.info("End controller getAllRole");
        }
    }

    @PostMapping(URL.CREATE_ROLE)
    public ResponseEntity createRole(@RequestBody Role role){
        logger.info("Begin service createRole");
        ServiceResponseDTO response = new ServiceResponseDTO();
        try{
            roleService.createRole(role);
            response.setMessage("Created");
            return new ResponseEntity(response, HttpStatus.OK);
        }catch (Exception e){
            logger.error(e);
            response.setMessage(e.getMessage());
            response.setStatus(ServiceResponseDTO.Status.FAILED);
            return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
        }finally {
            logger.info("End service createRole");
        }
    }
}
