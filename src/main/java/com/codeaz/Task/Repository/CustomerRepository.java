package com.codeaz.Task.Repository;

import com.codeaz.Task.Model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository  extends JpaRepository <Customer,Long> {
}
