package com.example.springdemo.repositories;

import com.example.springdemo.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
    List<Role> findAll();
//
//    @Query("SELECT f FROM Role f WHERE f.roleName = :RoleName")
//    Role getRoleByName(@Param("RoleName") String roleName);

    Role getRoleByRoleName(String roleName);



}
