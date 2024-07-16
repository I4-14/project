package com.sparta.trello.common.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.trello.auth.entity.Role;
import com.sparta.trello.auth.security.UserDetailsServiceImpl;
import com.sparta.trello.common.exception.CustomException;
import com.sparta.trello.common.exception.ErrorEnum;
import com.sparta.trello.common.exception.ExceptionDto;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;

@RequiredArgsConstructor
@Slf4j(topic = "JWT 검증 및 인가")
public class JwtAuthorizationFilter extends OncePerRequestFilter {
    private final JwtUtil jwtUtil;
    private final UserDetailsServiceImpl userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain filterChain) throws ServletException, IOException {
        String tokenValue = jwtUtil.getTokenFromRequest(req);

        if (StringUtils.hasText(tokenValue)) {
            tokenValue = jwtUtil.substringToken(tokenValue);

            try {
                if (!jwtUtil.validateToken(tokenValue)) {
                    throw new CustomException(ErrorEnum.TOKEN_VALIDATE);
                }

                Claims info = jwtUtil.getUserInfoFromToken(tokenValue);
                String username = info.getSubject();
                Role role = jwtUtil.getRoleFromToken(tokenValue);

                setAuthentication(username, role);
            } catch (CustomException e) {
                handleException(res, e.getStatusEnum().getMsg(), HttpServletResponse.SC_UNAUTHORIZED);
                return;
            } catch (Exception e) {
                log.error("Authentication Error: {}", e.getMessage());
                handleException(res, "Authentication Error: " + e.getMessage(), HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                return;
            }
        }
        filterChain.doFilter(req, res);
    }

    public void setAuthentication(String username, Role role) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        Authentication authentication = createAuthentication(username, role);
        context.setAuthentication(authentication);

        SecurityContextHolder.setContext(context);
    }

    private Authentication createAuthentication(String username, Role role) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        Collection<? extends GrantedAuthority> authorities = getAuthorities(role);
        return new UsernamePasswordAuthenticationToken(userDetails, null, authorities);
    }

    private Collection<? extends GrantedAuthority> getAuthorities(Role role) {
        return Collections.singletonList(new SimpleGrantedAuthority(role.name()));
    }

    private void handleException(HttpServletResponse res, String message, int statusCode) throws IOException {
        res.setStatus(statusCode);
        res.setContentType("application/json");
        res.setCharacterEncoding("UTF-8");
        ExceptionDto response = new ExceptionDto(message, statusCode);
        res.getWriter().write(new ObjectMapper().writeValueAsString(response));
    }
}