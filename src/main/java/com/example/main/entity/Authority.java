package com.example.main.entity;

import org.springframework.security.core.GrantedAuthority;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Authority implements GrantedAuthority {

	private static final long serialVersionUID = 8769822607940010904L;
	private String authority;

	@Override
	public String getAuthority() {
		return this.authority;
	}

}
