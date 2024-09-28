package com.imag.taxcalculator.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ErrorResponse {
	
	 private String message;
	 private int status;
	 

}
