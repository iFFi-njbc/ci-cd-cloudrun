package com.RestProject1.springrestapi.service;

import java.util.List;

import org.springframework.web.bind.annotation.RequestParam;

import com.RestProject1.springrestapi.model.Benifits;
import com.RestProject1.springrestapi.model.Employee;


public interface BenifitsService {
	
	public Benifits getbenifit(Long id);
	public List<Benifits> getBenifits();
	public Benifits saveBenefit(Benifits b);
	public void deleteBenifit(Long id);
	public Benifits updateBenifit(Benifits b);
	
	public List<Employee> getEmployees(Long id);
	//public List<Employee> getEmployees(Benifits b);
	
}
