/**
 The SubscriptionController class implements REST API endpoints for Subscription operations such as creating, retrieving and deleting subscriptions.
 */
package com.codeaz.Task.Controller;
import com.codeaz.Task.Model.Quotation;
import com.codeaz.Task.Model.Subscription;
import com.codeaz.Task.Service.Quotation.QuotationServiceImplementation;
import com.codeaz.Task.Service.Subscription.SubscriptionService;
import com.codeaz.Task.Service.Subscription.SubscriptionServiceImplementation;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
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
@RequestMapping("api/")
public class SubscriptionController {
    /**
     * The SubscriptionServiceImplementation dependency is autowired.
     */
    @Autowired
    private SubscriptionServiceImplementation subscriptionServiceImplementation;
    /**
     * The QuotationServiceImplementation dependency is autowired.
     */
    @Autowired
    private QuotationServiceImplementation quotationServiceImplementation;

    /**
     * Retrieves a Subscription object by its ID.
     * @param id The ID of the subscription.
     * @return A ResponseEntity containing the Subscription object and the HTTP status code.
     */
  @GetMapping("/subscription/{subscriptionId}")
  public ResponseEntity<Subscription> getSubscriptionById(@PathVariable(value="subscriptionId") @Min(1) Long id){
      return new ResponseEntity<>(subscriptionServiceImplementation.getSubscriptionById(id), HttpStatus.OK);
  }
    /**
     * Creates a new subscription for a quotation.
     * @param quotationId The ID of the quotation to create a subscription for.
     * @param subscription The Subscription object to create.
     * @return A ResponseEntity containing the created Subscription object and the HTTP status code.
     */
  @PostMapping("quotation/{quotationId}/subscription")
    public ResponseEntity<Subscription> CreateSubscription(@PathVariable(value="quotationId") @Min(1) Long quotationId,
                                                           @Valid @RequestBody Subscription subscription){
      if(! quotationServiceImplementation.quotationExist(quotationId)){
          return new ResponseEntity<>(HttpStatus.NO_CONTENT);
      }
      subscription.setQuotation(quotationServiceImplementation.getQuotationById(quotationId));
      return new ResponseEntity<>(subscriptionServiceImplementation.
              createSubscription(subscription), HttpStatus.OK);
  }
    /**
     * Retrieves all subscriptions for a given quotation ID.
     * @param quotationId The ID of the quotation to retrieve subscriptions for.
     * @return A ResponseEntity containing the list of Subscription objects and the HTTP status code.
     */
    @GetMapping("/quotation/{quotationId}/subscription")
    public ResponseEntity<List<Subscription>> getSubscriptionByQuotationId(@PathVariable(value="quotationId") @Min(1) Long quotationId){
      if(!quotationServiceImplementation.quotationExist(quotationId)){
          return new ResponseEntity<>(HttpStatus.NO_CONTENT);
      }

      return new ResponseEntity<>(subscriptionServiceImplementation.getSubscriptionByQuotationId(quotationId), HttpStatus.OK);
    }
    /**
     * Retrieves all Subscription objects.
     * @return A ResponseEntity containing the list of Subscription objects and the HTTP status code.
     */
    @GetMapping("/subscription")
    public ResponseEntity<List<Subscription>> getAllSubscription(){
      return new ResponseEntity<>(subscriptionServiceImplementation.getAllSubscription(),HttpStatus.OK);
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