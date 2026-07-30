package com.openclassroom.chatopapi.services;

import com.openclassroom.chatopapi.dto.UserDto;
import com.openclassroom.chatopapi.model.User;

public interface UserService {
     User save(User user);
     UserDto findById(Integer id);

}
