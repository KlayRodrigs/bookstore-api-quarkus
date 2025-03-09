package org.acme.services;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.acme.dtos.UserDTO;
import org.acme.entities.User;
import org.acme.repositories.BookRepository;
import org.acme.repositories.UserRepository;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class UserService {
    @Inject
    UserRepository userRepository;

    public int countUsers() throws SQLException {
        return userRepository.countUsers();
    }

    public List<User> getUsers() throws SQLException {
        return userRepository.getUsers();
    }

    public User getUserById(Long id) throws SQLException {
        return userRepository.getUserById(id);
    }

    public Optional<User> getUserByCpf(String cpf) throws SQLException {
        return Optional.of(userRepository.getUserByCpf(cpf));
    }

    public Long saveUser(UserDTO userDTO) throws SQLException {
        Long newUserId = userRepository.saveUser(userDTO);
        return newUserId;
    }

    public void updateUser(Long id, UserDTO userDTO) throws SQLException {
        userRepository.updateUser(id, userDTO);
    }

    public void deleteUser(Long id) throws SQLException {
        userRepository.deleteUser(id);
    }
}