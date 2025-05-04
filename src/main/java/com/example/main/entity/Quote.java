package com.example.main.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Quote {
	
	  @Id
	    private String quoteCode; // Now the primary key (e.g., QT-00001)

	    private LocalDate date = LocalDate.now(); // Auto-set to today's date

	    private String customerId;

	    private String customerName;

	    private String customerEmail;

	    @ManyToOne
	    @JoinColumn(name = "registration_id")
	    private CustomerRegistration customer;

	    @OneToMany(mappedBy = "quote", cascade = CascadeType.ALL, orphanRemoval = true)
	    @JsonManagedReference
	    private List<Item> items = new ArrayList<>();

	    public void addItem(Item item) {
	        item.setQuote(this);
	        this.items.add(item);
	    }
}
