package com.imag.taxcalculator.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.imag.taxcalculator.entity.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, String> {

	
	boolean existsByEmployeeId(String employeeId);
}
