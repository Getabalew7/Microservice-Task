/**
 * Controller class for managing Customer resources.
 */
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
/**
 This is the QuotationController class which handles all the HTTP requests related to Quotations
 @author [getabalew7]
 @version 1.0
 @since [05-05-2023]
 */
@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("api/customers")
public class CustomerController {
    /**
     * Service for managing Customer resources.
     */
    @Autowired
    private  CustomerServiceImplementation customerService;
    /**
     * Endpoint for creating a new Customer.
     *
     * @param customer Customer object to be created.
     * @return ResponseEntity containing the created Customer object.
     */
    @PostMapping()
    public ResponseEntity<Customer> createCustomer(@RequestBody Customer customer) {
        Customer cust= customerService.createCustomer(customer);
        return new ResponseEntity<>(cust, HttpStatus.CREATED);
    }
    /**
     * Endpoint for retrieving all Customer objects.
     *
     * @return ResponseEntity containing a List of all Customer objects.
     */
    @GetMapping
    public ResponseEntity<List<Customer>> getCustomers(){
        List<Customer> _customer= new ArrayList<>();
        customerService.getCustomers().forEach(_customer::add);
        if(_customer.isEmpty()){
            throw  new ResourceNotFoundException("Customer Not Found");
        }
        return new ResponseEntity<>(_customer, HttpStatus.OK);
    }
    /**
     * Endpoint for retrieving a single Customer object by ID.
     *
     * @param id ID of the Customer to retrieve.
     * @return ResponseEntity containing the requested Customer object.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable Long id){
        return new ResponseEntity<>(customerService.getCustomerById(id), HttpStatus.OK);
    }
    /**
     * Endpoint for updating an existing Customer object.
     *
     * @param id ID of the Customer to update.
     * @param customer Updated Customer object.
     * @return ResponseEntity containing the updated Customer object.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Customer> updatecustomer(@PathVariable Long id, @RequestBody Customer customer){
        customer.setId(id);
        Customer newCustomer= customerService.updateCustomer(customer);
        return new ResponseEntity<>(newCustomer, HttpStatus.OK);
    }
    /**
     * Endpoint for deleting a Customer object by ID.
     *
     * @param id ID of the Customer to delete.
     * @return ResponseEntity containing the deleted Customer object.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Customer> deleteCustomer(@PathVariable(value = "id") Long id){
        return new ResponseEntity<>(customerService.deleteCustomerById(id), HttpStatus.OK);
    }
}
