package com.example.main.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.main.entity.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, String> {

	Customer findByCustomerId(String customerId);

	List<Customer> findByRolesRoleNameNot(String roleName);

	@Query("SELECT c.customerEmail FROM Customer c")
	List<String> findAllCustomerEmails();

	@Query("SELECT c.phoneNumber FROM Customer c")
	List<Long> findAllPhoneNumbers();

	boolean existsByCustomerEmail(String customerEmail);

	boolean existsByPhoneNumber(long phoneNumber);

	@Query("SELECT COUNT(c) > 0 FROM Customer c WHERE c.customerEmail = :email AND c.customerId <> :customerId")
	boolean existsByEmailAndNotCustomerId(@Param("email") String email, @Param("customerId") String customerId);

	@Query("SELECT COUNT(c) > 0 FROM Customer c WHERE c.phoneNumber = :phoneNumber AND c.customerId <> :customerId")
	boolean existsByPhoneNumberAndNotCustomerId(@Param("phoneNumber") long phoneNumber,
			@Param("customerId") String customerId);

	@Query("SELECT c FROM Customer c JOIN c.roles r WHERE r.roleName IN :roles")
	List<Customer> findCustomersByRoles(@Param("roles") List<String> roles);

	@Query("SELECT c FROM Customer c JOIN c.roles r WHERE r.roleName = :roleName")
	List<Customer> findByRoleName(@Param("roleName") String roleName);

	List<Customer> findByRoles_RoleName(String roleName);

	Object findByCustomerEmail(String username);

}
