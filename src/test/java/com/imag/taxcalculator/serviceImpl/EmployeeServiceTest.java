package com.imag.taxcalculator.serviceImpl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.imag.taxcalculator.dto.TaxResponse;
import com.imag.taxcalculator.entity.Employee;
import com.imag.taxcalculator.exceptionhandler.EmployeIdAlredyexitException;
import com.imag.taxcalculator.exceptionhandler.EmployeeNotFoundException;
import com.imag.taxcalculator.repo.EmployeeRepository;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTest {

    @Mock
    private EmployeeRepository empRepo;

    @InjectMocks
    private EmployeeService employeeService;

    private Employee employee;

    @BeforeEach
    public void setUp() {
        employee = new Employee();
        employee.setEmployeeId("E123");
        employee.setFirstName("John");
        employee.setLastName("Doe");
        employee.setDoj(LocalDate.now().minusMonths(12).toString());
        employee.setSalary(50000.0);
    }

    @Test
    public void testSaveEmployeeSuccessfully() {
        when(empRepo.existsByEmployeeId(employee.getEmployeeId())).thenReturn(false);

        assertDoesNotThrow(() -> employeeService.saveEmployee(employee));
        verify(empRepo, times(1)).save(employee);
    }

    @Test
    public void testSaveEmployeeWithExistingId() {
        when(empRepo.existsByEmployeeId(employee.getEmployeeId())).thenReturn(true);

        EmployeIdAlredyexitException exception = assertThrows(EmployeIdAlredyexitException.class, () -> {
            employeeService.saveEmployee(employee);
        });

        assertEquals("Employee with ID E123 already exists.", exception.getMessage());
        verify(empRepo, never()).save(any());
    }

    @Test
    public void testCalculateTaxForExistingEmployee() {
        when(empRepo.findById(employee.getEmployeeId())).thenReturn(Optional.of(employee));

        TaxResponse taxResponse = employeeService.calculateTax(employee.getEmployeeId());

        assertNotNull(taxResponse);
        assertEquals(employee.getEmployeeId(), taxResponse.getEmployeeId());
        assertEquals(employee.getFirstName(), taxResponse.getFirstName());
        assertEquals(employee.getLastName(), taxResponse.getLastName());
        assertEquals(600000.0, taxResponse.getYearlySalary()); 
        assertEquals(22500.0, taxResponse.getTaxAmount()); 
        assertEquals(0.0, taxResponse.getCessAmount()); 
    }

    @Test
    public void testCalculateTaxForNonExistingEmployee() {
    	when(empRepo.findById("E999")).thenReturn(Optional.empty()); 

        EmployeeNotFoundException exception = assertThrows(EmployeeNotFoundException.class, () -> {
            employeeService.calculateTax("E999");
        });
        System.out.println(exception.getMessage());
        assertEquals("Employee not found", exception.getMessage());
    }

    @Test
    public void testCalculateYearlySalary() {
        double yearlySalary = employeeService.calculateYearlySalary(employee);
        System.out.println(yearlySalary);
        assertEquals(600000.0, yearlySalary, 0.01); 
    }
}

