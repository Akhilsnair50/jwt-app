package com.akhil.jwt.controller;

import com.akhil.jwt.dao.UserDao;
import com.akhil.jwt.entity.User;
import com.akhil.jwt.service.AdminService;
import com.akhil.jwt.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AdminController {
    @Autowired
    private UserService userService;

    @Autowired
    private AdminService adminService;

    @GetMapping({"/getUsers"})
    public List<User> getUsers(){
        return adminService.getUsers();
    }

}
