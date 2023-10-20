package com.RestProject1.springrestapi.converter;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.stereotype.Component;

import com.RestProject1.springrestapi.dto.EmployeeDTO;
import com.RestProject1.springrestapi.model.Benifits;
import com.RestProject1.springrestapi.model.Department;
import com.RestProject1.springrestapi.model.Employee;

@Component
public class Convertor {
	
	public EmployeeDTO entityToDto(Employee emp)
	{
		EmployeeDTO dto = new EmployeeDTO();
		dto.setId(emp.getId());
		dto.setAge(emp.getAge());
		dto.setEmail(emp.getEmail());
		dto.setName(emp.getName());
		dto.setBenifits(emp.getBenifits().getId());
		dto.setDepartment(emp.getDepartment().getId());
		dto.setLocation(emp.getLocation());
		//dto.setDeleted(emp.getDeleted());
		if(emp.getManager() != null)
		{
			dto.setManager(emp.getManager().getId());
		}
		else
		{
			dto.setManager(null);
		}
		
		return dto;
	}
	
	
	public List<EmployeeDTO> entityToDto(List<Employee> emp)
	{
		return (List<EmployeeDTO>)emp.stream().map(this::entityToDto).collect(Collectors.toList());
	}
	
	
	
	
	public Employee dtoToEntity(EmployeeDTO dto)
	{
		Employee emp = new Employee();
		Benifits b = new Benifits();
		b.setId(dto.getBenifits());
		
		Department d = new Department();
		d.setId(dto.getDepartment());
		
		Employee m = new Employee();
		m.setId(dto.getManager());
		
		emp.setLocation(dto.getLocation());
		emp.setId(dto.getId());
		emp.setAge(dto.getAge());
		emp.setEmail(dto.getEmail());
		emp.setName(dto.getName());
		emp.setBenifits(b);
		emp.setDepartment(d);
		emp.setManager(m);
		//emp.setDeleted(dto.getDeleted());
		
		return emp;
	}
	
	
	public List<Employee> dtoToEntity(List<EmployeeDTO> dto)
	{
		return (List<Employee>)dto.stream().map(this::dtoToEntity).collect(Collectors.toList());
	}
	
	

}
