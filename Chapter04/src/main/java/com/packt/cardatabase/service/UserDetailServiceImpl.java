package com.packt.cardatabase.service;

import com.packt.cardatabase.domain.User;
import com.packt.cardatabase.domain.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class UserDetailServiceImpl implements UserDetailsService {

  @Autowired
  private UserRepository repository;

  @Override
  public UserDetails loadUserByUsername(String username) {
    User currentUser = repository.findByUsername(username);
    return new org.springframework.security.core.userdetails.User(
        username,
        currentUser.getPassword(),
        true,
        true,
        true,
        true,
        AuthorityUtils.createAuthorityList(currentUser.getRole()));
  }
}
