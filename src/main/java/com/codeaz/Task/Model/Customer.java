package com.codeaz.Task.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
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

    @NotBlank(message = "First Name is required")
    @Column(name = "first_name")
    private String firstName;

    @NotBlank(message = "Last Name is required")
    @Column(name = "last_name")
    private String lastName;

    @NotBlank(message = "Email is required")
    @Column(name = "middle_name")
    private String middleName;

    @Email(message = "Invalid Email Address")
    @NotBlank(message = "Email Address is required")
    @Column(name = "email")
    private String email;

    @Pattern(regexp = "\\+(\\d{1,3})\\s\\d{9,10}",
            message = "Phone number must be in the format: +[country code] [phone number]")
    @Column(name = "phone_number")
    private String phoneNumber;

    @Past(message = "Birth date must be in the past")
    @Column(name = "birth_date")
    private LocalDate birthDate;
}
