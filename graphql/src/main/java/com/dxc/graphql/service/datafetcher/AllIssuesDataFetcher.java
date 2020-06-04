package com.dxc.graphql.service.datafetcher;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import com.dxc.graphql.model.Issue;
import com.dxc.graphql.repository.IssueRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class AllIssuesDataFetcher implements DataFetcher<List<Issue>> {
	
    private IssueRepository issueRepository;
    
    @Autowired
    public AllIssuesDataFetcher(IssueRepository issueRepository) {
        this.issueRepository= issueRepository;
    }
    @Override
    public List<Issue> get(DataFetchingEnvironment dataFetchingEnvironment) {
    	String creatorName = dataFetchingEnvironment.getArgument("creatorName");
        String projectName = dataFetchingEnvironment.getArgument("projectName");
        String datefrom = dataFetchingEnvironment.getArgument("datefrom");
        String dateto = dataFetchingEnvironment.getArgument("dateto");
        List<Issue> issueList =  issueRepository.findAll();
        if(!creatorName.equals("null") && !creatorName.isEmpty()) {
        	issueList = issueList.stream().filter(issue -> issue.getCreatorName().equals(creatorName)).collect(Collectors.<Issue>toList());
        }
        if(!projectName.equals("null") && !projectName.isEmpty()) {
        	issueList = issueList.stream().filter(issue -> issue.getProjectName().equals(projectName)).collect(Collectors.<Issue>toList());
        }
	    if(!datefrom.equals("null") && !dateto.equals("null") && !datefrom.isEmpty() && !dateto.isEmpty()) {
	        try {
	        	Date dateFrom=new SimpleDateFormat("yyyy-MM-dd").parse(datefrom);
				Date dateTo=new SimpleDateFormat("yyyy-MM-dd").parse(dateto);
	        	for(int i=0; i<issueList.size(); i++) {
					Date date=new SimpleDateFormat("yyyy-MM-dd").parse(issueList.get(i).getCreated());
					if(date.before(dateFrom) || date.after(dateTo)) {
						issueList.remove(i);
						i--;
					}
	        	}
			} catch (ParseException e) {
					e.printStackTrace();
	        }
	    }
        return issueList;
    }
}
