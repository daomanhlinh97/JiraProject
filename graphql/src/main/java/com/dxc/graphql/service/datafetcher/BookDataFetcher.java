package com.dxc.graphql.service.datafetcher;

import graphql.schema.DataFetcher;
import com.dxc.graphql.model.Book;
import com.dxc.graphql.repository.BookRepository;
import graphql.schema.DataFetchingEnvironment;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BookDataFetcher implements DataFetcher<Book> {
	private static Logger logger = LoggerFactory.getLogger(BookDataFetcher.class);
	
    private BookRepository bookRepository;
    
    @Autowired
    public BookDataFetcher(BookRepository bookRepository){
        this.bookRepository = bookRepository;
    }
    
    @Override
    public Book get(DataFetchingEnvironment dataFetchingEnvironment) {
    	logger.info("book DataFetcher is calling");
    	
        String isn = dataFetchingEnvironment.getArgument("id");
        return bookRepository.findById(isn).orElse(null);
    }
}
