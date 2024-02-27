package com.gabnob.userapi.controller;

import com.gabnob.shoppingclient.dtos.UserDTO;
import com.gabnob.userapi.model.DTOConverter;
import com.gabnob.userapi.service.UserService;
import com.gabnob.userapi.service.UserServiceTest;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @InjectMocks
    private UserController userController;

    @Mock
    private UserService userService;

    private MockMvc mockMvc;

    @Test
    @DisplayName("Pegar lista de usuarios deve dar stt200")
    public void testListUsers() throws Exception {
        List<UserDTO> users = new ArrayList<>();
        users.add(DTOConverter.convert(UserServiceTest.getUser(1, "Nome 1", "123")));

        Mockito.when(userService.getAll()).thenReturn(users);

        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.get("/user"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String resp = result.getResponse().getContentAsString();
        Assertions.assertEquals("[{\"nome\":\"Nome 1\"," +
                        "\"cpf\":\"123\",\"endereco\":\"endereco\",\"key\":null," +
                        "\"email\":null,\"telefone\":\"5432\",\"dataCadastro\":null}]"
                , resp);

    }


    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }


}