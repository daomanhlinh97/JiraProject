package com.dxc.graphql.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.dxc.graphql.model.Issue;

public interface IssueRepository extends JpaRepository<Issue, String> {
}
