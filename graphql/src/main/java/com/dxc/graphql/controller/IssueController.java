package com.dxc.graphql.controller;

import com.dxc.graphql.service.IssueGraphQLService;
import graphql.ExecutionResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/rest/issues")
@RestController
public class IssueController {
    
    private IssueGraphQLService graphQLService;
    
    @Autowired
    public IssueController(IssueGraphQLService graphQLService) {
        this.graphQLService=graphQLService;
    }
    @PostMapping
    public ResponseEntity<Object> getAllProjects(@RequestBody String query){

        ExecutionResult execute = graphQLService.getGraphQL().execute(query);
        return new ResponseEntity<>(execute, HttpStatus.OK);
    }
}
