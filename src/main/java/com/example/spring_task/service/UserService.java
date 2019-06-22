package com.example.spring_task.service;

import com.example.spring_task.model.User;
import com.example.spring_task.repository.RoleRepository;
import com.example.spring_task.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    UserRepository userRepository;
    RoleRepository roleRepository;

    @Autowired
    public UserService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    public boolean passwordCheck(String password, String password_confirm){
        if (password.equals(password_confirm)){
            return true;
        }
        return false;
    }

    public void registerUser(User user){
        user.addRole(roleRepository.getOne(1L));
        userRepository.save(user);
    }

    public boolean userCheck(String email, String password){
        return false;
    }

    public void loginUser(){

    }

}
