package com.imag.taxcalculator.entity;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import lombok.Data;

@Entity
@Data
public class Employee {
	
	@Id
	@Column(nullable = false,unique = true)
	@NotBlank(message = "Employee ID is mandatory")
	private String employeeId;
	
	@NotBlank(message = "First name is mandatory")
	private String firstName;
	
	@NotBlank(message = "Last name is mandatory")
	private String lastName;
	
	@Email(message = "Email should be valid")
	@NotBlank(message = "Email is mandatory")
	private String email;
	
	@ElementCollection
	@Size(min=1,message = "At least one phone number is required")
	private List<String> phoneNumbers;
	
	@NotBlank(message = "Date of joining is mandatory")
	@Pattern(regexp="\\d{4}-\\d{2}-\\d{2}",message="Date must be in the format YYYY-MM-DD")
	private String doj;
	
	@NotNull(message = "Salary is mandatory")
	@Positive(message = "Salary must be a positive number")
	private Double salary;
	
	
	public void setDoj(String doj) {
               if (isValidDate(doj)) {
            this.doj = doj;
        } else {
            throw new IllegalArgumentException("Invalid date format, should be yyyy-MM-dd");
        }
    }
	
	private boolean isValidDate(String dateStr) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate.parse(dateStr, formatter); 
            return true;
        } catch (DateTimeParseException e) {
            return false; 
        }
    }

}
