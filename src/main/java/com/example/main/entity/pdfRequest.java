package com.example.main.entity;

import java.util.Set;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class pdfRequest {
    private String toEmail;
    private String subject;
    private String message;
}
