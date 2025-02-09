package com.techgroup.techmanage.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.techgroup.techmanage.model.User;
import com.techgroup.techmanage.model.UserType;
import com.techgroup.techmanage.service.IUserService;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest 
{
	private MockMvc mockMvc;

    @Mock
    private IUserService userService;

    @InjectMocks
    private UserController userController;

    private User user1, user2;

    @BeforeEach
    void setUp() 
    {
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
        user1 = new User(1L, "João Silva", "joao@email.com", "99999-9999", new Date(), UserType.ADMIN);
        user2 = new User(2L, "Maria Souza", "maria@email.com", "98888-8888", new Date(), UserType.VIEWER);
    }

    @Test
    void testGetAllUsers() throws Exception 
    {
        List<User> users = Arrays.asList(user1, user2);
        when(userService.getAllUsers()).thenReturn(users);

        mockMvc.perform(get("/api/users"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.length()").value(2))
               .andExpect(jsonPath("$[0].fullName").value("João Silva"));
    }

    @Test
    void testGetUserById() throws Exception 
    {
        when(userService.getUserById(1L)).thenReturn(user1);

        mockMvc.perform(get("/api/users/1"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.fullName").value("João Silva"));
    }

    @Test
    void testCreateUser() throws Exception 
    {
        when(userService.createUser(any(User.class))).thenReturn(user1);

        mockMvc.perform(post("/api/users")
               .contentType(MediaType.APPLICATION_JSON)
               .content(new ObjectMapper().writeValueAsString(user1)))
               .andExpect(status().isCreated())
               .andExpect(jsonPath("$.fullName").value("João Silva"));
    }

    @Test
    void testDeleteUser() throws Exception 
    {
        doNothing().when(userService).deleteUser(1L);

        mockMvc.perform(delete("/api/users/1"))
               .andExpect(status().isNoContent());
    }
}
