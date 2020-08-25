package com.example.springdemo.repositories;

import com.example.springdemo.entity.AccountRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
@Repository
public interface AccountRoleRepository extends JpaRepository<AccountRole, Integer> {
    List<AccountRole> findAll();

    List<AccountRole> findAllByAccountId(int accountId);

    AccountRole findByAccountIdAndRoleId(int accountId, int roleId);

}
