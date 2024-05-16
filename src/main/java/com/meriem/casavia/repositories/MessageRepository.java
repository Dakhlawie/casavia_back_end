package com.meriem.casavia.repositories;

import com.meriem.casavia.entities.Message;
import com.meriem.casavia.entities.Person;
import com.meriem.casavia.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findBySenderAndRecipient(User sender, Person recipient);
}
