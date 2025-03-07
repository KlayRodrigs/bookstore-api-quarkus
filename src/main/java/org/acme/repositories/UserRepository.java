package org.acme.repositories;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.acme.entities.UserEntity;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class UserRepository {
    @Inject
    protected DataSource dataSource;

    public List<UserEntity> getUsers() throws SQLException {
        List<UserEntity> userEntities = new ArrayList<>();

        String sql = "SELECT id, cpf, email, name, phone, street, house_number, neighborhood, postal_code, city, state FROM \"userEntity\"";

        try (Connection connection = dataSource.getConnection();
                PreparedStatement stmt = connection.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                UserEntity userEntity = new UserEntity();
                userEntity.setId(rs.getLong("id"));
                userEntity.setCpf(rs.getString("cpf"));
                userEntity.setEmail(rs.getString("email"));
                userEntity.setName(rs.getString("name"));
                userEntity.setPhone(rs.getString("phone"));
                userEntity.setStreet(rs.getString("street"));
                userEntity.setHouseNumber(rs.getString("house_number"));
                userEntity.setNeighborhood(rs.getString("neighborhood"));
                userEntity.setPostalCode(rs.getString("postal_code"));
                userEntity.setCity(rs.getString("city"));
                userEntity.setState(rs.getString("state"));
                userEntities.add(userEntity);
            }
        }

        return userEntities;
    }

    public UserEntity getUserById(long id) throws SQLException {
        String sql = "SELECT id, cpf, email, name, phone, street, house_number, neighborhood, postal_code, city, state FROM \"userEntity\" WHERE id = ?";
        UserEntity userEntity = null;

        try (Connection connection = dataSource.getConnection();
                PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    userEntity = new UserEntity();
                    userEntity.setId(rs.getLong("id"));
                    userEntity.setCpf(rs.getString("cpf"));
                    userEntity.setEmail(rs.getString("email"));
                    userEntity.setName(rs.getString("name"));
                    userEntity.setPhone(rs.getString("phone"));
                    userEntity.setStreet(rs.getString("street"));
                    userEntity.setHouseNumber(rs.getString("house_number"));
                    userEntity.setNeighborhood(rs.getString("neighborhood"));
                    userEntity.setPostalCode(rs.getString("postal_code"));
                    userEntity.setCity(rs.getString("city"));
                    userEntity.setState(rs.getString("state"));
                }
            }
        }

        return userEntity;
    }

    public void addUsers(List<UserEntity> userEntities) throws SQLException {
        String sql = "INSERT INTO \"userEntity\" (cpf, email, name, phone, street, house_number, neighborhood, postal_code, city, state) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = dataSource.getConnection();
                PreparedStatement stmt = connection.prepareStatement(sql)) {
            for (UserEntity userEntity : userEntities) {
                stmt.setString(1, userEntity.getCpf());
                stmt.setString(2, userEntity.getEmail());
                stmt.setString(3, userEntity.getName());
                stmt.setString(4, userEntity.getPhone());
                stmt.setString(5, userEntity.getStreet());
                stmt.setString(6, userEntity.getHouseNumber());
                stmt.setString(7, userEntity.getNeighborhood());
                stmt.setString(8, userEntity.getPostalCode());
                stmt.setString(9, userEntity.getCity());
                stmt.setString(10, userEntity.getState());
                stmt.addBatch();
            }
            stmt.executeBatch();
        }
    }

    public void updateUser(UserEntity userEntity) throws SQLException {
        String sql = "UPDATE \"userEntity\" SET cpf = ?, email = ?, name = ?, phone = ?, street = ?, house_number = ?, neighborhood = ?, postal_code = ?, city = ?, state = ? WHERE id = ?";

        try (Connection connection = dataSource.getConnection();
                PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, userEntity.getCpf());
            stmt.setString(2, userEntity.getEmail());
            stmt.setString(3, userEntity.getName());
            stmt.setString(4, userEntity.getPhone());
            stmt.setString(5, userEntity.getStreet());
            stmt.setString(6, userEntity.getHouseNumber());
            stmt.setString(7, userEntity.getNeighborhood());
            stmt.setString(8, userEntity.getPostalCode());
            stmt.setString(9, userEntity.getCity());
            stmt.setString(10, userEntity.getState());
            stmt.setLong(11, userEntity.getId());
            stmt.executeUpdate();
        }
    }

    public void deleteUser(long id) throws SQLException {
        String sql = "DELETE FROM \"userEntity\" WHERE id = ?";

        try (Connection connection = dataSource.getConnection();
                PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
        }
    }
}