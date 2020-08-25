package com.example.springdemo.serviceImp;

import com.example.springdemo.entity.Role;
import com.example.springdemo.repositories.RoleRepository;
import com.example.springdemo.service.RoleService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class RoleServiceImp implements RoleService {

    private static final Logger logger = Logger.getLogger(RoleServiceImp.class);

    @Autowired
    RoleRepository roleRepository;


    @Override
    public List<Role> getAllRole() throws Exception {
        logger.info("Begin service getAllRole");
        try{
            List<Role> roleList = new ArrayList<>();
            roleList = roleRepository.findAll();
            return roleList;
        }finally {
            logger.info("End service getAllRole");
        }
    }

    @Override
    public Role createRole(Role role) throws Exception {
       logger.info("Begin service createRole");
       try{
           if(roleRepository.getRoleByRoleName(role.getRoleName()) != null){
               throw new Exception("Role is exist");
           }else {
               role.setRoleName(role.getRoleName());
               role.setCreatedAt(new Timestamp(new Date().getTime()));
               role.setStatusActive(true);
               roleRepository.save(role);
               return role;
           }
       }catch (Exception e){
           e.printStackTrace();
           return null;
       }finally {
           logger.info("End service createRole");
       }

    }
}
