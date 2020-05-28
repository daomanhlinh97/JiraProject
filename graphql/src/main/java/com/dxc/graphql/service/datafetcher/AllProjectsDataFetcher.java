package com.dxc.graphql.service.datafetcher;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import com.dxc.graphql.model.Project;
import com.dxc.graphql.repository.ProjectRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class AllProjectsDataFetcher implements DataFetcher<List<Project>> {
	
    private ProjectRepository projectRepository;
    
    @Autowired
    public AllProjectsDataFetcher(ProjectRepository projectRepository) {
        this.projectRepository=projectRepository;
    }
    @Override
    public List<Project> get(DataFetchingEnvironment dataFetchingEnvironment) {
        return projectRepository.findAll();
    }
}
