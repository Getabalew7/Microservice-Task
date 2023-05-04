package com.codeaz.Task.Controller;

import com.codeaz.Task.Model.Customer;
import com.codeaz.Task.Model.Quotation;
import com.codeaz.Task.Service.Customer.CustomerServiceImplementation;
import com.codeaz.Task.Service.Quotation.QuotationServiceImplementation;
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

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
@DataJpaTest
class QuotationControllerTest {
    @InjectMocks
    private QuotationController quotationController;

    @Mock
    private QuotationServiceImplementation quotationServiceImplementation;
    @Mock
    private CustomerServiceImplementation customerServiceImplementation;

    @Test
    void itShouldCreateQuotationSuccessfully() {
        // Arrange
        Long customerId = 1L;
        Customer customer = new Customer();
        customer.setId(customerId);

        Quotation quotationRequest = new Quotation();
        quotationRequest.setInsuredAmount(BigDecimal.valueOf(1000.0));
        Quotation expectedQuotation = new Quotation();
        expectedQuotation.setId(1L);
        expectedQuotation.setInsuredAmount(BigDecimal.valueOf(1000.0));
        expectedQuotation.setCustomer(customer);
        when(customerServiceImplementation.getCustomerById(customerId)).thenReturn(customer);
        when(quotationServiceImplementation.createQuotation(quotationRequest)).thenReturn(expectedQuotation);

        // Act
        ResponseEntity<Quotation> response = quotationController.createQuotation(customerId, quotationRequest);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        Quotation actualQuotation = response.getBody();
        assertNotNull(actualQuotation);
        assertEquals(expectedQuotation.getId(), actualQuotation.getId());
        assertEquals(expectedQuotation.getInsuredAmount(), actualQuotation.getInsuredAmount());
        assertEquals(expectedQuotation.getCustomer(), actualQuotation.getCustomer());

        verify(customerServiceImplementation, times(1)).getCustomerById(customerId);
        verify(quotationServiceImplementation, times(1)).createQuotation(quotationRequest);
    }
    @Test
    public void itShouldReturnQuotationByQuotationId() {
        // Arrange
        Long quotationId = 1L;
        Quotation expectedQuotation = new Quotation();
        expectedQuotation.setId(quotationId);
        when(quotationServiceImplementation.getQuotationById(quotationId)).thenReturn(expectedQuotation);

        // Act
        ResponseEntity<Quotation> response = quotationController.getQuotationById(quotationId);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedQuotation, response.getBody());
    }

    @Test
    public void itShouldReturnAllQuotationByCustomerId() {
        Long customerId = 1L;

        Customer customer = new Customer();
        customer.setId(customerId);

        Quotation quotation1 = new Quotation();
        quotation1.setId(1L);
        quotation1.setCustomer(customer);

        Quotation quotation2 = new Quotation();
        quotation2.setId(2L);
        quotation2.setCustomer(customer);

        List<Quotation> quotations = new ArrayList<>();
        quotations.add(quotation1);
        quotations.add(quotation2);

        when(customerServiceImplementation.customerExists(customerId)).thenReturn(true);
        when(quotationServiceImplementation.getQoutationsByCustomerId(customerId)).thenReturn(quotations);

        ResponseEntity<List<Quotation>> response = quotationController.getAllQuotationByCustomerId(customerId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(quotations, response.getBody());
    }
    @Test
    public void itShouldUpdateQuotation() {
        // Create a sample quotation to be updated
        Quotation oldQuotation = new Quotation();
        oldQuotation.setId(1L);
        oldQuotation.setInsuredAmount(BigDecimal.valueOf(5000.0));
        oldQuotation.setBeginningOfInsurance(LocalDate.now());
        oldQuotation.setDateOfSigningMortgage(LocalDate.now());

        // Create a sample new quotation to replace the old quotation
        Quotation newQuotation = new Quotation();
        newQuotation.setId(1L);
        newQuotation.setInsuredAmount(BigDecimal.valueOf(10000.0));
        newQuotation.setBeginningOfInsurance(LocalDate.now().plusDays(1));
        newQuotation.setDateOfSigningMortgage(LocalDate.now().plusDays(1));

        // Mock the quotationServiceImplementation's quotationExist() method to return true
        Mockito.when(quotationServiceImplementation.quotationExist(1L)).thenReturn(true);

        // Mock the quotationServiceImplementation's updateQuotation() method to return the new quotation
        Mockito.when(quotationServiceImplementation.updateQuotation(newQuotation)).thenReturn(newQuotation);

        // Call the updateQuotation() method with the sample new quotation
        ResponseEntity<Quotation> responseEntity = quotationController.updateQuotation(1L, newQuotation);

        // Verify that the quotationServiceImplementation's quotationExist() method was called with the correct argument
        Mockito.verify(quotationServiceImplementation).quotationExist(1L);

        // Verify that the quotationServiceImplementation's updateQuotation() method was called with the correct argument
        Mockito.verify(quotationServiceImplementation).updateQuotation(newQuotation);

        // Verify that the HTTP response code is HttpStatus.OK
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        // Verify that the returned quotation is the same as the new quotation
        Assert.assertEquals(newQuotation, responseEntity.getBody());
    }

    @Test
    void itShouldDeleteQuotationById() {
        Long quotationId = 1L;
        Quotation deletedQuotation = new Quotation(quotationId, null, null, null, null);
        when(quotationServiceImplementation.deleteQuotation(quotationId)).thenReturn(deletedQuotation);

        ResponseEntity<Quotation> response = quotationController.deleteQuotationById(quotationId);

        verify(quotationServiceImplementation).deleteQuotation(quotationId);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(deletedQuotation, response.getBody());
    }
    @Test
    public void deleteQuotationByCustomerId_CustomerExists_ReturnDeletedQuotations() {
        // Arrange
        Long customerId = 1L;
        Customer customer = new Customer();
        customer.setId(customerId);
        customer.setFirstName("John");
        Quotation quotation1 = new Quotation();
        quotation1.setId(1L);
        quotation1.setBeginningOfInsurance(LocalDate.now());
        quotation1.setInsuredAmount(BigDecimal.valueOf(1000.0));
        quotation1.setDateOfSigningMortgage(LocalDate.now());
        quotation1.setCustomer(customer);
        Quotation quotation2 = new Quotation(2L, LocalDate.now(), BigDecimal.valueOf(2000.0), LocalDate.now(), customer);
        List<Quotation> quotations = Arrays.asList(quotation1, quotation2);

        when(customerServiceImplementation.customerExists(customerId)).thenReturn(true);
        when(quotationServiceImplementation.deleteQoutationByCustomerId(customerId)).thenReturn(quotations);

        // Act
        ResponseEntity<List<Quotation>> responseEntity = quotationController.deleteQuotationByCustomerId(customerId);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(quotations, responseEntity.getBody());
    }

}