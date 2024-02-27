package com.gabnob.userapi.service;

import com.gabnob.shoppingclient.dtos.UserDTO;
import com.gabnob.userapi.model.DTOConverter;
import com.gabnob.userapi.model.User;
import com.gabnob.userapi.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Test
    @DisplayName("A lista de Usuarios deve ser igual")
    public void testListAllUsers() {
        List<User> users = new ArrayList<>();
        users.add(getUser(1, "UserName", "123"));
        users.add(getUser(2, "UserName 2", "321"));

        Mockito.when(userRepository.findAll()).thenReturn(users);
        List<UserDTO> userReturn = userService.getAll();
        Assertions.assertEquals(2, userReturn.size());
    }

    @Test
    @DisplayName("Usuario salvo deve ser igual ao criado")
    public void testSaveUser() {
        User userDB = getUser(1, "UserName", "123");
        UserDTO userDTO = DTOConverter.convert(userDB);

        Mockito.when(userRepository.save(Mockito.any())).thenReturn(userDB);

        UserDTO user = userService.save(userDTO);
        Assertions.assertEquals("UserName", user.getNome());
        Assertions.assertEquals("123", user.getCpf());
    }

    @Test
    @DisplayName("Usuario editado deve ser igual ao retornado")
    public void testEditUser() {
        User userDB = getUser(1, "UserName", "123");

        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(userDB));
        Mockito.when(userRepository.save(Mockito.any())).thenReturn(userDB);

        UserDTO userDTO = DTOConverter.convert(userDB);
        userDTO.setEndereco("novo-endereco");
        userDTO.setTelefone("6767-6767");

        UserDTO user = userService.editUser(1L, userDTO);
        Assertions.assertEquals("novo-endereco", user.getEndereco());
        Assertions.assertEquals("6767-6767", user.getTelefone());
    }

    public static User getUser(Integer id, String nome, String cpf) {
        User user = new User();
        user.setId(id);
        user.setNome(nome);
        user.setCpf(cpf);
        user.setEndereco("endereco");
        user.setTelefone("5432");
        return user;
    }



}
