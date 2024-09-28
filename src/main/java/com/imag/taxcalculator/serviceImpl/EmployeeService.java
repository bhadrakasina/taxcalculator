package com.imag.taxcalculator.serviceImpl;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.imag.taxcalculator.dto.TaxResponse;
import com.imag.taxcalculator.entity.Employee;
import com.imag.taxcalculator.exceptionhandler.EmployeIdAlredyexitException;
import com.imag.taxcalculator.exceptionhandler.EmployeeNotFoundException;
import com.imag.taxcalculator.repo.EmployeeRepository;
import com.imag.taxcalculator.service.IEmployeeService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class EmployeeService implements IEmployeeService{
	
	@Autowired
	private EmployeeRepository empRepo;

	@Override
	public void saveEmployee(Employee emp) {
		
		 if (empRepo.existsByEmployeeId(emp.getEmployeeId())) {
	            throw new EmployeIdAlredyexitException("Employee with ID " + emp.getEmployeeId() + " already exists.");
	        }
				empRepo.save(emp);
	}

	@Override
	public TaxResponse calculateTax(String employeeId) {
		 Employee employee = empRepo.findById(employeeId)
	                .orElseThrow(() -> new EmployeeNotFoundException("Employee not found"));

	        double yearlySalary = calculateYearlySalary(employee);
	        double taxAmount = calculateTaxForSalary(yearlySalary);
	        double cessAmount = calculateCess(yearlySalary);

	        return new TaxResponse(employee.getEmployeeId(), employee.getFirstName(),
	                employee.getLastName(), yearlySalary, taxAmount, cessAmount);
	}
	
	
	 double calculateYearlySalary(Employee employee) {
		  DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		    LocalDate start;
		    try {
		      	start = LocalDate.parse(employee.getDoj(), formatter);
		    } catch (DateTimeParseException e) {
		        throw new IllegalArgumentException("Invalid date format for DOJ: " + employee.getDoj());
		    }
	        LocalDate today = LocalDate.now();
	        double totalSalary=0;
	        double monthlySalary = employee.getSalary();
	        int currentYear = today.getYear();
	        LocalDate financialYearStart = LocalDate.of(currentYear, 4, 1); 
	        LocalDate financialYearEnd = LocalDate.of(currentYear + 1, 3, 31);
	        
	        if(start.isBefore(financialYearStart)) {
	        	totalSalary=monthlySalary*12;
	        }else {
	        	  int monthsWorked = calculateMonthsWorked(start, financialYearEnd);
	              totalSalary = monthlySalary * monthsWorked; 
	              if (today.isAfter(financialYearEnd)) {
	                  int daysWorkedThisMonth = today.getDayOfMonth();
	                  double dailySalary = monthlySalary / 30; 
	                  totalSalary -= (30 - daysWorkedThisMonth) * dailySalary; 
	              }
	        }
	       return totalSalary;
	    }
	 
	 private int calculateMonthsWorked(LocalDate start, LocalDate end) {
	        int months = 0; 
	        while (!start.isAfter(end)) { 
	            months++; 
	            start = start.plusMonths(1); 
	        }
	        return months; 
	    }

	    private double calculateTaxForSalary(double yearlySalary) {
	        double tax = 0;
	        if (yearlySalary > 250000 && yearlySalary <= 500000) {
	            tax = (yearlySalary - 250000) * 0.05;
	        } else if (yearlySalary > 500000 && yearlySalary <= 1000000) {
	            tax = (250000 * 0.05) + (yearlySalary - 500000) * 0.10;
	        } else if (yearlySalary > 1000000) {
	            tax = (250000 * 0.05) + (500000 * 0.10) + (yearlySalary - 1000000) * 0.20;
	        }
	        return tax;
	    }

	    private double calculateCess(double yearlySalary) {
	        if (yearlySalary > 2500000) {
	            return (yearlySalary - 2500000) * 0.02;
	        }
	        return 0;
	    }

}
