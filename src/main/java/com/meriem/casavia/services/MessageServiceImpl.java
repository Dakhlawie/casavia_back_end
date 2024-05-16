package com.meriem.casavia.services;

import com.meriem.casavia.entities.Message;
import com.meriem.casavia.entities.Person;
import com.meriem.casavia.entities.User;
import com.meriem.casavia.repositories.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MessageServiceImpl implements MessageService{
    @Autowired
    private MessageRepository messageRepository;

    @Override
    public Message sendMessage(Message message) {
        message.setTimestamp(LocalDateTime.now());
        return messageRepository.save(message);
    }
    @Override
    public List<Message> getConversation(User user, Person partner) {
        return messageRepository.findBySenderAndRecipient(user, partner);
    }
}
