package org.acme.services;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.acme.entities.UserEntity;
import org.acme.repositories.UserRepository;

import java.sql.SQLException;
import java.util.List;

@ApplicationScoped
public class UserService {
    @Inject
    UserRepository userRepository;

    public List<UserEntity> getUsers() throws SQLException {
        return userRepository.getUsers();
    }

    public UserEntity getUserById(long id) throws SQLException {
        return userRepository.getUserById(id);
    }

    public void addUsers(List<UserEntity> userEntities) throws SQLException {
        userRepository.addUsers(userEntities);
    }

    public void updateUser(long id, UserEntity userEntity) throws SQLException {
        userEntity.setId(id);
        userRepository.updateUser(userEntity);
    }

    public void deleteUser(long id) throws SQLException {
        userRepository.deleteUser(id);
    }
}