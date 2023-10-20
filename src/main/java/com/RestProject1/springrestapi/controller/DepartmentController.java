package com.RestProject1.springrestapi.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.RestProject1.springrestapi.converter.Convertor;
import com.RestProject1.springrestapi.converter.DepartmentConvertor;
import com.RestProject1.springrestapi.dto.DeleteDTO;
import com.RestProject1.springrestapi.dto.DepartmentDTO;
import com.RestProject1.springrestapi.dto.EmployeeDTO;
import com.RestProject1.springrestapi.model.Benifits;
import com.RestProject1.springrestapi.model.Department;
import com.RestProject1.springrestapi.model.Employee;
import com.RestProject1.springrestapi.service.BasicBenefitsService;
import com.RestProject1.springrestapi.service.DepartmentService;
import com.RestProject1.springrestapi.service.EmployeeService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;


@RestController
@Api(value = "Department Controller")
public class DepartmentController {
	
	@Autowired
	private EmployeeService empService; //as our application starts employeeservice will be injected to our Spring container

	@Autowired
	private DepartmentService dService;
	
	@Autowired
	private DepartmentConvertor convert;
	
	@Autowired
	private Convertor convert2;
	

	
	@GetMapping("/departments")  //--->@RequestMapping(--)
	@ApiOperation(value = "GET LIST OF ALL DEPARTMENTS")
	public List<DepartmentDTO> getDepartments()
	{
		List<Department> d = dService.getDepartments();
		List<DepartmentDTO> dto = convert.entityToDto(d);
		return dto;
	}
	
	@GetMapping("/departments/{id}") //PathVariable
	@ApiOperation(value = "GET DEPARTMENT WITH THE ID")
	public DepartmentDTO getDepartment(@PathVariable("id") Long id)
	{
		return convert.entityToDto(dService.getDepartment(id));
	}
	
	@PutMapping("/departments/{id}")
	@ApiOperation(value = "UPDATE A DEPARTMENT")
	public DepartmentDTO updateDepartment(@PathVariable Long id, @Valid @RequestBody DepartmentDTO dto)
	{
		
		System.out.println("Updating the Department for ID : " + id);
		dto.setId(id);
		
		Department d = new Department();
		d = convert.dtoToEntity(dto);
		
	
		return convert.entityToDto(dService.updateDepartment(d));
	}
	
	@PostMapping("/departments")
	@ApiOperation(value = "CREATE A DEPARTMENT")
	public DepartmentDTO saveDepartment(@Valid @RequestBody DepartmentDTO dto)
	{
		Department d = new Department();
		d = convert.dtoToEntity(dto);

		return convert.entityToDto(dService.saveDepartment(d));
	}
	
	//localhost:8080/employees?id=24
	@DeleteMapping("/departments") //dealing with requestParams
	@ApiOperation(value = "DELETE A DEPARTMENT")
	public ResponseEntity<DeleteDTO> deleteEmployee(@RequestParam("id") Long id) //if the requestparam parameter name is same is Long variable then we do not have to specify paramter in requestparam
	{
		dService.deleteDepartment(id);
		return new ResponseEntity<DeleteDTO>(new DeleteDTO("Department is deleted", id),  HttpStatus.OK);
	}
	
	@GetMapping("departments/getEmployees")
	@ApiOperation(value = "GET LIST OF ALL EMPLOYEES WITH THE GIVEN DEPARTMENT ID")
	public List<EmployeeDTO> getEmployees(@RequestParam Long id)
	{
		List<Employee> emp = dService.getEmployees(id);
		List<EmployeeDTO> dto = convert2.entityToDto(emp);
		return dto;
	}

	@GetMapping("departments/getByNames")
	@ApiOperation(value = "GET LIST OF ALL DEPARTMENTS WITH THE GIVEN NAME")
	public List<DepartmentDTO> getByNames(@RequestParam String name)
	{
		List<Department> departments = dService.getDepartmentsByName(name);
		List<DepartmentDTO> dto = convert.entityToDto(departments);
		return dto;
	}
}
