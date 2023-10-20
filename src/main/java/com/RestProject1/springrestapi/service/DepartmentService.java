package com.RestProject1.springrestapi.service;

import java.util.List;

import com.RestProject1.springrestapi.model.Department;
import com.RestProject1.springrestapi.model.Employee;

public interface DepartmentService {
	
	public List<Department> getDepartments();
	public Department saveDepartment(Department d);
	public Department getDepartment(Long id);
	public void deleteDepartment(Long id);
	public Department updateDepartment(Department d);
	
	public List<Employee> getEmployees(Long id);
}
