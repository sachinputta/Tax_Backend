package com.example.main.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.main.entity.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, String> {

	Employee findByEmployeeId(String employeeId);

	@Query("SELECT e FROM Employee e JOIN e.roles r WHERE r.roleName = :roleName AND e.status = :status")
	List<Employee> findByRoleAndStatus(@Param("roleName") String roleName, @Param("status") String status);

	List<Employee> findByRolesRoleNameNot(String roleName);

	List<Employee> findByStatus(String status);

	List<Employee> findByRoles_RoleNameAndStatus(String roleName, String status);

	List<Employee> findByRoles_RoleNameNotAndStatus(String roleName, String status);

	@Query("SELECT e.employeeEmail FROM Employee e")
	List<String> findAllEmployeeEmails();

	@Query("SELECT e.webMail FROM Employee e")
	List<String> findAllWebMails();

	@Query("SELECT e.phoneNumber FROM Employee e")
	List<Long> findAllPhoneNumbers();

	boolean existsByEmployeeEmail(String employeeEmail);

	boolean existsByWebMail(String webMail);

	boolean existsByPhoneNumber(long phoneNumber);

	@Query("SELECT COUNT(e) > 0 FROM Employee e WHERE e.employeeEmail = :email AND e.employeeId <> :employeeId")
	boolean existsByEmailAndNotEmployeeId(@Param("email") String email, @Param("employeeId") String employeeId);

	@Query("SELECT COUNT(e) > 0 FROM Employee e WHERE e.phoneNumber = :phoneNumber AND e.employeeId <> :employeeId")
	boolean existsByPhoneNumberAndNotEmployeeId(@Param("phoneNumber") long phoneNumber,
			@Param("employeeId") String employeeId);

	@Query("SELECT e FROM Employee e JOIN e.roles r WHERE r.roleName IN :roles")
	List<Employee> findEmployeesByRoles(@Param("roles") List<String> roles);

	@Query("SELECT e FROM Employee e JOIN e.roles r WHERE r.roleName = :roleName")
	List<Employee> findByRoleName(@Param("roleName") String roleName);

	List<Employee> findByRoles_RoleName(String roleName);

    // Fetch employees based on role, status, and experience
    @Query("SELECT e FROM Employee e JOIN e.roles r WHERE r.roleName = :role AND e.status = :status AND e.experience = :experience")
    List<Employee> findByRoleStatusAndExperience(String role, String status, String experience);
    
    @Query("SELECT e FROM Employee e JOIN e.roles r WHERE r.roleName = :role AND e.experience = :experience")
    List<Employee> findByRoleAndExperience(@Param("role") String role, @Param("experience") String experience);
}
