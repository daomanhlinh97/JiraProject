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

import com.dxc.graphql.model.Project;
import com.dxc.graphql.repository.ProjectRepository;
import com.dxc.graphql.service.datafetcher.AddingProjectDataFetcher;
import com.dxc.graphql.service.datafetcher.AllProjectsDataFetcher;
import com.dxc.graphql.service.datafetcher.ProjectDataFetcher;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import graphql.GraphQL;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;

@Service
public class ProjectGraphQLService {
	
	private ProjectRepository projectRepository;
	private AllProjectsDataFetcher allProjectsDataFetcher;
	private ProjectDataFetcher projectDataFetcher;
	private AddingProjectDataFetcher addingProjectDataFetcher;
	
	@Value("classpath:projects.graphql")
	Resource resource;

	private GraphQL graphQL;

	@Autowired
	public ProjectGraphQLService(ProjectRepository bookRepository, AllProjectsDataFetcher allBooksDataFetcher,
			ProjectDataFetcher bookDataFetcher, AddingProjectDataFetcher addingBookDataFetcher) {
		this.projectRepository = bookRepository;
		this.allProjectsDataFetcher = allBooksDataFetcher;
		this.projectDataFetcher = bookDataFetcher;
		this.addingProjectDataFetcher = addingBookDataFetcher;
	}

	@PostConstruct
	private void loadSchema() throws IOException, JSONException {
		// Get the graphql file
		pullDataProject();
		File file = resource.getFile();
		// Parse SchemaF
		TypeDefinitionRegistry typeDefinitionRegistry = new SchemaParser().parse(file);
		RuntimeWiring runtimeWiring = buildRuntimeWiring();
		GraphQLSchema graphQLSchema = new SchemaGenerator().makeExecutableSchema(typeDefinitionRegistry, runtimeWiring);
		graphQL = GraphQL.newGraphQL(graphQLSchema).build();
	}
	
	
	public void pullDataProject() throws IOException, JSONException{
		URL url = new URL("http://localhost:8080/rest/api/2/project");
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
		JSONArray projects = new JSONArray(sb.toString());
		for(int i = 0; i<projects.length(); i++) {
			Project project = new Project();
			project.setName(projects.getJSONObject(i).getString("name"));
			project.setId(projects.getJSONObject(i).getString("key"));
			project.setProjectTypeKey(projects.getJSONObject(i).getString("projectTypeKey"));
			projectRepository.save(project);
		}
	}
	
	private RuntimeWiring buildRuntimeWiring() {
        return RuntimeWiring.newRuntimeWiring()
                .type("Query", typeWiring -> typeWiring
                		.dataFetcher("allProjects", allProjectsDataFetcher)
                		.dataFetcher("project", projectDataFetcher))
                .type("Mutation", typeWiring -> typeWiring
                		.dataFetcher("addProject", addingProjectDataFetcher))
                .build();
    }

	public GraphQL getGraphQL() {
		return graphQL;
	}
}