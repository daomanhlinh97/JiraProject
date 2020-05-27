package com.dxc.graphql.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.dxc.graphql.model.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

}