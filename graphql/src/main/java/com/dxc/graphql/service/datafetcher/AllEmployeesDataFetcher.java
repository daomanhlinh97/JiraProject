package com.dxc.graphql.service.datafetcher;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import com.dxc.graphql.model.Employee;
import com.dxc.graphql.repository.EmployeeRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class AllEmployeesDataFetcher implements DataFetcher<List<Employee>> {
	
    private EmployeeRepository Repository;
    
    @Autowired
    public AllEmployeesDataFetcher(EmployeeRepository employeeRepository) {
        this.Repository= employeeRepository;
    }
    @Override
    public List<Employee> get(DataFetchingEnvironment dataFetchingEnvironment) {
        return Repository.findAll();
    }
}
