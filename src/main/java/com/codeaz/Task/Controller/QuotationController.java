package com.codeaz.Task.Controller;

import com.codeaz.Task.Model.Customer;
import com.codeaz.Task.Model.Quotation;
import com.codeaz.Task.Repository.QuotationRepository;
import com.codeaz.Task.Service.Customer.CustomerServiceImplementation;
import com.codeaz.Task.Service.Quotation.QuotationServiceImplementation;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 This is the QuotationController class which handles all the HTTP requests related to Quotations
 @author [getabalew7]
 @version 1.0
 @since [05-05-2023]
 */
@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/api")
public class QuotationController {
    /**
     * The QuotationServiceImplementation dependency is autowired.
     */
    @Autowired
    private QuotationServiceImplementation quotationServiceImplementation;
    /**
     * The CustomerServiceImplementation dependency is autowired.
     */
    @Autowired
    private CustomerServiceImplementation customerServiceImplementation;
    /**
     * This method returns all the quotations of a particular customer.
     * @param customerId Unique Id of the customer whose quotations need to be fetched.
     * @return List of quotations of a particular customer.
     */
    @GetMapping("/customers/{customerId}/quotation")
    public ResponseEntity<List<Quotation>> getAllQuotationByCustomerId(
            @PathVariable(value = "customerId") Long customerId) {
        if (!customerServiceImplementation.customerExists(customerId)) {
            throw new ResourceNotFoundException("Not found Quotation with id = " + customerId);
        }

        List<Quotation> quotation = quotationServiceImplementation.getQoutationsByCustomerId(customerId);
        return new ResponseEntity<>(quotation, HttpStatus.OK);
    }
    /**
     * This method returns a particular quotation object based on the unique quotation id.
     * @param id Unique id of the quotation object.
     * @return Quotation object.
     */
    @GetMapping("/quotations/{id}")
    public ResponseEntity<Quotation> getQuotationById(@PathVariable(value = "id") Long id) {
        Quotation quotation = quotationServiceImplementation.getQuotationById(id);
        return new ResponseEntity<>(quotation, HttpStatus.OK);
    }
    /**
     * This method creates a new quotation object for a particular customer.
     * @param customerId Unique id of the customer for which the quotation is to be created.
     * @param quotationRequest Quotation object that needs to be created.
     * @return Newly created quotation object.
     */
    @PostMapping("/customers/{customerId}/quotation")
    public ResponseEntity<Quotation> createQuotation(@PathVariable(value = "customerId") Long customerId,
                                                 @RequestBody Quotation quotationRequest) {
        Customer customer= customerServiceImplementation.getCustomerById(customerId);
        quotationRequest.setCustomer(customer);
        Quotation quotation= quotationServiceImplementation.createQuotation(quotationRequest);
        return new ResponseEntity<>(quotation, HttpStatus.CREATED);
    }
    /**
     * This method updates an existing quotation object.
     * @param quotaionId Unique id of the quotation object that needs to be updated.
     * @param newQuotaion Quotation object containing the updated details.
     * @return Updated quotation object.
     */

    @PutMapping("/quotations/{quotationId}")
   public ResponseEntity<Quotation> updateQuotation(@PathVariable(value = "quotationId") @Min(1) Long quotaionId,
                                                   @Valid @RequestBody Quotation newQuotaion){
        if(! quotationServiceImplementation.quotationExist(quotaionId)){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        newQuotaion.setId(quotaionId);
     Quotation updatedQuotation= quotationServiceImplementation.updateQuotation(newQuotaion);
     return new ResponseEntity<>(updatedQuotation, HttpStatus.OK);
    }

    /**
     Delete a quotation by ID.
     @param quotaionId the ID of the quotation to delete
     @return a ResponseEntity with a status code indicating the success or failure of the operation
     */
    @DeleteMapping("/quotations/{quotaionId}")
   public ResponseEntity<Quotation> deleteQuotationById(@PathVariable (value="quotaionId") @Min(1) Long quotaionId){
        return new ResponseEntity<>(quotationServiceImplementation.deleteQuotation(quotaionId),HttpStatus.OK);
    }
    /**
     Delete all quotations for a customer.
     @param customerId the ID of the customer whose quotations should be deleted
     @return a ResponseEntity with a status code indicating the success or failure of the operation
     */
    @DeleteMapping("/customers/{customerId}/quotation")
    public ResponseEntity<List<Quotation>> deleteQuotationByCustomerId(@PathVariable (value="customerId") @Min(1) Long customerId){
       if(! customerServiceImplementation.customerExists(customerId)){
           return new ResponseEntity<>(HttpStatus.NO_CONTENT);
       }
        return new ResponseEntity<>(quotationServiceImplementation.deleteQoutationByCustomerId(customerId),HttpStatus.OK);
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
