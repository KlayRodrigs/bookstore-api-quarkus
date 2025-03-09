package org.acme.repositories;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.acme.dtos.UserDTO;
import org.acme.entities.User;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class UserRepository {
    @Inject
    protected DataSource dataSource;

    public int countUsers() throws SQLException {
        String sql = "SELECT COUNT(*) FROM users";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            var rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        }
        return 0;
    }

    public List<User> getUsers() throws SQLException {
        List<User> userList = new ArrayList<>();

        String sql = "SELECT id, cpf, email, name, phone, street, house_number, neighborhood, postal_code, city, state " +
                     "FROM users";

        try (Connection connection = dataSource.getConnection();
                PreparedStatement stmt = connection.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                User user = this.getUserFromResultSet(rs);
                userList.add(user);
            }
        }

        return userList;
    }

    public User getUserById(Long id) throws SQLException {
        String sql = "SELECT id, cpf, email, name, phone, street, house_number, neighborhood, postal_code, city, state " +
                     "FROM users WHERE id = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setLong(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return getUserFromResultSet(rs);
                } else {
                    return null;
                }
            }
        }
    }

    public User getUserByCpf(String cpf) throws SQLException {
        String sql = "SELECT id, cpf, email, name, phone, street, house_number, neighborhood, postal_code, city, state " +
                     "FROM users WHERE cpf = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
           stmt.setString(1, cpf);
           var rs = stmt.executeQuery();
           if (rs.next()) {
               return getUserFromResultSet(rs);
           } else {
               return null;
           }
        }

    }

    public Long saveUser(UserDTO userDTO) throws SQLException {
        String sql = "INSERT INTO users (cpf, email, name, phone, street, house_number, neighborhood, postal_code, city, state) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = dataSource.getConnection()) {
            var stmt = this.createUser(userDTO, connection, sql);
            int insertedRows = stmt.executeUpdate();
            if (insertedRows > 0) {
                var rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    Long newUserId = rs.getLong(1);
                    return newUserId;
                }
            }
        }
        return null;
    }

    public void updateUser(Long id, UserDTO userDTO) throws SQLException {
        String sql = "UPDATE users " +
                     "SET cpf = ?, email = ?, name = ?, phone = ?, street = ?, house_number = ?, neighborhood = ?, postal_code = ?, city = ?, state = ? " +
                     "WHERE id = ?";

        try (Connection connection = dataSource.getConnection()) {
            var stmt = this.createUser(userDTO, connection, sql);
            stmt.setLong(11, id);
            stmt.executeUpdate();
        }
    }

    public void deleteUser(long id) throws SQLException {
        String sql = "DELETE FROM users " +
                     "WHERE id = ?";

        try (Connection connection = dataSource.getConnection();
                PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
        }
    }

    private User getUserFromResultSet(ResultSet rs) throws SQLException {
        User user = new User();

        user.setId(rs.getLong("id"));
        user.setCpf(rs.getString("cpf"));
        user.setEmail(rs.getString("email"));
        user.setName(rs.getString("name"));
        user.setPhone(rs.getString("phone"));
        user.setStreet(rs.getString("street"));
        user.setHouseNumber(rs.getString("house_number"));
        user.setNeighborhood(rs.getString("neighborhood"));
        user.setPostalCode(rs.getString("postal_code"));
        user.setCity(rs.getString("city"));
        user.setState(rs.getString("state"));

        return user;
    }

    private PreparedStatement createUser(UserDTO userDTO, Connection connection, String sql) throws SQLException {
        PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

        stmt.setString(1, userDTO.cpf());
        stmt.setString(2, userDTO.email());
        stmt.setString(3, userDTO.email());
        stmt.setString(4, userDTO.phone());
        stmt.setString(5, userDTO.street());
        stmt.setString(6, userDTO.houseNumber());
        stmt.setString(7, userDTO.neighborhood());
        stmt.setString(8, userDTO.postalCode());
        stmt.setString(9, userDTO.city());
        stmt.setString(10, userDTO.state());

        return stmt;
    }
}