package com.imag.taxcalculator.exceptionhandler;

public class EmployeIdAlredyexitException  extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public EmployeIdAlredyexitException(String message) {
        super(message);
    }

    
    public EmployeIdAlredyexitException(String message, Throwable cause) {
        super(message, cause);
    }

}
