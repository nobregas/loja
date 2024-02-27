package com.gabnob.userapi.service;

import com.gabnob.shoppingclient.exceptions.UserNotFoundException;
import com.gabnob.userapi.model.DTOConverter;
import com.gabnob.userapi.model.User;
import com.gabnob.shoppingclient.dtos.UserDTO;
import com.gabnob.userapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public List<UserDTO> getAll() {
        List<User> usuarios = userRepository.findAll();
        return usuarios
                .stream()
                .map(DTOConverter:: convert)
                .collect(Collectors.toList());

    }

    public UserDTO findById(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(RuntimeException::new);

        return DTOConverter.convert(user);
    }

    public UserDTO save(UserDTO userDTO) {
        userDTO.setKey(UUID.randomUUID().toString());
        userDTO.setDataCadastro(LocalDateTime.now());
        User user = userRepository.save(User.convert(userDTO));
        return DTOConverter.convert(user);
    }

    public UserDTO delete(Long userId) {
        User user = userRepository.
                findById(userId)
                .orElseThrow(RuntimeException::new);

        userRepository.delete(user);
        return DTOConverter.convert(user);
    }

    public UserDTO findByCpf(String cpf, String key) {
        User user = userRepository.findByCpfAndKey(cpf, key);
        if (user != null)
            return DTOConverter.convert(user);
        throw new UserNotFoundException();

    }

    public List<UserDTO> queryByName(String name) {
        List<User> usuarios = userRepository.queryByNomeLike(name);
        return usuarios.stream()
                .map(DTOConverter::convert)
                .collect(Collectors.toList());
    }

    public UserDTO editUser(Long userId, UserDTO userDTO) {
        User user = userRepository.findById(userId)
                .orElseThrow(RuntimeException::new);
        if (userDTO.getEmail() != null && !userDTO.getEmail().isEmpty() &&
                !user.getEmail().equals(userDTO.getEmail())){
            user.setEmail(userDTO.getEmail());
        }
        if (userDTO.getTelefone() != null && !userDTO.getTelefone().isEmpty() &&
                !user.getTelefone().equals(userDTO.getTelefone())){
            user.setTelefone(userDTO.getTelefone());
        }
        if (userDTO.getEndereco() != null && !userDTO.getEndereco().isEmpty() &&
                !user.getEndereco().equals(userDTO.getEndereco())){
            user.setEndereco(userDTO.getEndereco());
        }
        user = userRepository.save(user);
        return DTOConverter.convert(user);
    }

    public Page<UserDTO> getAllPage(Pageable page) {
        Page<User> users = userRepository.findAll(page);
        return users.map(DTOConverter::convert);
    }



}
