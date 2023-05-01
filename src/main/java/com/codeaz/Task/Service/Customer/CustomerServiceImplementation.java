package com.codeaz.Task.Service.Customer;

import com.codeaz.Task.Exception.ResourceNotFoundException;
import com.codeaz.Task.Model.Customer;
import com.codeaz.Task.Repository.CustomerRepository;
import com.codeaz.Task.Service.Customer.CustomerService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerServiceImplementation implements CustomerService {
    private final CustomerRepository customerRepository;

    public CustomerServiceImplementation(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public Customer createCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    @Override
    public List<Customer> createCusotomer(List<Customer> customers) {

        return customerRepository.saveAll(customers);
    }

    @Override
    public List<Customer> getCustomers() {
        return customerRepository.findAll();
    }

    @Override
    public Customer updateCustomer(Customer customer) {
        Customer oldCustomer = customerRepository.findById(customer.getId()).
                orElseThrow(()-> new ResourceNotFoundException("Customer not found with id " + customer.getId()));
        oldCustomer.setFirstName(customer.getFirstName());
        oldCustomer.setLastName(customer.getLastName());
        oldCustomer.setMiddleName(customer.getMiddleName());
        oldCustomer.setEmail(customer.getEmail());
        oldCustomer.setPhoneNumber(customer.getPhoneNumber());
        oldCustomer.setBirthDate(customer.getBirthDate());
        return customerRepository.save(oldCustomer);
    }

    @Override
    public Customer getCustomerById(Long id) {
        return customerRepository.findById(id).
                orElseThrow(()-> new ResourceNotFoundException("Customer not found with id "+id));
    }

    @Override
    public Customer deleteCustomerById(Long id) {
        Customer customer= customerRepository.findById(id).
                orElseThrow(()-> new ResourceNotFoundException("Customer not found with id "+ id));
        customerRepository.deleteById(id);
        return customer;
    }

    public boolean customerExists(Long id) {
        return (customerRepository.existsById(id));
    }
}
