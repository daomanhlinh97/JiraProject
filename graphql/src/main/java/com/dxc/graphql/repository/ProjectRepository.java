package com.dxc.graphql.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.dxc.graphql.model.Project;

public interface ProjectRepository extends JpaRepository<Project, String> {

}
