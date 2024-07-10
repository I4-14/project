package com.sparta.trello.auth.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.trello.auth.entity.QUser;
import com.sparta.trello.auth.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static com.sparta.trello.auth.entity.QUser.user;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Optional<User> findByUsername(String username) {

        User findUser = jpaQueryFactory
                .selectFrom(user)
                .where(user.username.eq(username))
                .fetchOne();

        return Optional.ofNullable(findUser);
    }

}
