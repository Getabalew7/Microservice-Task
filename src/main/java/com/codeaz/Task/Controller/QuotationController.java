package com.codeaz.Task.Controller;

import com.codeaz.Task.Model.Customer;
import com.codeaz.Task.Model.Quotation;
import com.codeaz.Task.Repository.QuotationRepository;
import com.codeaz.Task.Service.Customer.CustomerServiceImplementation;
import com.codeaz.Task.Service.Quotation.QuotationServiceImplementation;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/api")
public class QuotationController {

    @Autowired
    private QuotationServiceImplementation quotationServiceImplementation;
    @Autowired
    private CustomerServiceImplementation customerServiceImplementation;

    @GetMapping("/customers/{customerId}/quotation")
    public ResponseEntity<List<Quotation>> getAllQuotationByCustomerId(
            @PathVariable(value = "customerId") Long customerId) {
        if (!customerServiceImplementation.customerExists(customerId)) {
            throw new ResourceNotFoundException("Not found Quotation with id = " + customerId);
        }

        List<Quotation> quotation = quotationServiceImplementation.getQoutationsByCustomerId(customerId);
        return new ResponseEntity<>(quotation, HttpStatus.OK);
    }
    @GetMapping("/quotations/{id}")
    public ResponseEntity<Quotation> getQuotationById(@PathVariable(value = "id") Long id) {
        Quotation quotation = quotationServiceImplementation.getQuotationById(id);
        return new ResponseEntity<>(quotation, HttpStatus.OK);
    }
    @PostMapping("/customers/{customerId}/quotation")
    public ResponseEntity<Quotation> createQuotation(@PathVariable(value = "customerId") Long customerId,
                                                 @RequestBody Quotation quotationRequest) {
        Customer customer= customerServiceImplementation.getCustomerById(customerId);
        quotationRequest.setCustomer(customer);
        Quotation quotation= quotationServiceImplementation.createQuotation(quotationRequest);
        return new ResponseEntity<>(quotation, HttpStatus.CREATED);
    }

    @PutMapping("/quotations/{quotationId}")
   public ResponseEntity<Quotation> updateQuotation(@PathVariable(value = "quotationId") Long quotaionId,
                                                    @RequestBody Quotation newQuotaion){
        if(! quotationServiceImplementation.quotationExist(quotaionId)){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        newQuotaion.setId(quotaionId);
     Quotation updatedQuotation= quotationServiceImplementation.updateQuotation(newQuotaion);
     return new ResponseEntity<>(updatedQuotation, HttpStatus.OK);
    }

    @DeleteMapping("/quotations/{quotaionId}")
   public ResponseEntity<Quotation> deleteQuotationById(@PathVariable (value="quotaionId") Long quotaionId){
        return new ResponseEntity<>(quotationServiceImplementation.deleteQuotation(quotaionId),HttpStatus.OK);
    }
    @DeleteMapping("/customers/{customerId}/quotation")
    public ResponseEntity<List<Quotation>> deleteQuotationByCustomerId(@PathVariable (value="customerId") Long customerId){
       if(! customerServiceImplementation.customerExists(customerId)){
           return new ResponseEntity<>(HttpStatus.NO_CONTENT);
       }
        return new ResponseEntity<>(quotationServiceImplementation.deleteQoutationByCustomerId(customerId),HttpStatus.OK);
    }
}
