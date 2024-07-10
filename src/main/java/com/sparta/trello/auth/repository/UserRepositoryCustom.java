package com.sparta.trello.auth.repository;

import com.sparta.trello.auth.entity.User;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepositoryCustom {
    Optional<User> findByUsername(String username);
}
