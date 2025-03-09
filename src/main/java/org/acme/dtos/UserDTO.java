package org.acme.dtos;

import org.acme.entities.User;

public record UserDTO(Long id, String cpf, String email, String name, String phone, String street, String houseNumber,
                      String neighborhood, String postalCode, String city, String state) {
    public static UserDTO fromUser(User user) {
        return new UserDTO(
                user.getId(),
                user.getCpf(),
                user.getEmail(),
                user.getName(),
                user.getPhone(),
                user.getStreet(),
                user.getHouseNumber(),
                user.getNeighborhood(),
                user.getPostalCode(),
                user.getCity(),
                user.getState()
        );
    }
}