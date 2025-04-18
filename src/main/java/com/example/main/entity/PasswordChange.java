package com.example.main.entity;

import lombok.Data;

@Data
public class PasswordChange {
	
	private String username;
    private String currentPassword;
    private String newPassword;
    private String confirmPassword;

}
