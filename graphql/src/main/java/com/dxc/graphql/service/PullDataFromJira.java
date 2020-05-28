//package com.dxc.graphql.service;
//
//import java.io.BufferedReader;
//import java.io.ByteArrayInputStream;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.InputStreamReader;
//import java.net.HttpURLConnection;
//import java.net.URL;
//import java.nio.charset.Charset;
//import java.nio.charset.StandardCharsets;
//import java.util.Base64;
//import java.util.List;
//
//import javax.annotation.PostConstruct;
//
//import org.apache.commons.io.IOUtils;
//import org.apache.tomcat.util.json.JSONParser;
//import org.apache.tomcat.util.json.ParseException;
//import org.json.JSONArray;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.configurationprocessor.json.JSONException;
//import org.springframework.boot.configurationprocessor.json.JSONObject;
//import org.springframework.boot.configurationprocessor.json.JSONTokener;
//import org.springframework.stereotype.Component;
//import org.springframework.stereotype.Service;
//import org.springframework.web.client.RestTemplate;
//
//import com.dxc.graphql.model.Employee;
//import com.dxc.graphql.model.EmployeeLogwork;
//import com.dxc.graphql.model.Issue;
//import com.dxc.graphql.model.Project;
//import com.dxc.graphql.repository.EmployeeLogworkRepository;
//import com.dxc.graphql.repository.EmployeeRepository;
//import com.dxc.graphql.repository.IssueRepository;
//import com.dxc.graphql.repository.ProjectRepository;
//
//import com.google.gson.Gson;
//import com.google.gson.reflect.TypeToken;
//
//@Component
//public class PullDataFromJira {
//	@Autowired
//	private EmployeeRepository EmployeeRepository;
//	@Autowired
//	private EmployeeLogworkRepository EmployeeLogworkRepository;
//	@Autowired
//	private IssueRepository IssueRepository;
//	@Autowired
//	private ProjectRepository ProjectRepository;
//	
//	@PostConstruct
//	public void pullDataProject() throws IOException{
//		URL url = new URL("http://localhost:8080/rest/api/2/project");
//        String encoding = Base64.getEncoder().encodeToString("daomanhlinh97:daomanhlinh".getBytes("utf-8"));
//        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//        conn.setRequestMethod("GET");
//        conn.setRequestProperty("Accept", "application/json");
//        conn.setRequestProperty  ("Authorization", "Basic " + encoding);
//        if (conn.getResponseCode() != 200) {
//            throw new RuntimeException("Failed : HTTP error code : "
//                    + conn.getResponseCode());
//        }
//        
//        InputStreamReader isr = new InputStreamReader(conn.getInputStream());
//        TypeToken<List<Project>> token = new TypeToken<List<Project>>(){};
//        List<Project> list = new Gson().fromJson(isr, token.getType());
//        for (int i=0;i<list.size();i++)
//        {
//      	  	ProjectRepository.save(list.get(i));
//        }
//	}
//	
//	@PostConstruct
//	public void pullDataEmployee() throws IOException, JSONException, ParseException, org.json.JSONException{
//		
//		URL url = new URL("http://localhost:8080/rest/api/2/group/member?groupname=jira-core-users&startAt=0");
//        String encoding = Base64.getEncoder().encodeToString("daomanhlinh97:daomanhlinh".getBytes("utf-8"));
//        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//        conn.setRequestMethod("GET");
//        conn.setRequestProperty("Accept", "application/json");
//        conn.setRequestProperty  ("Authorization", "Basic " + encoding);
//        if (conn.getResponseCode() != 200) {
//            throw new RuntimeException("Failed : HTTP error code : "
//                    + conn.getResponseCode());
//        }
//        
//        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
//        StringBuilder sb = new StringBuilder();
//        String line;
//        while ((line = br.readLine()) != null) {
//            sb.append(line+"\n");
//        }
//
//        org.json.JSONObject obj = new org.json.JSONObject(sb.toString());
//        JSONArray issues = obj.getJSONArray("values");
//        for(int i = 0; i<issues.length(); i++) {
//        	Employee e =new Employee();
//        	e.setId(issues.getJSONObject(i).getString("key"));
//        	e.setName(issues.getJSONObject(i).getString("name"));
//        	e.setEmailAddress(issues.getJSONObject(i).getString("emailAddress"));;
//        	EmployeeRepository.save(e);
//        }
//	}
//	
//	@PostConstruct
//	public void pullDataIssue() throws IOException, org.json.JSONException{
//		URL url = new URL("http://localhost:8080/rest/api/2/search?");
//        String encoding = Base64.getEncoder().encodeToString("daomanhlinh97:daomanhlinh".getBytes("utf-8"));
//        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//        conn.setRequestMethod("GET");
//        conn.setRequestProperty("Accept", "application/json");
//        conn.setRequestProperty  ("Authorization", "Basic " + encoding);
//        if (conn.getResponseCode() != 200) {
//            throw new RuntimeException("Failed : HTTP error code : "
//                    + conn.getResponseCode());
//        }
//        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
//        StringBuilder sb = new StringBuilder();
//        String line;
//        while ((line = br.readLine()) != null) {
//            sb.append(line+"\n");
//        }
//        org.json.JSONObject obj = new org.json.JSONObject(sb.toString());
//        JSONArray issues = obj.getJSONArray("issues");
//        for(int i = 0; i<issues.length(); i++) {
//        	Issue issue =new Issue();
//        	issue.setId(issues.getJSONObject(i).getString("id"));
//        	issue.setDescription(issues.getJSONObject(i).getJSONObject("fields").getString("description"));
//        	issue.setCreatorName(issues.getJSONObject(i).getJSONObject("fields").getJSONObject("creator").getString("name"));
//        	issue.setPriority(issues.getJSONObject(i).getJSONObject("fields").getJSONObject("priority").getString("name"));
//        	issue.setSummary(issues.getJSONObject(i).getJSONObject("fields").getString("summary"));
//        	issue.setType(issues.getJSONObject(i).getJSONObject("fields").getJSONObject("issuetype").getString("name"));
//        	issue.setStatus(issues.getJSONObject(i).getJSONObject("fields").getJSONObject("status").getString("name"));
//        	IssueRepository.save(issue);
//        }
//	}
//}