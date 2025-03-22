package com.example.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Message;
import com.example.entity.Account;

import com.example.repository.MessageRepository;
import com.example.repository.AccountRepository;

import java.util.List;
import java.util.Optional;

@Service
public class MessageService {

    private MessageRepository mrepo;
    private AccountRepository arepo;

    @Autowired
    public MessageService(MessageRepository mrepo, AccountRepository arepo){
        this.mrepo = mrepo;
        this.arepo = arepo;
    }

    //======================================== Create Message : DONE ================================================
    
    public Optional<Message> createMessage(Message a) {

        //check if postedby user exists
        Optional<Account> userPosting = arepo.findById(a.getPostedBy());

        if (userPosting.isPresent()) {
            return Optional.of(mrepo.save(a));
        }
        
       return Optional.empty();

    }

    //======================================== Get all Messages : DONE ================================================
    
    public List<Message> getAllMessages() {
        return mrepo.findAll();
    }

    //======================================== Get Message By ID : DONE ================================================

    public Optional<Message> getMessageByID(int id) {

        return mrepo.findById(id);

    }

    //======================================== Delete Message By ID : DONE ================================================
    
    public Optional<Message> deleteMessageByID(int id) {

        Optional<Message> msgToDelete = mrepo.findById(id);
        
        if(msgToDelete.isPresent()) {
            mrepo.deleteById(id);
            return msgToDelete;
        }
        return Optional.empty();
    }

    //======================================== Update Message By ID : not implemented ================================================
    
    public Optional<Message> updateMessageByID(int id, String newText) {

        Optional<Message> msgToUpdate = mrepo.findById(id);
        
        if(msgToUpdate.isPresent()) {
            msgToUpdate.get().setMessageText(newText);
            mrepo.save(msgToUpdate.get());
            return msgToUpdate;
        }

        return Optional.empty();
    }

    //======================================== Get all Messages By Account ID : not implemented ================================================

    public List<Message> getAllMessagesByAccountID(int id) {

        return mrepo.findByPostedBy(id);
    }




}
