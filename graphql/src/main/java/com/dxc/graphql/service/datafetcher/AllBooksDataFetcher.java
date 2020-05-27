package com.dxc.graphql.service.datafetcher;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import com.dxc.graphql.model.Book;
import com.dxc.graphql.repository.BookRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class AllBooksDataFetcher implements DataFetcher<List<Book>> {
	private static Logger logger = LoggerFactory.getLogger(AllBooksDataFetcher.class);
	
    private BookRepository bookRepository;
    
    @Autowired
    public AllBooksDataFetcher(BookRepository bookRepository) {
        this.bookRepository=bookRepository;
    }
    @Override
    public List<Book> get(DataFetchingEnvironment dataFetchingEnvironment) {
    	logger.info("addBooks DataFetcher is calling");
        return bookRepository.findAll();
    }
}
