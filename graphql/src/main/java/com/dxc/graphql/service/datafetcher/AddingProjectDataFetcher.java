package com.dxc.graphql.service.datafetcher;

import java.util.Date;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dxc.graphql.model.Project;
import com.dxc.graphql.repository.ProjectRepository;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;

@Component
public class AddingProjectDataFetcher implements DataFetcher<Project> {
	
	private ProjectRepository ProjectRepository;

	@Autowired
	public AddingProjectDataFetcher(ProjectRepository ProjectRepository) {
		this.ProjectRepository = ProjectRepository;
	}

	@Override
	public Project get(DataFetchingEnvironment dataFetchingEnvironment) {
		
		String id = dataFetchingEnvironment.getArgument("id");
		String name = dataFetchingEnvironment.getArgument("name");
		String projectTypeKey = dataFetchingEnvironment.getArgument("project_type_key");
		
		Project newProject = new Project();
		newProject.setId(id);
		newProject.setName(name);
		newProject.setProjectTypeKey(projectTypeKey);
    	
    	ProjectRepository.save(newProject);
        return newProject;
	}
}
