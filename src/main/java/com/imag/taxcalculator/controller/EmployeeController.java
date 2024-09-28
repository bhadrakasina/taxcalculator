package com.imag.taxcalculator.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.imag.taxcalculator.dto.TaxResponse;
import com.imag.taxcalculator.entity.Employee;
import com.imag.taxcalculator.service.IEmployeeService;

@RestController
@RequestMapping("/api/employees")
@Validated
public class EmployeeController {
	
	@Autowired
	private IEmployeeService employeeService;
	
	 @PostMapping
	    public ResponseEntity<?> createEmployee(@Valid @RequestBody Employee employee) {
		        employeeService.saveEmployee(employee);
	            return new ResponseEntity<>("Employee created successfully", HttpStatus.CREATED);
	        
	    }
	
	   @GetMapping("/{employeeId}/tax-deductions")
	    public ResponseEntity<TaxResponse> getTaxDeductions(@PathVariable String employeeId) {
	  	        TaxResponse taxResponse = employeeService.calculateTax(employeeId);
	            return new ResponseEntity<>(taxResponse, HttpStatus.OK);
	  	    }
	
	

}
