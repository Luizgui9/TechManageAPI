package com.techgroup.techmanage.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.techgroup.techmanage.model.User;
import com.techgroup.techmanage.repository.IUserRepository;
import com.techgroup.techmanage.service.IUserService;

import jakarta.persistence.EntityNotFoundException;

@Service
public class UserService implements IUserService
{

	@Autowired
	private IUserRepository userRepository;
	
	@Override
	public User createUser(User user) 
	{
		return userRepository.save(user);
	}

	@Override
	public List<User> getAllUsers() 
	{
		return userRepository.findAll();
	}

	@Override
	public User getUserById(Long id) 
	{
		return userRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Usuário com ID '" + id + "' não encontrado"));
	}

	@Override
	public User getUserByEmail(String email) 
	{
		return userRepository.findByEmail(email)
				.orElseThrow(() -> new EntityNotFoundException("Usuário com e-mail '" + email + "' não encontrado"));
	}

	@Override
	public User updateUser(Long id, User user) 
	{
		User existingUser = getUserById(id);
		existingUser.update(user);
		
		return userRepository.save(existingUser);
	}

	@Override
	public void deleteUser(Long id) 
	{
		// Verifica se o usuário existe; caso contrário, lança exceção
		getUserById(id);
		userRepository.deleteById(id);
	}

}
