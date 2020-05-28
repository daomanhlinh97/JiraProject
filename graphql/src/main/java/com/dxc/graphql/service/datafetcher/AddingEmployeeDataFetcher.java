package com.dxc.graphql.service.datafetcher;

import java.util.Date;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dxc.graphql.model.Employee;
import com.dxc.graphql.repository.EmployeeRepository;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;

@Component
public class AddingEmployeeDataFetcher implements DataFetcher<Employee> {
	
	private EmployeeRepository Repository;

	@Autowired
	public AddingEmployeeDataFetcher(EmployeeRepository ProjectRepository) {
		this.Repository = ProjectRepository;
	}

	@Override
	public Employee get(DataFetchingEnvironment dataFetchingEnvironment) {
		
		String id = dataFetchingEnvironment.getArgument("id");
		String name = dataFetchingEnvironment.getArgument("name");
		String emailAddress = dataFetchingEnvironment.getArgument("emailAddress");
		
		Employee newProject = new Employee();
		newProject.setId(id);
		newProject.setName(name);
		newProject.setEmailAddress(emailAddress);;
    	
    	Repository.save(newProject);
        return newProject;
	}
}
