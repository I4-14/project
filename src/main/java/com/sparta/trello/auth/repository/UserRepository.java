package com.sparta.trello.auth.repository;

import com.sparta.trello.auth.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
