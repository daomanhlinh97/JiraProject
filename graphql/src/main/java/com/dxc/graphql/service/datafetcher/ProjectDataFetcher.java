package com.dxc.graphql.service.datafetcher;

import graphql.schema.DataFetcher;
import com.dxc.graphql.model.Project;
import com.dxc.graphql.repository.ProjectRepository;
import graphql.schema.DataFetchingEnvironment;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProjectDataFetcher implements DataFetcher<Project> {

    private ProjectRepository projectRepository;
    
    @Autowired
    public ProjectDataFetcher(ProjectRepository projectRepository){
        this.projectRepository = projectRepository;
    }
    
    @Override
    public Project get(DataFetchingEnvironment dataFetchingEnvironment) {
    	
        String id = dataFetchingEnvironment.getArgument("id");
        return projectRepository.findById(id).orElse(null);
    }
}
