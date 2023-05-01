package com.codeaz.Task.Service.Customer;

import com.codeaz.Task.Model.Customer;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


public interface CustomerService {

    public Customer createCustomer(Customer customer);
    public List<Customer> createCusotomer(List<Customer> customers);
    public List<Customer> getCustomers();
    Customer updateCustomer(Customer customer);
    Customer getCustomerById(Long id);

    Customer deleteCustomerById(Long id);
}
