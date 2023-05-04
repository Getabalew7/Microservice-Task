package com.codeaz.Task.Model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

/**
 The Customer class represents a customer object.
 It has properties such as id, first name, last name, middle name, email, phone number and birth date.
 This class is annotated with JPA annotations to define the mapping to the database.
 @author [getabalew7]
 @version 1.0
 @since [05-05-2023]
 */
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "middle_name")
    private String middleName;

    @Column(name = "email")
    private String email;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "birth_date")
    private LocalDate birthDate;

}
