package com.codeaz.Task.Service.Quotation;

import com.codeaz.Task.Model.Quotation;

import java.util.List;

public interface QuotationService {
    Quotation createQuotation (Quotation quotation);
    Quotation getQuotationById (Long id);

    Quotation updateQuotation (Quotation quotation);
    Quotation deleteQuotation (Long id);

    List<Quotation> getAllQuotations();

    List<Quotation> deleteQoutationByCustomerId(Long id);
    List<Quotation> getQoutationsByCustomerId(Long id);

    boolean quotationExist(Long id);
}
