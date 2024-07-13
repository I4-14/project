package com.sparta.trello.auth.security;

import com.sparta.trello.auth.entity.User;
import com.sparta.trello.auth.repository.UserRepository;
import com.sparta.trello.common.exception.CustomException;
import com.sparta.trello.common.exception.ErrorEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new CustomException(ErrorEnum.USER_NOT_FOUND));

        return new UserDetailsImpl(user);
    }
}
