package com.meriem.casavia.services;

import com.meriem.casavia.entities.Message;
import com.meriem.casavia.entities.Person;
import com.meriem.casavia.entities.User;


import java.util.List;

public interface MessageService {
    Message sendMessage(Message message);
    List<Message> getConversation(User user, Person partner);
}
