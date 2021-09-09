package com.urso.user.service;

import com.urso.exception.UserNotFoundException;
import com.urso.user.entity.User;
import com.urso.user.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.NotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> listUsers(){
        return userRepository.findAll();
    }

    public User getUserById(Long id) throws UserNotFoundException {
        Optional<User> user = userRepository.findById(id);
        return user.orElseThrow(()-> new UserNotFoundException("User: "+id +" not found in database"));
    }


}
