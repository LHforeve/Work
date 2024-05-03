package com.example.respository;

import com.example.domain.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BookDao {

    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    public void setValue(String key,String value){//存储字符串类型的key-value
        stringRedisTemplate.opsForValue().set(key,value);
    }

    public String getValue(String key){//通过键获取字符串类型的值
       return stringRedisTemplate.opsForValue().get(key);
    }

    public List<Book> findBooks(String id){ //查找所有书
        return (List<Book>) redisTemplate.opsForValue().get(id);
    }

    public void saveBooks(Object key,List<Book> books) { //保存书
        redisTemplate.opsForValue().set(key,books);
    }

    public Book findBookById(int id) {//通过id号查找一本书

        return (Book) redisTemplate.opsForValue().get(String.valueOf(id));
    }

    public void saveBook(Book book) { //保存书
        redisTemplate.opsForValue().set(String.valueOf(book.getId()),book);
    }


    public void deleteBook(int id) {
        redisTemplate.delete(String.valueOf(id));
    }
}

