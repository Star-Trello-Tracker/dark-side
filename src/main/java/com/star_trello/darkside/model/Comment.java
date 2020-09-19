package com.star_trello.darkside.model;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    private User creator;

    private String text;

    @OneToMany(fetch = FetchType.EAGER)
    private List<User> whoCalled;

    private long created;

    private int taskId;
}
