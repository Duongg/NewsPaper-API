package com.example.springdemo.service;

import com.example.springdemo.entity.Role;

import java.util.List;

public interface RoleService {

    List<Role> getAllRole() throws Exception;

    Role createRole(Role role) throws Exception;
}
