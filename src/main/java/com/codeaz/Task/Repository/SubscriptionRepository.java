package com.codeaz.Task.Repository;

import com.codeaz.Task.Model.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubscriptionRepository extends JpaRepository <Subscription, Long> {

  List<Subscription> findByQuotationId(Long id);
}
