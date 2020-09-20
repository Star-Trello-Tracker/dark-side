package com.star_trello.darkside;

import com.star_trello.darkside.controller.*;
import com.star_trello.darkside.dto.*;
import com.star_trello.darkside.model.*;
import com.star_trello.darkside.repo.QueueRepo;
import com.star_trello.darkside.repo.UserRepo;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;

import static com.star_trello.darkside.constants.UserTestConstants.*;
import static com.star_trello.darkside.constants.UtilsConstants.*;

@SpringBootTest
class DarkSideApplicationTests {


    @Autowired
    UserController userController;

    @Autowired
    AuthController authController;

    @Autowired
    TaskController taskController;

    @Autowired
    QueueController queueController;

    @Autowired
    CommentController commentController;

    @Autowired
    UserRepo userRepo;

    @Autowired
    QueueRepo queueRepo;

    @Test
    void contextLoads() {
        boolean x = true;
        Assert.assertTrue(x);
    }

    @Test
    void fullCycleUserAuthCreateQueueTaskCommentCreation() {
        User user = User.builder()
                .email(MAIL)
                .username(USERNAME)
                .password(PASSWORD)
                .build();

        authController.register(user);

        ResponseEntity<?> login = authController.login(UserCredentialsDto.builder()
                .email(MAIL)
                .password(PASSWORD)
                .build());

        UserSession userSessionBody = (UserSession) login.getBody();
        String token = userSessionBody.getToken();

        Queue queue = (Queue) queueController.createQueue(user,
                new QueueCreationDto(QUEUE_TITLE, QUEUE_DESCRIPTION))
                .getBody();

        Task task = (Task) taskController.createTask(user,
                new TaskCreationDto(TASK_TITLE, TASK_DESCRIPTION, 5, queue.getTitle(),  null, new ArrayList<>()))
                .getBody();

        // update queue
        queue = queueRepo.getByTitle(queue.getTitle());
        Assert.assertEquals(task.getKey(), queue.getTaskList().get(0).getKey());

        Comment comment = (Comment) commentController.createComment(user,
                new CommentCreationDto(task.getKey(), COMMENT_TEXT, new ArrayList<>()))
                .getBody();

        queue = queueRepo.getByTitle(queue.getTitle());
        Assert.assertEquals(COMMENT_TEXT, queue.getTaskList().get(0).getComments().get(0).getText());

        commentController.editComment(user, comment.getId(),
                new CommentCreationDto(task.getKey(), EDIT_COMMENT, new ArrayList<>()));
        queue = queueRepo.getByTitle(queue.getTitle());
        Assert.assertEquals(EDIT_COMMENT, queue.getTaskList().get(0).getComments().get(0).getText());

        commentController.deleteComment(user, comment.getId());
        queue = queueRepo.getByTitle(queue.getTitle());
        Assert.assertEquals(0, queue.getTaskList().get(0).getComments().size());

        taskController.changeTaskPriority(user, task, new CodeDto(4));
        queue = queueRepo.getByTitle(queue.getTitle());
        Assert.assertEquals(TaskPriority.BLOCKER, queue.getTaskList().get(0).getPriority());

        User userForAssigning = getUserForAssigning();

        // after changing task priority, in task will add new auto comment
        // => we need to update current task
        task = queue.getTaskList().get(0);
        taskController.assignUser(user, task, new TextDto(userForAssigning.getUsername()));
        queue = queueRepo.getByTitle(queue.getTitle());
        Assert.assertEquals(userForAssigning, queue.getTaskList().get(0).getAssignee());
        Assert.assertTrue(queue.getTaskList().get(0).getObservers().contains(user));
        Assert.assertTrue(queue.getTaskList().get(0).getObservers().contains(userForAssigning));
    }

    private User getUserForAssigning() {
        User user = User.builder()
                .email(ASSIGNEE_MAIL)
                .username(ASSIGNEE_USERNAME)
                .password(ASSIGNEE_PASSWORD)
                .build();

        authController.register(user);
        authController.login(UserCredentialsDto.builder()
                .email(ASSIGNEE_MAIL)
                .password(ASSIGNEE_PASSWORD)
                .build());

        return user;
    }

}
