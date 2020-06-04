package com.dxc.graphql.service.datafetcher;

import java.util.Date;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dxc.graphql.model.Issue;
import com.dxc.graphql.repository.IssueRepository;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;

@Component
public class AddingIssueDataFetcher implements DataFetcher<Issue> {
	
	private IssueRepository IssueRepository;

	@Autowired
	public AddingIssueDataFetcher(IssueRepository IssueRepository) {
		this.IssueRepository = IssueRepository;
	}

	@Override
	public Issue get(DataFetchingEnvironment dataFetchingEnvironment) {

		String id = dataFetchingEnvironment.getArgument("id");
		String creator_name = dataFetchingEnvironment.getArgument("creatorName");
		String description = dataFetchingEnvironment.getArgument("description");
		String priority = dataFetchingEnvironment.getArgument("priority");
		String status = dataFetchingEnvironment.getArgument("status");
		String summary = dataFetchingEnvironment.getArgument("summary");
		String type = dataFetchingEnvironment.getArgument("type");
		
		
		Issue newIssue = new Issue();
		newIssue.setId(id);
		newIssue.setCreatorName(creator_name);;
		newIssue.setDescription(description);
		newIssue.setPriority(priority);
		newIssue.setStatus(status);
		newIssue.setSummary(summary);
		newIssue.setType(type);
//		newIssue.setCreated(created);
    	
    	IssueRepository.save(newIssue);
        return newIssue;
	}
}
