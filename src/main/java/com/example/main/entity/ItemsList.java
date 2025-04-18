package com.example.main.entity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemsList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String purchaseDescription;

    private Double purchaseRate;

    private String description;

    private Double rate;

    private String usageUnit;
}
