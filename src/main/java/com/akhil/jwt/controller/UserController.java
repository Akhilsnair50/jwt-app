package com.akhil.jwt.controller;

import com.akhil.jwt.entity.User;
import com.akhil.jwt.service.UserService;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.ForeignKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

//@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @PostConstruct
    public void initRolesAndUsers() {
        userService.initUserAndRoles();
    }

    @PostMapping({"/registerNewUser"})
    public User registerNewUser(@RequestBody User user) {
        return userService.registerNew(user);
    }

    @GetMapping({"/forAdmin"})
    @PreAuthorize("hasRole('Admin')")
    public String forAdmin() {
        return "this url is only accessible to admin";
    }


    @GetMapping({"/forUser"})
    @PreAuthorize("hasRole('User')")
    public String forUser() {
        return "only for user";
    }

    @GetMapping({"/check-username/{userName}"})
    public boolean isUserNameAvailable(@PathVariable String userName) {
        System.out.println(userService.isUserNameAvailable(userName));
        return userService.isUserNameAvailable(userName);
    }

    @GetMapping({"find-by-userName/{userName}"})
    public ResponseEntity<User> findUserByUserName(@PathVariable String userName) {
        Optional<User> user = userService.findByUserName(userName);
        return user.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping({"get-image/{userName}"})
    public String findImagePath(@PathVariable String userName) {
        return userService.getUserImage(userName);
    }

    @PutMapping({"users/{userName}"})
    public ResponseEntity<User> updateUser(@PathVariable String userName, @RequestBody User updatedUser) {
        Optional<User> existingUserOptional = userService.findByUserName(userName);

        if (existingUserOptional.isPresent()) {
            User existingUser = existingUserOptional.get();
            existingUser.setUserFirstName(updatedUser.getUserFirstName());
            existingUser.setUserLastName(updatedUser.getUserLastName());

            User savedUser = userService.save(existingUser);
            return ResponseEntity.ok(savedUser);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping({"/delete/{userName}"})
    public ResponseEntity<Void> deleteUserByUserName(@PathVariable String userName) {
        Optional<User> optionalUser = userService.findByUserName(userName);
        if (optionalUser.isPresent()) {
            userService.deleteUser(userName);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
