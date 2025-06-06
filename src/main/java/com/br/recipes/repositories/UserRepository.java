package com.br.recipes.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import com.br.recipes.entities.User;

public interface UserRepository extends JpaRepository<User, String> {
  UserDetails findByLogin(String login);
}
