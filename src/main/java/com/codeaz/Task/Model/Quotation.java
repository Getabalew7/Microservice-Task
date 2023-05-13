package com.codeaz.Task.Model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 Quotation is a class that represents a quotation for an insurance policy.
 It contains the ID, beginning date of insurance, insured amount, date of signing mortgage,
 and a customer object.

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
public class Quotation implements Serializable {
    /**
     * ID of the quotation.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * Beginning date of the insurance policy.
     */
    @NotNull(message = "Beginning of insurance is required")
    @Column(name = "beginning_of_insurance")
    private LocalDate beginningOfInsurance;
    /**
     * The amount that the policy is insured for.
     */
    @NotNull(message = "Insured amount is required")
    @Positive(message = "Insured amount must be a positive value")
    @Column(name = "insured_amount")
    private BigDecimal insuredAmount;

    /**
     * The date that the mortgage was signed.
     */
    @NotNull(message = "Date of signing mortgage is required")
    @Future(message = "Date of signing mortgage must be in the future")
    @Column(name = "date_of_signing_mortgage")
    private LocalDate dateOfSigningMortgage;
    /**
     * The customer associated with the quotation.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Customer customer;
}
