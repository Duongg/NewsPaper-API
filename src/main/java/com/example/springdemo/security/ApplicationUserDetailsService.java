package com.example.springdemo.security;


import com.example.springdemo.entity.Account;
import com.example.springdemo.entity.AccountRole;
import com.example.springdemo.entity.Role;
import com.example.springdemo.repositories.AccountRepository;
import com.example.springdemo.repositories.AccountRoleRepository;
import com.example.springdemo.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ApplicationUserDetailsService implements UserDetailsService {
    @Autowired
    AccountRepository accountRepository;

    @Autowired
    AccountRoleRepository accountRoleRepository;

    @Autowired
    RoleRepository roleRepository;

    public ApplicationUserDetailsService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = accountRepository.findByUsername(username);

        List<AccountRole> accountRoles = accountRoleRepository.findAllByAccountId(account.getId());

        List<Role> roleList = roleRepository.findAll();
        if(account == null){
            throw new UsernameNotFoundException(username);
        }
        List<GrantedAuthority> authorityList = new ArrayList<GrantedAuthority>();
        accountRoles.forEach((roleOfAccount) ->{
            roleList.forEach((roleOfListRole) ->{
                if(roleOfAccount.getRoleId() == roleOfListRole.getId()){
                    authorityList.add(new SimpleGrantedAuthority(roleOfListRole.getRoleName()));
                }
            });
        });
        return new org.springframework.security.core.userdetails.User(account.getUsername(),
                account.getPassword(),
                authorityList
                );
    }
}
