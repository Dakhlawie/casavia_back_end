package com.meriem.casavia.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long notification_id;
    private String title;
    private LocalDateTime date;
    private boolean seen=false;
    @Enumerated(EnumType.STRING)
    private NotificationType type;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "person_id")
    private Person person;
    @Override
    public String toString() {
        return "Notification{" +
                "notification_id=" + notification_id +
                ", title='" + title + '\'' +
                ", date=" + date +
                ", seen=" + seen +
                ", type=" + type +
                '}';
    }
}
