package com.star_trello.darkside.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

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
    @ManyToMany
    private List<User> observers;

    @OneToMany
    private List<Comment> comments;

    private LocalDateTime refreshed;
}
