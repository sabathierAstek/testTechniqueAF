package com.example.demo;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void shouldRegisterUserSuccessfully() throws Exception {
        String userJson = "{ \"name\": \"Pierre\", \"age\": 20, \"country\": \"France\", \"email\": \"pierre@example.com\" }";

        mockMvc.perform(post("/api/users/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(userJson))
                .andExpect(status().isCreated());
    }

    @Test
    public void shouldReturnBadRequestForInvalidAge() throws Exception {
        String userJson = "{ \"name\": \"Pierre\", \"age\": 17, \"country\": \"France\", \"email\": \"pierre@example.com\" }";

        mockMvc.perform(post("/api/users/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(userJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldReturnBadRequestForInvalidCountry() throws Exception {
        String userJson = "{ \"name\": \"Pierre\", \"age\": 20, \"country\": \"USA\", \"email\": \"pierre@example.com\" }";

        mockMvc.perform(post("/api/users/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(userJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldGetUserByIdSuccessfully() throws Exception {
        String userJson = "{ \"name\": \"Marie\", \"age\": 25, \"country\": \"France\", \"email\": \"marie@example.com\" }";

        String userResponse = mockMvc.perform(post("/api/users/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(userJson))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        User registeredUser = objectMapper.readValue(userResponse, User.class);

        mockMvc.perform(get("/api/users/" + registeredUser.getId())
                .param("param", "someValue"))
                .andExpect(status().isOk());
    }
}
