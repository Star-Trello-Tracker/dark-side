package com.star_trello.darkside.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private NotificationType type;

    @ManyToOne
    private User initiator;
    @ManyToOne
    private User calledUser;

    @ManyToOne
    private Task task;
}
