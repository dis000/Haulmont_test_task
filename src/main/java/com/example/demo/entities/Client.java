package com.example.demo.entities;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@Table
@Entity
@NoArgsConstructor
public class Client implements IEntity {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "ID", updatable = false, nullable = false, columnDefinition = "uuid")
    private UUID ID;


    @NotNull
    @Column(length = 30)
    private String name;

    @NotNull
    @Column(length = 15,name = "PHONE")
    private String Phone;

    @Column(length = 10,name = "PASSPORTID")
    @NotNull
    private String passportID;



    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "BANK")
    @NotNull
    private Bank bank;


    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "client")
    private Set<CreditOffer> creditOffers;


    public Client(String name, String Phone, String passportID, Bank bank) {
        this.name = name;
        this.Phone = Phone;
        this.passportID = passportID;
        this.bank = bank;
    }

    @Override
    public String toString() {
        return getName();
    }
}

