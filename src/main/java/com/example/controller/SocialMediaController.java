package com.example.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.service.AccountService;
import com.example.service.MessageService;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
@RestController
public class SocialMediaController {
    MessageService messageService;
    AccountService accountService;
    
    @Autowired
    public SocialMediaController(MessageService messageService, AccountService accountService) {
        this.messageService = messageService;
        this.accountService = accountService;
    }

    @PostMapping("/register")
    public ResponseEntity<Account> createAccount(@RequestBody Account newAccount) {
        
        Optional<Account> accountOptional = accountService.retrieveAccountByUsername(newAccount.getUsername());
        if(!accountOptional.isEmpty() ) {
            System.out.println("User account already exists. Conflict error 409.");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        }

        if( accountOptional.isEmpty() && newAccount.getPassword().length() >= 4 && !newAccount.getUsername().isEmpty()  ) {
            accountService.addAccount(newAccount);
            System.out.println("New login account successfully created");
            return ResponseEntity.status(HttpStatus.OK).body(newAccount);
        } 
        /* if we get to this line then user account creation failed for some other reasons */
        System.out.println("Your username or password is invalid");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);

    }

    @PostMapping("/login")
    public ResponseEntity<Account> userLogin(@RequestBody Account loginAccount) {
        
        Optional<Account> accountOptional = accountService.retrieveAccountByUsername(loginAccount.getUsername());
        /**
             * Checks to ensure that username being passed isn't blank.
             * Checks to ensure that password is at least 4 characters long
             * Checks to ensure that an account with that username does already exist
             */

        if(!accountOptional.isEmpty() ) {
                String expectedUname = accountOptional.get().getUsername();
                String expectedPass = accountOptional.get().getPassword();
                String actualUserName = loginAccount.getUsername();
                String actualPassword = loginAccount.getPassword();

                if(expectedUname.equals(actualUserName) && expectedPass.equals(actualPassword)) {
                    System.out.println("User login successful.");
                    return ResponseEntity.status(HttpStatus.OK).body(accountOptional.get());
                    
                }
        }

        /* if we've reach the lines below then user login has failed */ 
        System.out.println("user login has failed. Please check username & password are valid");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
    }
    
    /*
     * Retrieve all messages that are currently saved/persisted in the db
     */
    @GetMapping("/messages")
    public List<Message> getAllMessages() {
        return messageService.getAllMessages(); 
    }

    /*
     * Retrieve all user accounts that are currently saved/
     */
    @GetMapping("/accounts")
    public List<Account> getAllAccounts() {
        return accountService.getAllAccounts(); 
    }
}
