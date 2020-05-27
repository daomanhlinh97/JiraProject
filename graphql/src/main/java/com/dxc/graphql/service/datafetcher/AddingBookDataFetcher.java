package com.dxc.graphql.service.datafetcher;

import java.util.Date;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dxc.graphql.model.Book;
import com.dxc.graphql.repository.BookRepository;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;

@Component
public class AddingBookDataFetcher implements DataFetcher<Book> {
	private static Logger logger = LoggerFactory.getLogger(AddingBookDataFetcher.class);
	
	private BookRepository bookRepository;

	@Autowired
	public AddingBookDataFetcher(BookRepository bookRepository) {
		this.bookRepository = bookRepository;
	}

	@Override
	public Book get(DataFetchingEnvironment dataFetchingEnvironment) {
		logger.info("addBook DataFetcher is calling");
		
		String title = dataFetchingEnvironment.getArgument("title");
		String publisher = dataFetchingEnvironment.getArgument("publisher");
		String author = dataFetchingEnvironment.getArgument("author");
		
		Random rand = new Random();
		
		Book newBook = new Book();
		newBook.setIsn(String.valueOf(rand.nextInt(1000)));
    	newBook.setTitle(title);
    	newBook.setPublisher(publisher);
    	newBook.setPublishedDate((new Date()).toString());
    	newBook.setAuthor(new String[] {author});
    	
    	bookRepository.save(newBook);
        return newBook;
	}
}
