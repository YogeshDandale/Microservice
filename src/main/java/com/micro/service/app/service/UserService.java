package com.micro.service.app.service;

import java.util.List;

import com.micro.service.app.exception.UserNotFoundException;
import com.micro.service.app.model.User;

public interface UserService {

	User saveUser(User user, String authTokenHeader);

	List<User> getAllData(String authTokenHeader);

	User getDataById(String userId, String authTokenHeader)throws UserNotFoundException;

}
