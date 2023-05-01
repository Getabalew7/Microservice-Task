package com.codeaz.Task.Service.Quotation;

import com.codeaz.Task.Model.Quotation;
import com.codeaz.Task.Repository.CustomerRepository;
import com.codeaz.Task.Repository.QuotationRepository;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuotationServiceImplementation  implements QuotationService{

    @Autowired
    private final QuotationRepository quotationRepository;

    public QuotationServiceImplementation(QuotationRepository quotationRepository, CustomerRepository customerRepository) {
        this.quotationRepository = quotationRepository;
        this.customerRepository = customerRepository;
    }
    @Autowired
    private final CustomerRepository customerRepository;

    @Override
    public Quotation createQuotation(Quotation quotation) {
        return quotationRepository.save(quotation);
    }

    @Override
    public Quotation getQuotationById(Long id) {
        return quotationRepository.findById(id).
                orElseThrow(() -> new ResourceNotFoundException("Not found Quotation with id = " + id));
    }
    @Override
    public Quotation updateQuotation(Quotation quotation) {
        Quotation updatedQuotation = quotationRepository.findById(quotation.getId()).
                orElseThrow(() -> new ResourceNotFoundException("Quotation not found with id " +
                        quotation.getId()));

        updatedQuotation.setInsuredAmount(quotation.getInsuredAmount());
        updatedQuotation.setBeginningOfInsurance(quotation.getBeginningOfInsurance());
        updatedQuotation.setDateOfSigningMortgage(quotation.getDateOfSigningMortgage());
        updatedQuotation.setCustomer(quotation.getCustomer());
        return quotationRepository.save(updatedQuotation);
    }

    @Override
    public Quotation deleteQuotation(Long id) {
       Quotation quotation = quotationRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Quotaion not found with Id" + id));
       quotationRepository.deleteById(id);
       return quotation;
    }

    @Override
    public List<Quotation> getAllQuotations() {
        return null;
    }

    @Override
    public List<Quotation> deleteQoutationByCustomerId(Long id) {
        List<Quotation> quotations= quotationRepository.findByCustomerId(id);
        quotationRepository.deleteByCustomerId(id);
        return quotations;
    }

    @Override
    public List<Quotation> getQoutationsByCustomerId(Long id) {
        return quotationRepository.findByCustomerId(id);
    }

    public boolean quotationExist(Long id) {

        return quotationRepository.existsById(id);
    }
}
