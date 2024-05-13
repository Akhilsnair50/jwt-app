package com.akhil.jwt.dao;

import com.akhil.jwt.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao extends CrudRepository<User,String> {
    boolean existsByUserName(String userName);


}
