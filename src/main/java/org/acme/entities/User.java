package org.acme.entities;

import jakarta.persistence.*;
import lombok.Data;

@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String cpf;
    private String email;
    private String name;
    private String phone;
    private String street;
    private String houseNumber;
    private String neighborhood;
    private String postalCode;
    private String city;
    private String state;
}