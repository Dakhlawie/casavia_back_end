package com.meriem.casavia.repositories;


import com.meriem.casavia.entities.Message;
import com.meriem.casavia.entities.Person;
import com.meriem.casavia.entities.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {

}
