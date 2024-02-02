package com.akhil.jwt.controller;

import com.akhil.jwt.entity.User;
import com.akhil.jwt.service.UserService;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.ForeignKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

//@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @PostConstruct
    public void initRolesAndUsers(){
        userService.initUserAndRoles();
    }

    @PostMapping({"/registerNewUser"})
    public User registerNewUser(@RequestBody User user){
        return userService.registerNew(user);
    }

    @GetMapping({"/forAdmin"})
    @PreAuthorize("hasRole('Admin')")
    public String forAdmin(){
        return "this url is only accessible to admin";
    }


    @GetMapping({"/forUser"})
    @PreAuthorize("hasRole('User')")
    public String forUser(){
        return "only for user";
    }

//    @GetMapping({"/forUser"})
////    @PreAuthorize("hasRole('User')")
//    public String forUser(){
//        return "only for user";
//    }
}
