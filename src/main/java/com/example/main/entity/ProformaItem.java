

package com.example.main.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProformaItem {

    @Id
    private String piId; // Now primary key: e.g., PI-0001

    private String itemPackage;

    private LocalDate transactionDate;

    private LocalDate periodFrom;

    private LocalDate periodTo;

    private String hscSac;

    private Double charges;

    private Double discount;

    private Double netCharges;

    @ManyToOne
    @JoinColumn(name = "proforma_invoice_id")
    @JsonBackReference
    private ProformaInvoice proformaInvoice;

    @PrePersist
    @PreUpdate
    public void calculateNetCharges() {
        if (charges == null) charges = 0.0;
        if (discount == null) discount = 0.0;
        this.netCharges = charges - discount;
    }
}

