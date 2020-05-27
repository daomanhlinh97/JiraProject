package com.dxc.graphql.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dxc.graphql.model.Book;

public interface BookRepository extends JpaRepository<Book, String> {

}
