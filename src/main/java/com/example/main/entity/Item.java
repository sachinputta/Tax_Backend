package com.example.main.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Item {
	  @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long itemId;

	    private String description;
	    private int quantity;
	    private double rate;
	    private double amount;

	    @ManyToOne
	    @JoinColumn(name = "quote_id")
	    @JsonBackReference
	    private Quote quote;
}
