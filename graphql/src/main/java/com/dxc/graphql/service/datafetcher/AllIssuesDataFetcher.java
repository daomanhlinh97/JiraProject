package com.dxc.graphql.service.datafetcher;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import com.dxc.graphql.model.Issue;
import com.dxc.graphql.repository.IssueRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class AllIssuesDataFetcher implements DataFetcher<List<Issue>> {
	
    private IssueRepository issueRepository;
    
    @Autowired
    public AllIssuesDataFetcher(IssueRepository issueRepository) {
        this.issueRepository= issueRepository;
    }
    @Override
    public List<Issue> get(DataFetchingEnvironment dataFetchingEnvironment) {
        return issueRepository.findAll();
    }
}
