package com.codeaz.Task.Service.Subscription;

import com.codeaz.Task.Model.Subscription;

import java.util.List;

public interface SubscriptionService {

    Subscription createSubscription(Subscription subscription);
    Subscription getSubscriptionById(Long id);
    List<Subscription> getSubscriptionByQuotationId(Long id);

    List<Subscription> getAllSubscription();
}
