package com.codeaz.Task.Controller;

import com.codeaz.Task.Model.Customer;
import com.codeaz.Task.Service.Customer.CustomerService;
import com.codeaz.Task.Service.Customer.CustomerServiceImplementation;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.codeaz.Task.Exception.ResourceNotFoundException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
@DataJpaTest
class CustomerControllerTest {
    @InjectMocks
    private CustomerController customerController;

    @Mock
    private CustomerServiceImplementation customerService;

    @Test
    void itShouldCreateCustomerSuccessfully() {
        // Arrange
        Customer customer = new Customer();
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customer.setMiddleName("Gech");
        customer.setEmail("johndoe@example.com");
        customer.setPhoneNumber("1234567890");
        customer.setBirthDate(LocalDate.of(1990, 1, 1));

        when(customerService.createCustomer(Mockito.any(Customer.class))).thenReturn(customer);
        // Act
        ResponseEntity<Customer> response = customerController.createCustomer(customer);
        // Assert
        Assert.assertEquals(HttpStatus.CREATED, response.getStatusCode());
        Assert.assertEquals(customer, response.getBody());
        verify(customerService, Mockito.times(1)).createCustomer(Mockito.any(Customer.class));
    }

    @Test
    public void itShouldReturnAllCustomers() {
        // create mock list of customers
        List<Customer> customers = new ArrayList<>();
        customers.add(new Customer(1L, "John", "Doe","Doe", "john.doe@example.com", "555-1234", LocalDate.of(2000, 1, 1)));
        customers.add(new Customer(2L, "Jane", "Doe", "Doe", "jane.doe@example.com", "555-5678", LocalDate.of(2001, 2, 2)));

        // mock the customerService
        Mockito.when(customerService.getCustomers()).thenReturn(customers);

        // call the controller method
        ResponseEntity<List<Customer>> response = customerController.getCustomers();

        // assert that the response is not null and has status OK
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        // assert that the response body contains the correct number of customers
        List<Customer> responseBody = response.getBody();
        assertNotNull(responseBody);
        assertEquals(customers.size(), responseBody.size());

        // assert that the response body contains the correct customers
        for (int i = 0; i < customers.size(); i++) {
            assertEquals(customers.get(i), responseBody.get(i));
        }

        // verify that the customerService was called
        Mockito.verify(customerService, Mockito.times(1)).getCustomers();
    }

    @Test
    void itShouldReturnCustomerByID() {
        // Arrange
        Long customerId = 1L;
        Customer customer = new Customer(customerId, "John",
                "Doe", "Middle", "johndoe@example.com", "555-555-5555", null);

        when(customerService.getCustomerById(customerId)).thenReturn(customer);

        // Act
        ResponseEntity<Customer> response = customerController.getCustomerById(customerId);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(customer, response.getBody());
    }
    @Test
    void itShouldUpdateCustomerById(){
        // Arrange
        Customer customer = new Customer(1L,"John", "Doe", "Smith", "johndoe@example.com", "1234567890", LocalDate.of(1980, 1, 1));
        Customer updatedCustomer = new Customer(1L,"John", "Doe", "Johnson", "johndoe@example.com", "1234567890", LocalDate.of(1980, 1, 1));
        updatedCustomer.setId(1L);

        when(customerService.updateCustomer(updatedCustomer)).thenReturn(updatedCustomer);

        // Act
        ResponseEntity<Customer> response = customerController.updatecustomer(1L, updatedCustomer);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedCustomer, response.getBody());
    }

    @Test
    void itShouldDeleteCustomerById() {
        Long customerId = 1L;
        Customer customer = new Customer();
        customer.setId(customerId);

        when(customerService.deleteCustomerById(customerId)).thenReturn(customer);

        ResponseEntity<Customer> response = customerController.deleteCustomer(customerId);

        verify(customerService, times(1)).deleteCustomerById(customerId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(customer, response.getBody());
    }


}