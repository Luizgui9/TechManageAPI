package com.techgroup.techmanage.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.techgroup.techmanage.api.IUserAPI;
import com.techgroup.techmanage.model.User;
import com.techgroup.techmanage.service.IUserService;

@RestController
@RequestMapping("/api/users")
public class UserController implements IUserAPI 
{	
	@Autowired
	private IUserService userService;
	
	@Override
	public ResponseEntity<User> createUser (@RequestBody User user)
	{
		URI location = ServletUriComponentsBuilder.fromCurrentRequest()
	            .path("/{id}")
	            .buildAndExpand(user.getUserId())
	            .toUri();
		
		return ResponseEntity.created(location).body(userService.createUser(user));
	}
	
	@Override
	public ResponseEntity<List<User>> getAllUsers()
	{
		return ResponseEntity.ok(userService.getAllUsers());
	}
	
	@Override
	public ResponseEntity<User> getUserById(@PathVariable Long id)
	{
		return ResponseEntity.ok(userService.getUserById(id));
	}
	
	@Override
	public ResponseEntity<User> getUserByEmail(@PathVariable String email)
	{
		return ResponseEntity.ok(userService.getUserByEmail(email));
	}
	
	@Override
	public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User user)
	{
		return ResponseEntity.ok(userService.updateUser(id, user));
	}
	
	@Override
	public ResponseEntity<Void> deleteUser(@PathVariable Long id)
	{
		userService.deleteUser(id);
		return ResponseEntity.noContent().build();
	}
}
