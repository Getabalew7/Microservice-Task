/**
 * Controller class for managing Customer resources.
 */
package com.codeaz.Task.Controller;
import com.codeaz.Task.Exception.ResourceNotFoundException;
import com.codeaz.Task.Model.Customer;
import com.codeaz.Task.Service.Customer.CustomerServiceImplementation;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
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
    public ResponseEntity<Customer> createCustomer(@Valid @RequestBody Customer customer) {
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
    public ResponseEntity<Customer> getCustomerById(@PathVariable @Min(1) Long id){
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
    public ResponseEntity<Customer> updateCustomer(@PathVariable  @Min(1) Long id,
                                                   @Valid @RequestBody Customer customer){
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
    public ResponseEntity<Customer> deleteCustomer(@PathVariable(value = "id") @Min(1) Long id){
        return new ResponseEntity<>(customerService.deleteCustomerById(id), HttpStatus.OK);
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleValidationException(MethodArgumentNotValidException ex) {
        int len= ex.getBindingResult().getFieldErrors().size();
        StringBuilder errMsg = new StringBuilder();
        for(int i=0;i<len;i++){
            errMsg.append(ex.getBindingResult().getFieldErrors().get(i));
            if(i<len-1){
                errMsg.append(",");
            }
        }
        ObjectMapper objectMapper = new ObjectMapper();;
        try{
            String jsonErrorMessage= objectMapper.writeValueAsString(errMsg);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(jsonErrorMessage);
        } catch (JsonProcessingException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
