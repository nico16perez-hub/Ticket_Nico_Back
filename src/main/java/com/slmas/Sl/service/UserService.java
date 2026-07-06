package com.slmas.Sl.service;

import com.slmas.Sl.dto.request.UserRequestDto;
import com.slmas.Sl.dto.response.UserResponseDto;
import com.slmas.Sl.exceptions.MissingDataException;
import com.slmas.Sl.exceptions.NotFoundException;

import java.security.NoSuchAlgorithmException;
import java.util.List;

public interface UserService {
    String createUser (UserRequestDto userRequestDto) throws MissingDataException, NoSuchAlgorithmException;
    String deleteUser (String name);
    List<UserResponseDto> getUsers();
    UserResponseDto getUserByName(String userName);
    String editUser (UserRequestDto userRequestDto) throws Exception;
}
