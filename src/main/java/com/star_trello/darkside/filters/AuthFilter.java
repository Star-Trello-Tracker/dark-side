package com.star_trello.darkside.filters;


import com.star_trello.darkside.model.User;
import com.star_trello.darkside.repo.UserSessionRepo;
import com.star_trello.darkside.service.UserSessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class AuthFilter extends OncePerRequestFilter {

    @Autowired
    UserSessionRepo userSessionRepo;

    @Autowired
    UserSessionService userSessionService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String path = request.getServletPath();
        if (!path.equals("/register") && !path.equals("/login")) {
            String token = request.getHeader("Authorization");
            User user = userSessionService.getUserByToken(token);
            if (user == null) {
                response.sendError(401);
                return;
            }
            request.setAttribute("user", user);
        }

        filterChain.doFilter(request, response);
    }
}