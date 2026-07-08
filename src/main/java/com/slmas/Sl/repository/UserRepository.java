package com.slmas.Sl.repository;

import com.slmas.Sl.domain.User;
import com.slmas.Sl.exceptions.NotFoundException;

import java.util.List;

public interface UserRepository {
    Integer createUser(User user);
    Integer deleteUser(String userName);
    Integer deleteUserById(Long id);
    User findUserByName(String userName) throws NotFoundException;
    List<User> getUsers();
    Integer editUser(User user) throws Exception;

}
