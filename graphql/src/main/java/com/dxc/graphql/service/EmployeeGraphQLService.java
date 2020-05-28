package com.dxc.graphql.service;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.tomcat.util.json.ParseException;
import org.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import com.dxc.graphql.model.Employee;
import com.dxc.graphql.repository.EmployeeRepository;
import com.dxc.graphql.service.datafetcher.AddingEmployeeDataFetcher;
import com.dxc.graphql.service.datafetcher.AllEmployeesDataFetcher;
import com.dxc.graphql.service.datafetcher.EmployeeDataFetcher;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import graphql.GraphQL;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;

@Service
public class EmployeeGraphQLService {
	
	private EmployeeRepository Repository;
	private AllEmployeesDataFetcher allEmployeesDataFetcher;
	private EmployeeDataFetcher employeeDataFetcher;
	private AddingEmployeeDataFetcher addingEmployeeDataFetcher;
	
	@Value("classpath:employees.graphql")
	Resource resource;

	private GraphQL graphQL;

	@Autowired
	public EmployeeGraphQLService(EmployeeRepository bookRepository, AllEmployeesDataFetcher allBooksDataFetcher,
			EmployeeDataFetcher bookDataFetcher,AddingEmployeeDataFetcher addingEmployeeDataFetcher) {
		this.Repository = bookRepository;
		this.allEmployeesDataFetcher = allBooksDataFetcher;
		this.employeeDataFetcher = bookDataFetcher;
		this.addingEmployeeDataFetcher = addingEmployeeDataFetcher;
	}
	


	@PostConstruct
	private void loadSchema() throws IOException, JSONException, ParseException, org.json.JSONException {

		// Get the graphql file
		pullDataEmployee();
		File file = resource.getFile();
		// Parse SchemaF
		TypeDefinitionRegistry typeDefinitionRegistry = new SchemaParser().parse(file);
		RuntimeWiring runtimeWiring = buildRuntimeWiring();
		GraphQLSchema graphQLSchema = new SchemaGenerator().makeExecutableSchema(typeDefinitionRegistry, runtimeWiring);
		graphQL = GraphQL.newGraphQL(graphQLSchema).build();
	}
	
	
public void pullDataEmployee() throws IOException, JSONException, ParseException, org.json.JSONException{
		
		URL url = new URL("http://localhost:8080/rest/api/2/group/member?groupname=jira-core-users&startAt=0");
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
        JSONArray issues = obj.getJSONArray("values");
        for(int i = 0; i<issues.length(); i++) {
        	Employee e =new Employee();
        	e.setId(issues.getJSONObject(i).getString("key"));
        	e.setName(issues.getJSONObject(i).getString("name"));
        	e.setEmailAddress(issues.getJSONObject(i).getString("emailAddress"));;
        	Repository.save(e);
        }
	}
	
	private RuntimeWiring buildRuntimeWiring() {
        return RuntimeWiring.newRuntimeWiring()
                .type("Query", typeWiring -> typeWiring
                		.dataFetcher("allEmployees", allEmployeesDataFetcher)
                		.dataFetcher("employee", employeeDataFetcher))
                .type("Mutation", typeWiring -> typeWiring
                		.dataFetcher("addEmployee", addingEmployeeDataFetcher))
                .build();
    }

	public GraphQL getGraphQL() {
		return graphQL;
	}
}