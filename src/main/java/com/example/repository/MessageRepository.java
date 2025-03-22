package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.List;
import com.example.entity.Message;

public interface MessageRepository extends JpaRepository<Message, Integer> {

    List<Message> findByPostedBy(Integer postedBy);
    
}
