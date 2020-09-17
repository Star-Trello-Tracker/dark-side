package com.star_trello.darkside.model;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Queue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String title;
    private String description;

    @ManyToOne
    private User creator;

    @OneToMany
    List<Task> taskList;

}
