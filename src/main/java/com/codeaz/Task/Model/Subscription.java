package com.codeaz.Task.Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.io.Serializable;
import java.time.LocalDate;
/**
 The Subscription class represents a subscription for an insurance quotation
 It contains information about the subscription, including the quotation it belongs to,
 its start date and valid until date.
 @author [getabalew7]
 @version 1.0
 @since [05-05-2023]
 */
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Subscription implements Serializable {
    /**
     The id of the subscription
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     The quotation that the subscription belongs to
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quotation_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Quotation quotation;
    /**
     The start date of the subscription
     */
    @Column(name = "start_date")
    private LocalDate startDate;
    /**
     The date until which the subscription is valid
     */
    @Column(name = "valid_until")
    private LocalDate validUntil;

}
