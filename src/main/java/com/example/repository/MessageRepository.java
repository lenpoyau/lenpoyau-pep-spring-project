package com.example.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.example.entity.Message;

/*
 * Jpa repository for entity Message
 */
public interface MessageRepository extends JpaRepository<Message, Integer>{

    @Query(value = "SELECT * FROM message WHERE message_id = ?1", nativeQuery = true)
    Optional<Message> findMessageByID(int id);
    
    @Modifying
    @Query(value="delete from message m WHERE m.message_id = ?1", nativeQuery=true)
    int deleteMessagePerID(int id);
}
