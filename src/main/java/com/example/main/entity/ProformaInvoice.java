
package com.example.main.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProformaInvoice {

    @Id
    private String proformaCode; // Example: PI-0001

    private LocalDate date = LocalDate.now();

    private String customerId;

    private String customerName;

    private String customerEmail;
    
    private String technology;

    @ManyToOne
    @JoinColumn(name = "registration_id")
    private CustomerRegistration customer;

    @OneToMany(mappedBy = "proformaInvoice", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<ProformaItem> items = new ArrayList<>();

    public void addItem(ProformaItem item) {
        item.setProformaInvoice(this);
        this.items.add(item);
    }
}
