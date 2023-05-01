package com.codeaz.Task.Controller;

import com.codeaz.Task.Model.Quotation;
import com.codeaz.Task.Model.Subscription;
import com.codeaz.Task.Service.Quotation.QuotationServiceImplementation;
import com.codeaz.Task.Service.Subscription.SubscriptionService;
import com.codeaz.Task.Service.Subscription.SubscriptionServiceImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("api/")
public class SubscriptionController {
    @Autowired
    private SubscriptionServiceImplementation subscriptionServiceImplementation;

    @Autowired
    private QuotationServiceImplementation quotationServiceImplementation;
  @GetMapping("/subscription/{subscriptionId}")
  public ResponseEntity<Subscription> getSubscriptionById(@PathVariable(value="subscriptionId") Long id){
      return new ResponseEntity<>(subscriptionServiceImplementation.getSubscriptionById(id), HttpStatus.OK);
  }
  @PostMapping("quotation/{quotationId}/subscription")
    public ResponseEntity<Subscription> CreateSubscription(@PathVariable(value="quotationId") Long quotationId,
                                                           @RequestBody Subscription subscription){
      if(! quotationServiceImplementation.quotationExist(quotationId)){
          return new ResponseEntity<>(HttpStatus.NO_CONTENT);
      }
      subscription.setQuotation(quotationServiceImplementation.getQuotationById(quotationId));
      return new ResponseEntity<>(subscriptionServiceImplementation.
              createSubscription(subscription), HttpStatus.OK);
  }
    @GetMapping("/quotation/{quotationId}/subscription")
    public ResponseEntity<List<Subscription>> getSubscriptionByQuotationId(@PathVariable(value="quotationId") Long quotationId){
      if(!quotationServiceImplementation.quotationExist(quotationId)){
          return new ResponseEntity<>(HttpStatus.NO_CONTENT);
      }

      return new ResponseEntity<>(subscriptionServiceImplementation.getSubscriptionByQuotationId(quotationId), HttpStatus.OK);
    }
    @GetMapping("/subscription")
    public ResponseEntity<List<Subscription>> getAllSubscription(){
      return new ResponseEntity<>(subscriptionServiceImplementation.getAllSubscription(),HttpStatus.OK);
    }
}