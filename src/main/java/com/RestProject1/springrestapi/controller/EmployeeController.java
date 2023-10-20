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
import com.RestProject1.springrestapi.dto.DeleteDTO;
import com.RestProject1.springrestapi.dto.EmployeeDTO;
import com.RestProject1.springrestapi.exception.BuisnessException;
import com.RestProject1.springrestapi.model.Benifits;
import com.RestProject1.springrestapi.model.Department;
import com.RestProject1.springrestapi.model.Employee;

import com.RestProject1.springrestapi.repository.BenifitsRepository;
import com.RestProject1.springrestapi.repository.EmployeeRepository;
import com.RestProject1.springrestapi.service.BasicBenefitsService;
import com.RestProject1.springrestapi.service.DepartmentService;
import com.RestProject1.springrestapi.service.EmployeeService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;


@RestController //@controller + @ResponseBody
@Api(value = "Employee Controller")
public class EmployeeController {
	
	@Autowired
	private EmployeeService empService; //as our application starts employeeservice will be injected to our Spring container

	@Autowired
	private Department d;
	@Autowired
	private Employee m;
	@Autowired
	private Benifits b;
	
	@Autowired
	private BasicBenefitsService bService;
	
	
	@Autowired 
	private DepartmentService dService;
	
	@Autowired
	private Convertor convert;
	

	
	//@RequestMapping(value="/employees", method = RequestMethod.GET)
	//@ResponseBody //for RestAPI
	
	@Value("${app.name}") //or ${app.name : Employee Management System}
	private String appName;
	
	@Value("${app.version}") // or ${app.version : version1}
	private String appVersion;
	
	
	@GetMapping("/version")
	@ApiOperation(value = "GET APPLICATION DETAILS")
	public String getAppDetails()
	{
		return appName + "\n" + appVersion;
	}
	
	
	@GetMapping("/employees")  //--->@RequestMapping(--)
	@ApiOperation(value = "GET LIST OF ALL EMPLOYEES")
	public ResponseEntity<List<EmployeeDTO>> getEmployees()
	{

		List<Employee> emp = empService.getEmployees();
		List<EmployeeDTO> dto = convert.entityToDto(emp);
		return new ResponseEntity<List<EmployeeDTO>>(dto, HttpStatus.OK);
	
	}
	
	
	
	
	
	
	@GetMapping("/employees/{id}") //PathVariable
	@ApiOperation(value = "GET EMPLOYEE DETAILS WITH ID")
	public EmployeeDTO getEmployee(@PathVariable("id") Long id)
	{
		return convert.entityToDto(empService.getEmployee(id));
		//return empService.getEmployee(id);
	}
	
	
	
	
	@PutMapping("/employees/{id}")
	@ApiOperation(value = "UPDATE EMPLOYEE DETAILS")
	public EmployeeDTO updateEmployee(@PathVariable Long id, @Valid @RequestBody EmployeeDTO dto)
	{
		System.out.println("Updating the Employee Data for Employee ID : " + id);
		dto.setId(id);
		
		Employee emp = new Employee();
		emp = convert.dtoToEntity(dto);
		
		//Benifits b = new Benifits();
		//Department d = new Department();
		//Employee m = new Employee();
		d = (Department)dService.getDepartment(emp.getDepartment().getId());
		if(emp.getManager().getId() != null)
		{
			m = (Employee)empService.getEmployee(emp.getManager().getId());
		}
		else
		{
			m = null;
		}
		
		b = (Benifits)bService.getbenifit(emp.getBenifits().getId());
		emp.setBenifits(b);
		emp.setDepartment(d);
		emp.setManager(m);
		
		
		
		return convert.entityToDto(empService.updateEmployee(emp));
	}
	
	
	
	
	
	
	@PostMapping("/employees")
	@ApiOperation(value = "CREATE A NEW EMPLOYEE")
	public ResponseEntity<EmployeeDTO> saveEmployee(@Valid @RequestBody EmployeeDTO empreq)
	{
		
		Employee e = new Employee();
		e = convert.dtoToEntity(empreq);
		
		//Benifits b = new Benifits();
		//Department d = new Department();
		//Employee m = new Employee();
		d = (Department)dService.getDepartment(e.getDepartment().getId());
		//m = (Manager)mService.getManager(empreq.getManager().getId());
		if(e.getManager().getId() != null)
		{
			m = (Employee)empService.getEmployee(e.getManager().getId());
		}
		else
		{
			m = null;
		}
		
		b = (Benifits)bService.getbenifit(e.getBenifits().getId());
		System.out.println("------->" + e.getBenifits().getId());
		System.out.println("------->" + (Benifits)bService.getbenifit(e.getBenifits().getId()));
		System.out.println("------->" + b);
		e.setBenifits(b);
		e.setDepartment(d);
		e.setManager(m);
		
		//return empService.saveEmployee(empreq); */

		
		return new ResponseEntity<EmployeeDTO>(convert.entityToDto(empService.saveEmployee(e)), HttpStatus.OK);

	}
	
	
	
	
	
	
	
	//localhost:8080/employees?id=24
	@DeleteMapping("/employees") //dealing with requestParams
	@ApiOperation(value = "DELETE AN EMPLOYEE")
	public ResponseEntity<DeleteDTO> deleteEmployee(@RequestParam("id") Long id) //if the requestparam parameter name is same is Long variable then we do not have to specify paramter in requestparam
	{

		empService.deleteEmployee(id);
		return new ResponseEntity<DeleteDTO>(new DeleteDTO("Employee is deleted", id),  HttpStatus.OK);
	}
	
	
	
	
	
	@GetMapping("/employees/getEmployeesByNameAndLocation")
	@ApiOperation(value = "GET EMPLOYEE BY NAME AND LOCATION")
	public ResponseEntity<List<EmployeeDTO>> getEmployeesByNameAndLocation(@RequestParam String name, @RequestParam String location)
	{
		return new ResponseEntity<List<EmployeeDTO>>(convert.entityToDto(empService.getEmployeesbyNameandLocation(name, location)), HttpStatus.OK);
	}
	
	
	@GetMapping(value = "/employeesByFilter")
	@ApiOperation(value = "GET DELETED EMPLOYEES BY SETTING VALUE OF 'isDeleted' TO TRUE")
	public ResponseEntity<List<EmployeeDTO>> findAll(
	@RequestParam(value = "isDeleted", defaultValue = "false") boolean isDeleted) {
	    List<Employee> emp = empService.findAllFilter(isDeleted);
		List<EmployeeDTO> dto = convert.entityToDto(emp);
		for(EmployeeDTO e : dto )
		{
			System.out.println(e);
		}
	    
	    return new ResponseEntity<List<EmployeeDTO>>(dto, HttpStatus.OK);
	}

	@GetMapping("/employeesByName")
	@ApiOperation(value = "GET LIST OF EMPLOYEES BY NAME")
	public ResponseEntity<List<EmployeeDTO>> getEmployeesByName(@RequestParam String name) {
		List<Employee> employees = empService.getEmployeesByName(name);
		List<EmployeeDTO> dto = convert.entityToDto(employees);
		return new ResponseEntity<List<EmployeeDTO>>(dto, HttpStatus.OK);
	}

}
