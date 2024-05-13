package com.akhil.jwt.service;

import com.akhil.jwt.dao.RoleDao;
import com.akhil.jwt.dao.UserDao;
import com.akhil.jwt.entity.Role;
import com.akhil.jwt.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService {
    @Autowired
    private UserDao userDao;
    @Autowired
    private RoleDao roleDao;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public User registerNew(User user){

        Role role = roleDao.findById("User").get();

        Set<Role> roles = new HashSet<>();
        roles.add(role);
        user.setRole(roles);
        user.setUserPassword(getEncodedPassword(user.getUserPassword()));
        return userDao.save(user);
    }
    public void initUserAndRoles(){

        Role adminRole = new Role();
        adminRole.setRoleName("Admin");
        adminRole.setRoleDescription("Admin role");
        roleDao.save(adminRole);

        Role userRole = new Role();
        userRole.setRoleName("User");
        userRole.setRoleDescription("Default role for newly created record");
        roleDao.save(userRole);

        User adminUser = new User();
        adminUser.setUserFirstName("admin");
        adminUser.setUserLastName("admin");
        adminUser.setUserName("admin123");
        adminUser.setUserPassword(getEncodedPassword("admin@pass"));
        Set<Role> adminRoles = new HashSet<>();
        adminRoles.add(adminRole);
        adminUser.setRole(adminRoles);
        userDao.save(adminUser);

//        User user = new User();
//        user.setUserFirstName("akhil");
//        user.setUserLastName("s nair");
//        user.setUserName("akhil123");
//        user.setUserPassword(getEncodedPassword("akhil@pass"));
//        Set<Role> userRoles = new HashSet<>();
//        userRoles.add(userRole);
//        user.setRole(userRoles);
//        userDao.save(user);


    }

    public String getEncodedPassword(String password){
        return passwordEncoder.encode(password);
    }



    public boolean isUserNameAvailable(String userName) {
        return !userDao.existsByUserName(userName);
    }

    public Optional<User> findByUserName(String userName){
        return userDao.findById(userName);
    }

    public void setUserImage(String imageName,String userName){
       Optional<User> user = userDao.findById(userName);
       if(user.isPresent()){
           User user1  = user.get();
           user1.setImage(imageName);
           userDao.save(user1);
       }else {
           System.out.println("oops!!");
       }
    }

    public String getUserImage(String userName){
        Optional<User> user = userDao.findById(userName);
        return user.get().getImagePath();
    }
    public User save(User user){
        return userDao.save(user);
    }

    public void deleteUser(String userName){
        User user = userDao.findById(userName).orElse(null);
        if (user!=null){
            user.setRole(null);
            userDao.deleteById(userName);
        }

    }
}
