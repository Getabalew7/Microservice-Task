package com.codeaz.Task.Service.Subscription;

import com.codeaz.Task.Model.Subscription;
import com.codeaz.Task.Repository.SubscriptionRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SubscriptionServiceImplementation implements SubscriptionService{
    private final SubscriptionRepository subscriptionRepository;

    public SubscriptionServiceImplementation(SubscriptionRepository subscriptionRepository) {
        this.subscriptionRepository = subscriptionRepository;
    }

    @Override
    public Subscription createSubscription(Subscription subscription) {

        return subscriptionRepository.save(subscription);
    }

    @Override
    public Subscription getSubscriptionById(Long id) {

        return subscriptionRepository.findById(id).
                orElseThrow(()-> new EntityNotFoundException("Not Found subscription by Id "+ id));
    }

    @Override
    public List<Subscription> getSubscriptionByQuotationId(Long id) {
        List<Subscription> subscriptionList = new ArrayList<>();
        subscriptionRepository.findByQuotationId(id).forEach(subscriptionList::add);
        if(subscriptionList.isEmpty()){
            throw new EntityNotFoundException("Not Found subscription with Quotation Id "+ id);
        }
        return subscriptionRepository.findByQuotationId(id);
    }

    @Override
    public List<Subscription> getAllSubscription() {
        List<Subscription> subscriptionList = new ArrayList<>();
        subscriptionRepository.findAll().forEach(subscriptionList::add);
        if(subscriptionList.isEmpty()){
            throw new EntityNotFoundException("Subscription is Empty");
        }
        return subscriptionList;
    }
}
