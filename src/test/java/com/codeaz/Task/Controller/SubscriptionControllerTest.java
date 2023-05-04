package com.codeaz.Task.Controller;

import com.codeaz.Task.Exception.ResourceNotFoundException;
import com.codeaz.Task.Model.Quotation;
import com.codeaz.Task.Model.Subscription;
import com.codeaz.Task.Service.Quotation.QuotationServiceImplementation;
import com.codeaz.Task.Service.Subscription.SubscriptionServiceImplementation;
import jakarta.persistence.EntityNotFoundException;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
@DataJpaTest
class SubscriptionControllerTest {
    @InjectMocks
    private SubscriptionController subscriptionController;

    @Mock
    private QuotationServiceImplementation quotationServiceImplementation;

    @Mock
    private SubscriptionServiceImplementation subscriptionServiceImplementation;
    @Test
    void createSubscription() {
        Long quotationId = 1L;
        Quotation quotation = new Quotation();
        quotation.setId(quotationId);

        Subscription subscription = new Subscription();
        subscription.setId(1L);

        when(quotationServiceImplementation.quotationExist(quotationId)).thenReturn(true);
        when(quotationServiceImplementation.getQuotationById(quotationId)).thenReturn(quotation);
        when(subscriptionServiceImplementation.createSubscription(Mockito.any())).thenReturn(subscription);

        ResponseEntity<Subscription> responseEntity = subscriptionController.CreateSubscription(quotationId, subscription);

        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assert.assertEquals(subscription, responseEntity.getBody());
        Assert.assertEquals(quotation, subscription.getQuotation());
    }
    @Test
    public void testCreateSubscriptionWithInvalidQuotationId() {
        Long quotationId = 1L;
        Subscription subscription = new Subscription();
        subscription.setId(1L);

        when(quotationServiceImplementation.quotationExist(quotationId)).thenReturn(false);

        ResponseEntity<Subscription> responseEntity = subscriptionController.CreateSubscription(quotationId, subscription);

        Assert.assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
        Assert.assertNull(responseEntity.getBody());
    }
    @Test
    void itShouldReturnSubscriptionById() {
        // Arrange
        long subscriptionId = 1L;
        Subscription subscription = new Subscription();
        subscription.setId(subscriptionId);
        when(subscriptionServiceImplementation.getSubscriptionById(subscriptionId)).
                thenReturn((subscription));

        // Act
        Subscription result = subscriptionServiceImplementation.getSubscriptionById(subscriptionId);

        // Assert
        assertEquals(subscription, result);
    }

    @Test
    public void itShouldReturnSubscriptionByQuotationId() {
        Long quotationId = 1L;

        Quotation quotation = new Quotation();
        quotation.setId(quotationId);

        Subscription subscription1 = new Subscription();
        subscription1.setId(1L);
        subscription1.setQuotation(quotation);

        Subscription subscription2 = new Subscription();
        subscription2.setId(2L);
        subscription2.setQuotation(quotation);

        List<Subscription> subscriptions = new ArrayList<>();
        subscriptions.add(subscription1);
        subscriptions.add(subscription2);

        when(quotationServiceImplementation.quotationExist(quotationId)).thenReturn(true);
        when(subscriptionServiceImplementation.getSubscriptionByQuotationId(quotationId)).thenReturn(subscriptions);

        ResponseEntity<List<Subscription>> response = subscriptionController.getSubscriptionByQuotationId(quotationId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(subscriptions, response.getBody());
    }
    @Test
    public void itShouldReturnAllSubscription() {
        List<Subscription> subscriptionList = new ArrayList<>();
        Subscription subscription1 = new Subscription();
        subscription1.setId(1L);
        subscriptionList.add(subscription1);
        Subscription subscription2 = new Subscription();
        subscription2.setId(2L);
        subscriptionList.add(subscription2);

        Mockito.when(subscriptionServiceImplementation.getAllSubscription()).thenReturn(subscriptionList);

        ResponseEntity<List<Subscription>> responseEntity = subscriptionController.getAllSubscription();
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(subscriptionList, responseEntity.getBody());
    }


    @Test
    public void givenNonExistingQuotationId_whenGetSubscriptionByQuotationId_thenReturnsNoContent() {
        Long quotationId = 1L;

        Mockito.when(quotationServiceImplementation.quotationExist(quotationId)).thenReturn(false);

        ResponseEntity<List<Subscription>> response = subscriptionController.getSubscriptionByQuotationId(quotationId);

        Assert.assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        Assert.assertNull(response.getBody());
    }



}