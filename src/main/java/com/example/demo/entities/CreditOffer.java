package com.example.demo.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Set;
import java.util.UUID;

@Table
@Entity
@Getter
@Setter
@NoArgsConstructor
public class CreditOffer implements IEntity {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "ID", updatable = false, nullable = false, columnDefinition = "uuid")
    private UUID ID;

    @ManyToOne(fetch =  FetchType.EAGER)
    @JoinColumn(name="CREDIT")
    @NotNull
    private Credit credit;

    @ManyToOne(fetch =  FetchType.EAGER)
    @JoinColumn(name="CLIENT")
    @NotNull
    private Client client;

    @Column(name = "AMOUNT_OF_PAYMENT")
    private BigDecimal amountOfPayment;

    //TODO
    @OneToMany(mappedBy = "creditOffer", fetch = FetchType.EAGER)
    private Set<PaymentSchedule> paymentSchedule;


    public CreditOffer(Credit credit, Client client, BigDecimal amountOfPayment) {
        this.credit = credit;
        this.client = client;
        this.amountOfPayment = amountOfPayment;
    }

    @Override
    public String toString() {
        return paymentSchedule.size()+" мес. " + amountOfPayment + " " + credit.getPercentRate() + "%";
    }
}
