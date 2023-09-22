package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Message;
import com.example.repository.MessageRepository;

@Service
public class MessageService {
    MessageRepository messageRepository;
    
    /*
     * The MessageRepository has been Autowired into this class via Constructor injection below. A bean for it 
     * will be injected and made available that way.
     * @param messageRepository
     */
    @Autowired
    public MessageService (MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    /*
     * Add a message and persist/record it in the db method 
     * and return this new persisted message entity
     * @param message a new entity being passed as an arg
     */
    public Message addMessage(Message message) {
        return messageRepository.save(message);
    }

    /*
     * return all message entities found in the db method
     */
    public List<Message> getAllMessage() {
        return messageRepository.findAll();
    }
}
