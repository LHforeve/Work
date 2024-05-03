package com.example.service;

import com.example.domain.Book;
import com.example.respository.BookDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    @Autowired
    private BookDao bookDao;

    public String getValue(String key){
        return bookDao.getValue(key);
    }

    public void setValue(String key,String value){
        bookDao.setValue(key,value);
    }

    public List<Book> findBooks(String id){
        return bookDao.findBooks(id);
    }

    public void saveBooks(Object key,List<Book> books) {
        bookDao.saveBooks(key,books);
    }

    public Book findBookById(int id) {

        return (Book) bookDao.findBookById(id);
    }

    public void saveBook(Book book) {
        bookDao.saveBook(book);
    }


    public void deleteBook(int id) {
        bookDao.deleteBook(id);
    }
}
