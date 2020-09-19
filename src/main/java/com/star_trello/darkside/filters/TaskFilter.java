package com.star_trello.darkside.filters;

import com.star_trello.darkside.model.Task;
import com.star_trello.darkside.repo.TaskRepo;
import com.star_trello.darkside.service.UserSessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class TaskFilter extends OncePerRequestFilter {

    @Autowired
    TaskRepo taskRepo;

    @Autowired
    UserSessionService userSessionService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String path = request.getServletPath();
        System.out.println(path);
        if (path.matches("/tasks/\\d+/.*")) {
            // e.g. path = "/tasks/1/priority/change" - split by "/" = ["", "tasks", "1", "priority", "change"]
            int taskId = Integer.parseInt(path.split("/")[2]);
            if (!taskRepo.existsById(taskId)) {
                response.sendError(HttpStatus.NOT_FOUND.value());
            }
            Task task = taskRepo.getById(taskId);
            request.setAttribute("task", task);
        }

        filterChain.doFilter(request, response);
    }
}