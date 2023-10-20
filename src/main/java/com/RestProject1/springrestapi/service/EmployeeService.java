package com.RestProject1.springrestapi.service;

import java.util.List;

import com.RestProject1.springrestapi.dto.EmployeeDTO;
import com.RestProject1.springrestapi.model.Employee;

public interface EmployeeService {
	
	public List<Employee> getEmployees();
	public Employee saveEmployee(Employee e);
	public Employee getEmployee(Long id);
	public void deleteEmployee(Long id);
	public Employee updateEmployee(Employee emp);
	public List<Employee> getEmployeesbyNameandLocation(String name, String location);
	public List<Employee> findAllFilter(boolean isDeleted);
}
