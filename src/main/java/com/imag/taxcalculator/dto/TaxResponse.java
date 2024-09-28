package com.imag.taxcalculator.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TaxResponse {
	
	
	private String employeeId;
    private String firstName;
    private String lastName;
    private double yearlySalary;
    private double taxAmount;
    private double cessAmount;
    
    

}
