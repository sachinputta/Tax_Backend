package com.example.main.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerRegistration {
	

    @Id
    private String customerId;

    // Customer Details
    private String customerType;
    private String salutation;
    private String firstName;
    private String lastName;
    private String companyName;
    private String customerEmail;
    private String phoneNumber;
    private String mobileNumber;

    // Billing Address
    private String billingStreet;
    private String billingCity;
    private String billingState;
    private String billingCountry;
    private String billingZip;

    // Shipping Address
    private String shippingStreet;
    private String shippingCity;
    private String shippingState;
    private String shippingCountry;
    private String shippingZip;

    // Tax & Compliance
    private String taxId;
    private String pan;
    private String supplyState;
    private String currency;

    // Payment & Accounting
    private String paymentTerms;
    private String creditLimit;
    private String bankName;
    private String accountNumber;
    private String ifscCode;
    private String preferredPaymentMethod;

    // Additional Info
    private String websiteUrl;
    private String contactPersonName;
    private String contactPersonEmail;
    private String contactPersonPhone;
    private String notes;

}
