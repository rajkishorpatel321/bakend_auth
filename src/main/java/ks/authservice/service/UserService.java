package ks.authservice.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ks.authservice.entity.User;
import ks.authservice.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public boolean authUser(User user) {
    	
    	User savedUser = userRepository.findByUsername(user.getUsername());
    	if(savedUser!= null) {
    		if(user.getPassword().equals(savedUser.getPassword())) {
    			return true;
    		}
    		return false;
    	}
    	else {
    		return false;
    	}
    }
    public User createUser(User user) {
        // In a real application, ensure to hash the password before storing
        return userRepository.save(user);
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User updateUser(Long id, User userDetails) {
        return userRepository.findById(id).map(user -> {
            user.setUsername(userDetails.getUsername());
            user.setPassword(userDetails.getPassword());
            return userRepository.save(user);
        }).orElseGet(() -> {
            userDetails.setId(id);
            return userRepository.save(userDetails);
        });
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}