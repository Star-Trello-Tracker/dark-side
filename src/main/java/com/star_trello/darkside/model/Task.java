package com.star_trello.darkside.model;

import lombok.*;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String key;
    private String title;
    private String description;

    private TaskPriority priority;
    private TaskStatus status;

    @ManyToOne
    private User creator;
    @ManyToOne
    private User assignee;
    @ManyToMany(fetch = FetchType.EAGER)
    private Set<User> observers;
    @ManyToMany
    private Set<User> calledUsers;

    @OneToMany(fetch = FetchType.EAGER)
    private List<Comment> comments;

    private long refreshed;

    private String boardName;

    private int boardId;
}
