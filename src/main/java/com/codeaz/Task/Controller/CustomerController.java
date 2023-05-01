package com.codeaz.Task.Controller;

import com.codeaz.Task.Exception.ResourceNotFoundException;
import com.codeaz.Task.Model.Customer;
import com.codeaz.Task.Service.Customer.CustomerServiceImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("api/customers")
public class CustomerController {
    @Autowired
    private  CustomerServiceImplementation customerService;
    @PostMapping()
    public ResponseEntity<Customer> createCustomer(@RequestBody Customer customer) {
        Customer cust= customerService.createCustomer(customer);
        return new ResponseEntity<>(cust, HttpStatus.CREATED);
    }
    @GetMapping
    public ResponseEntity<List<Customer>> getCustomers(){
        List<Customer> _customer= new ArrayList<>();
        customerService.getCustomers().forEach(_customer::add);
        if(_customer.isEmpty()){
            throw  new ResourceNotFoundException("Customer Not Found");
        }
        return new ResponseEntity<>(_customer, HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable Long id){
        return new ResponseEntity<>(customerService.getCustomerById(id), HttpStatus.OK);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Customer> updatecustomer(@PathVariable Long id, @RequestBody Customer customer){
        customer.setId(id);
        Customer newCustomer= customerService.updateCustomer(customer);
        return new ResponseEntity<>(newCustomer, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Customer> deleteCustomer(@PathVariable(value = "id") Long id){
        return new ResponseEntity<>(customerService.deleteCustomerById(id), HttpStatus.OK);
    }
}
