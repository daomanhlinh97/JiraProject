package com.dxc.graphql.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;
import java.util.List;
import java.util.stream.Stream;

import javax.annotation.PostConstruct;

import org.json.JSONArray;
import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import com.dxc.graphql.model.Issue;
import com.dxc.graphql.repository.IssueRepository;
import com.dxc.graphql.service.datafetcher.AddingIssueDataFetcher;
import com.dxc.graphql.service.datafetcher.AllIssuesDataFetcher;
import com.dxc.graphql.service.datafetcher.IssueDataFetcher;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import graphql.GraphQL;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;

@Service
public class IssueGraphQLService {
	
	private IssueRepository issueRepository;
	private AllIssuesDataFetcher allIssuesDataFetcher;
	private IssueDataFetcher issueDataFetcher;
	private AddingIssueDataFetcher addingIssueDataFetcher;
	
	@Value("classpath:issues.graphql")
	Resource resource;

	private GraphQL graphQL;

	@Autowired
	public IssueGraphQLService(IssueRepository bookRepository, AllIssuesDataFetcher allBooksDataFetcher,
			IssueDataFetcher bookDataFetcher, AddingIssueDataFetcher addingBookDataFetcher) {
		this.issueRepository = bookRepository;
		this.allIssuesDataFetcher = allBooksDataFetcher;
		this.issueDataFetcher = bookDataFetcher;
		this.addingIssueDataFetcher = addingBookDataFetcher;
	}

	@PostConstruct
	private void loadSchema() throws IOException, JSONException {
		// Get the graphql file
		pullDataIssue();
		File file = resource.getFile();
		// Parse SchemaF
		TypeDefinitionRegistry typeDefinitionRegistry = new SchemaParser().parse(file);
		RuntimeWiring runtimeWiring = buildRuntimeWiring();
		GraphQLSchema graphQLSchema = new SchemaGenerator().makeExecutableSchema(typeDefinitionRegistry, runtimeWiring);
		graphQL = GraphQL.newGraphQL(graphQLSchema).build();
	}
	
	
	public void pullDataIssue() throws IOException, org.json.JSONException{
		URL url = new URL("http://localhost:8080/rest/api/2/search?");
        String encoding = Base64.getEncoder().encodeToString("daomanhlinh97:daomanhlinh".getBytes("utf-8"));
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Accept", "application/json");
        conn.setRequestProperty  ("Authorization", "Basic " + encoding);
        if (conn.getResponseCode() != 200) {
            throw new RuntimeException("Failed : HTTP error code : "
                    + conn.getResponseCode());
        }
        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
            sb.append(line+"\n");
        }
        org.json.JSONObject obj = new org.json.JSONObject(sb.toString());
        JSONArray issues = obj.getJSONArray("issues");
        for(int i = 0; i<issues.length(); i++) {
        	Issue issue =new Issue();
        	issue.setId(issues.getJSONObject(i).getString("id"));
        	issue.setDescription(issues.getJSONObject(i).getJSONObject("fields").getString("description"));
        	issue.setCreatorName(issues.getJSONObject(i).getJSONObject("fields").getJSONObject("creator").getString("name"));
        	issue.setPriority(issues.getJSONObject(i).getJSONObject("fields").getJSONObject("priority").getString("name"));
        	issue.setSummary(issues.getJSONObject(i).getJSONObject("fields").getString("summary"));
        	issue.setType(issues.getJSONObject(i).getJSONObject("fields").getJSONObject("issuetype").getString("name"));
        	issue.setStatus(issues.getJSONObject(i).getJSONObject("fields").getJSONObject("status").getString("name"));
        	issueRepository.save(issue);
        }
	}
	
	private RuntimeWiring buildRuntimeWiring() {
        return RuntimeWiring.newRuntimeWiring()
                .type("Query", typeWiring -> typeWiring
                		.dataFetcher("allIssues", allIssuesDataFetcher)
                		.dataFetcher("issue", issueDataFetcher))
                .type("Mutation", typeWiring -> typeWiring
                		.dataFetcher("addIssue", addingIssueDataFetcher))
                .build();
    }

	public GraphQL getGraphQL() {
		return graphQL;
	}
}