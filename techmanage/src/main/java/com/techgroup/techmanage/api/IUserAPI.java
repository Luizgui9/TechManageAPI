package com.techgroup.techmanage.api;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.techgroup.techmanage.model.User;

@RequestMapping("/api/users")
public interface IUserAPI 
{
	@PostMapping
	ResponseEntity<User> createUser(@RequestBody User user);
	
	@GetMapping
	ResponseEntity<List<User>> getAllUsers();
	
	@GetMapping("/{id}")
	ResponseEntity<User> getUserById(@PathVariable Long id);
	
	@GetMapping("/email/{email}")
	public ResponseEntity<User> getUserByEmail(@PathVariable String email);
	
	@PutMapping("/{id}")
	ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User user);
	
	@DeleteMapping("/{id}")
	ResponseEntity<Void> deleteUser(@PathVariable Long id);
}
