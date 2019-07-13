package com.example.spring_task.service;

import com.example.spring_task.model.Role;
import com.example.spring_task.model.User;
import com.example.spring_task.repository.RoleRepository;
import com.example.spring_task.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class UserService {

    UserRepository userRepository;
    RoleRepository roleRepository;
    PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public boolean passwordCheck(String password, String password_confirm){
        if (password.equals(password_confirm)){
            return true;
        }
        return false;
    }

    public void registerUser(User user){
        user.addRole(roleRepository.getOne(1L));
        //szyfrowanie hasła
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    public boolean userCheck(String email, String password){
        return false;
    }

    public void loginUser(){

    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Role getAdminRole(){
        return roleRepository.getOne(2L);
    }

    public User getUserById(Long user_id){
        return userRepository.getOne(user_id);
    }

    public void addAdminRole(Long user_id){
        User user = getUserById(user_id);
        user.addRole(getAdminRole());
        userRepository.save(user);
    }

    public void subAdminRole(Long user_id){
        User user = getUserById(user_id);
        user.subRole(getAdminRole());
        userRepository.save(user);
    }

}
