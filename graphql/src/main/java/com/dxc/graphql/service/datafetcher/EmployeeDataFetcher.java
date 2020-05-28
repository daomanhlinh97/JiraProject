package com.dxc.graphql.service.datafetcher;

import graphql.schema.DataFetcher;
import com.dxc.graphql.model.Employee;
import com.dxc.graphql.repository.EmployeeRepository;
import graphql.schema.DataFetchingEnvironment;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EmployeeDataFetcher implements DataFetcher<Employee> {

	
    private EmployeeRepository Repository;
    
    @Autowired
    public EmployeeDataFetcher(EmployeeRepository EmployeeRepository){
        this.Repository = EmployeeRepository;
    }
    
    @Override
    public Employee get(DataFetchingEnvironment dataFetchingEnvironment) {

    	
        long id = dataFetchingEnvironment.getArgument("id");
        return Repository.findById(id).orElse(null);
    }
}
