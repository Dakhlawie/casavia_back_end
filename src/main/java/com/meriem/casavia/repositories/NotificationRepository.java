package com.meriem.casavia.repositories;


import com.meriem.casavia.entities.Notification;
import com.meriem.casavia.entities.Person;
import com.meriem.casavia.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findByPersonOrderByDateDesc(Person person);
    List<Notification> findByUser(User u);
    long countByPersonAndSeenFalse(Person p);
    List<Notification> findByPersonAndSeenFalse(Person p);
}
