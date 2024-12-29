package ks.authservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ks.authservice.entity.User;
import ks.authservice.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
    	System.out.println("insed the postmaping");
    	System.out.println(user.getUsername());
        User createdUser = userService.createUser(user);
        return ResponseEntity.ok(createdUser);
    }
    
    @PostMapping("/authenticate")
    public ResponseEntity<String> authenticate(@RequestBody User user) {
        if (isValidUser(user)) {
            return ResponseEntity.ok("Authentication approved with username "+user.getUsername());
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authentication failed");
        }
    }

    private boolean isValidUser(User user) {
    	if(userService.authUser(user))
    		return true;
    	else
    		return false;
    }
}