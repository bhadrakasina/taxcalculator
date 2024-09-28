package com.imag.taxcalculator.service;

import com.imag.taxcalculator.dto.TaxResponse;
import com.imag.taxcalculator.entity.Employee;

public interface IEmployeeService {
	
	public void saveEmployee(Employee emp);
	
	public TaxResponse calculateTax(String employeeId);
	
	

}
