package com.techgroup.techmanage.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.techgroup.techmanage.model.User;
import com.techgroup.techmanage.model.UserType;
import com.techgroup.techmanage.repository.IUserRepository;
import com.techgroup.techmanage.service.impl.UserService;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest 
{
	@Mock
	private IUserRepository userRepository;
	
	@InjectMocks
	private UserService userService;
	
	private User user1, user2;
	 
	@BeforeEach
	void setUp()
	{
		user1 = new User(1L, "João Silva", "joao@email.com", "99999-9999", new Date(), UserType.ADMIN);
        user2 = new User(2L, "Maria Souza", "maria@email.com", "98888-8888",  new Date(), UserType.VIEWER);
	}
	
	@Test
	void testCreateUser()
	{
		when(userRepository.save(any(User.class))).thenReturn(user1);
		User createdUser = userService.createUser(user1);
		assertNotNull(createdUser);
		assertEquals("João Silva", createdUser.getFullName());
	}
	
	@Test
    void testGetAllUsers() 
	{
        when(userRepository.findAll()).thenReturn(Arrays.asList(user1, user2));
        List<User> users = userService.getAllUsers();
        assertEquals(2, users.size());
    }

    @Test
    void testGetUserById() 
    {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user1));
        User foundUser = userService.getUserById(1L);
        assertEquals("João Silva", foundUser.getFullName());
    }

    @Test
    void testUpdateUser() 
    {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user1));
        when(userRepository.save(any(User.class))).thenReturn(user1);

        User updatedUser = new User();
        updatedUser.setFullName("João Atualizado");
        updatedUser.setEmail("novo@email.com");

        User result = userService.updateUser(1L, updatedUser);
        assertEquals("João Atualizado", result.getFullName());
        assertEquals("novo@email.com", result.getEmail());
    }

    @Test
    void testDeleteUser() 
    {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user1));
        assertDoesNotThrow(() -> userService.deleteUser(1L));
        verify(userRepository, times(1)).deleteById(1L);
    }
}
