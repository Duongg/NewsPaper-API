package com.example.springdemo.repositories;

import com.example.springdemo.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {
    List<Account> findAll();

    Account findById(int id);

    Account findByUsername(String username);
}
