package com.techgroup.techmanage.service;

import java.util.List;

import com.techgroup.techmanage.model.User;

public interface IUserService 
{
	User createUser(User user);
	List<User> getAllUsers();
	User getUserById(Long id);
	User getUserByEmail(String email);
	User updateUser(Long id, User user);
	void deleteUser(Long id);
}
