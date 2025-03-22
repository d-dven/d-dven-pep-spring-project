package com.example.controller;
import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.*;

import com.example.service.AccountService;
import com.example.service.MessageService;
import com.example.entity.Account;
import com.example.entity.Message;

import java.util.Optional;
import java.util.Map;
import java.util.List;



/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
@RestController
public class SocialMediaController {

    private AccountService accountService;
    private MessageService messageService;

    @Autowired
    public SocialMediaController(AccountService accountService, MessageService messageService) {
        this.accountService = accountService;
        this.messageService = messageService;
    }

    //===================================== Create Account : not tested  ==============================================
    @PostMapping("/register")
    public ResponseEntity<Account> createAccount(@RequestBody Account a) {

        //Check if Username is not blank and Password is long enough
        if (a.getUsername().length() > 0 && a.getPassword().length() > 3) { 

            //Attempt to Create Acc
            Optional<Account> newAcc = accountService.createAccount(a);
        
            if (newAcc.isPresent()) {

                return ResponseEntity.ok(newAcc.get());

            }
            
            // Return 409 status if unable to create acc
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        }

        //Return 400 status for Client Error
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }

    //===================================== Login : not implemented ==============================================

    @PostMapping("/login")
    public ResponseEntity<Account> loginToAccount(@RequestBody Account a ) {
        
        Optional<Account> loggedInAcc = accountService.loginToAccount(a);

        if (loggedInAcc.isPresent()) {
            return ResponseEntity.ok(loggedInAcc.get());
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
    }

    //===================================== Create Message : not implemented ==============================================

    @PostMapping("/messages")
    public ResponseEntity<Message> createMessage(@RequestBody Message m ) {

        //check if messagetext is acceptable
        if (m.getMessageText().length() > 0 && m.getMessageText().length() <= 255) {

            Optional<Message> newMsg = messageService.createMessage(m);
            
            //check if postedby was existing user
            if (newMsg.isPresent()) {

                return ResponseEntity.ok(newMsg.get());

            }
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }

    //===================================== Get All Messages : not implemented ==============================================

    @GetMapping("/messages")
    public ResponseEntity<List<Message>> getAllMessages() {

        return ResponseEntity.ok(messageService.getAllMessages());
    
    }

    //===================================== Get Message by ID : not implemented ==============================================

    @GetMapping("/messages/{messageID}")
    public ResponseEntity<Message> getMessageByID(@PathVariable int messageID) {
        
        Optional<Message> msgFound = messageService.getMessageByID(messageID);

        if (msgFound.isPresent()) {
        
            return ResponseEntity.ok(msgFound.get());
        
        }

        return ResponseEntity.ok(null);
    }

    
    //===================================== Delete Message by ID : not implemented ==============================================

    @DeleteMapping("/messages/{messageID}")
    public ResponseEntity<Integer> deleteMessageByID(@PathVariable int messageID) {
        
        Optional<Message> deletedMsg = messageService.deleteMessageByID(messageID);

        if(deletedMsg.isPresent()) {

            return ResponseEntity.ok(1);

        }
        return ResponseEntity.ok(null);
    }

    //===================================== Update Message by ID : not implemented ==============================================

    @PatchMapping("/messages/{messageID}")
    public ResponseEntity<Integer> updateMessageByID(@PathVariable int messageID, @RequestBody Map<String, String> messageTextRequested) {
        
        String messageText = messageTextRequested.get("messageText");

        //check message requirements

        if(messageText.length() > 0 && messageText.length() <= 255) {

            Optional<Message> updatedMsg = messageService.updateMessageByID(messageID, messageText);
            System.out.println("message length = " + messageText.length());
            //check if message ID exists already
            if(updatedMsg.isPresent() ) {
                return ResponseEntity.ok(1);
            }
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }

    //===================================== Get Messages by Account : not implemented ==============================================

    @GetMapping("/accounts/{accountID}/messages")
    public ResponseEntity<List<Message>> getMessagesByAccount(@PathVariable int accountID) {

        return ResponseEntity.ok(messageService.getAllMessagesByAccountID(accountID));

    }
}
