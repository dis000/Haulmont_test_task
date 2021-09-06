package com.example.demo.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;
import java.util.UUID;


@Getter
@Setter
@Entity
@Table
@NoArgsConstructor
public class Bank implements IEntity {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "ID", updatable = false, nullable = false, columnDefinition = "uuid")
    private UUID ID;

    @Column(length = 25,name = "NAME",unique = true)
    private String name;





    @OneToMany(fetch = FetchType.LAZY, mappedBy = "bank",orphanRemoval = true)
    private Set<Client> clients;


   @OneToMany(fetch = FetchType.LAZY, mappedBy = "bank", orphanRemoval = true)
    private Set<Credit> credits;


    public Bank(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return getName();
    }
}
