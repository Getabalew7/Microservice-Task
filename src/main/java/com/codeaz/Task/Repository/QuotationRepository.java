package com.codeaz.Task.Repository;

import com.codeaz.Task.Model.Customer;
import com.codeaz.Task.Model.Quotation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuotationRepository  extends JpaRepository <Quotation, Long> {
    List<Quotation> findByCustomerId(Long customerId);

    List<Quotation> deleteByCustomerId(Long customerId);
}
