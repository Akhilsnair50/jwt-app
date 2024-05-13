package com.akhil.jwt.service;

import com.akhil.jwt.dao.UserDao;
import com.akhil.jwt.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class AdminService {
    @Autowired
    private UserDao userDao;

    public List<User> getUsers() {
        return (List<User>) userDao.findAll();
    }
}
