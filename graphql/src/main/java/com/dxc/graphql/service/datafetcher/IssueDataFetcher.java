package com.dxc.graphql.service.datafetcher;

import graphql.schema.DataFetcher;
import com.dxc.graphql.model.Issue;
import com.dxc.graphql.repository.IssueRepository;
import graphql.schema.DataFetchingEnvironment;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class IssueDataFetcher implements DataFetcher<Issue> {
	
    private IssueRepository Repository;
    
    @Autowired
    public IssueDataFetcher(IssueRepository issueRepository){
        this.Repository = issueRepository;
    }
    
    @Override
    public Issue get(DataFetchingEnvironment dataFetchingEnvironment) {
    	
    	String id = dataFetchingEnvironment.getArgument("id");
        return Repository.findById(id).orElse(null);
    }
}
