package com.dxc.graphql.service.datafetcher;

import graphql.schema.DataFetcher;
import com.dxc.graphql.model.Issue;
import com.dxc.graphql.repository.IssueRepository;
import graphql.schema.DataFetchingEnvironment;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class IssueDataFetcher implements DataFetcher<Issue> {
	
    private IssueRepository issueRepository;
    
    @Autowired
    public IssueDataFetcher(IssueRepository issueRepository){
        this.issueRepository = issueRepository;
    }
    
    @Override
    public Issue get(DataFetchingEnvironment dataFetchingEnvironment) {
    	
        String id = dataFetchingEnvironment.getArgument("id");
        return issueRepository.findById(id).orElse(null);
    }
}
