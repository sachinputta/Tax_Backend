package com.example.main.entity;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Employee implements UserDetails {

	/**
	 * 
	 */

	private static final long serialVersionUID = 1L;

	@Id
	private String employeeId;
	private String firstName;
	private String lastName;
	private String address;
	private String webMail;
	private String webMailPassword;
	private String employeeEmail;
	private String employeePassword;
	private long phoneNumber;
	private String imagePath;
	private String dateOfJoin;
	private String status;
	private String experience; 


	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinTable(name = "Employee_Roles", joinColumns = { @JoinColumn(name = "Employee_Id") }, inverseJoinColumns = {
			@JoinColumn(name = "Role_Name") })
	private Set<Role> roles = new HashSet<>();



	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Set<Authority> authorities = new HashSet<>();
		this.roles.forEach(userRole -> {
			authorities.add(new Authority("ROLE_" + userRole.getRoleName()));
		});
		return authorities;
	}

	@Override
	public String getPassword() {
		return this.employeePassword;
	}

	@Override
	public String getUsername() {
		return this.employeeId;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
}

