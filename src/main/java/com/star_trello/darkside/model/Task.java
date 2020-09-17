package com.star_trello.darkside.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String title;

    private String description;

    private int priority;

    @ManyToOne
    private User creator;

    @OneToOne
    private User asignee;

    private List<User> observers;

    private List<User> needAnswer;

}
