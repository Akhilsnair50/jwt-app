package com.akhil.jwt;

import com.akhil.jwt.controller.UserController;
import com.akhil.jwt.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.mockito.Mockito.when;
@WebMvcTest
public class Usernametests {
    @Autowired private MockMvc mockMvc;
    @Mock
    private UserService userService;
    @InjectMocks
    private UserController userController;

    @Test
    void testIsUserNameAvailable() throws Exception {
        // Arrange
        String username = "testuser";
        when(userService.isUserNameAvailable(username)).thenReturn(true);

        // Act & Assert
        mockMvc.perform(get("/api/users/check-username/{username}", username))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }
}
