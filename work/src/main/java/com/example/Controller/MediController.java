package com.example.Controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.domain.Book;
import com.example.medis.Medi;
import com.example.medis.Result;
import com.example.service.BookService;
import com.example.service.IMediService;
import com.example.medis.Result;
import com.example.service.IMediService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/Medis")
public class MediController {
    @Autowired
    IMediService Mediservice;

//    @GetMapping
//    public Result<?> findALlMedis(){
//        List<Medi>  medis = Mediservice.list();
//        return Result.success(medis);
//    }

    @GetMapping
    public Result<?> findPage(@RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "5") Integer pageSize, @RequestParam(defaultValue = "", required = false) String search) {
        System.out.println("search:" + search);
        Page<Medi> page = Mediservice.page(new Page(pageNum, pageSize), Wrappers.<Medi>lambdaQuery().like(Medi::getMediname, search));
        return Result.success(page);
    }

    @PostMapping
    public Result<?> save(@RequestBody Medi medi) {
        Mediservice.save(medi);
        return Result.success();
    }

    @PutMapping
    public Result<?> update(@RequestBody Medi medi) {
        Mediservice.updateById(medi);
        return Result.success();
    }

    @DeleteMapping("/(id)")
    public Result<?> delete(@PathVariable long id) {
        Mediservice.removeById(id);
        return Result.success();
    }

    @PostMapping("/refund/{id}")
    public Result<?> refund(@PathVariable int id) {
        boolean refunded = Mediservice.list(id);
        if (refunded) {
            return Result.success();
        } else {
            return Result.error("退单失败");
        }
    }

    @DeleteMapping("/delete_refunded")
    public Result<?> deleteRefundedMedis() {
        Mediservice.deleteRefundedMedis();
        return Result.success();
    }

    @Controller
    public class BookController {

        @Autowired
        private BookService bookService;

        @ResponseBody
        @GetMapping("/setValue")
        public String setVlaue() { //存储字符串类型的key-value
            bookService.setValue("redis", "Spring Date Redis");
            return "保存键值对成功！";
        }

        @ResponseBody
        @GetMapping("/getValue")
        public String getValue() { //通过键获取字符串类型的值
            ModelAndView mv = new ModelAndView();
            return bookService.getValue("redis");

        }


        @GetMapping("/saveBook")
        @ResponseBody
        public String saveBook() {
            Book book = new Book(1, "C语言程序设计", 50.0, "计算机", 100, "101.jpg", "", "zhangsan", 50);
            bookService.saveBook(book);
            return "保存一本书成功！";
        }

        @GetMapping("/findBook")
        public ModelAndView findBookById() {
            ModelAndView mv = new ModelAndView();
            Book book = bookService.findBookById(1);
            mv.addObject("book", book);
            mv.setViewName("book");
            return mv;
        }

        @GetMapping("/saveBooks")
        @ResponseBody
        public String saveBooks() {
            Book book1 = new Book(1, "C语言程序设计", 50.0, "计算机", 100, "101.jpg", "", "zhangsan", 50);
            Book book2 = new Book(2, "java语言程序设计", 60.0, "计算机", 100, "102.jpg", "", "zhangsan", 50);
            Book book3 = new Book(3, "python语言程序设计", 70.0, "计算机", 100, "103.jpg", "", "zhangsan", 50);
            List<Book> books = new ArrayList<Book>();
            books.add(book1);
            books.add(book2);
            books.add(book3);
            bookService.saveBooks("101", books);
            return "保存多本书成功！";
        }

        @GetMapping("/findBookById/{id}")
        public ModelAndView findBookById(@PathVariable("id") int id) {
            ModelAndView mv = new ModelAndView();
            List<Book> books = bookService.findBooks("101");
            Book book = new Book();
            if (books != null && books.size() > 0) {
                book = books.get(id - 1);
            }
            mv.addObject("book", book);
            mv.setViewName("book");
            return mv;
        }

        @GetMapping("/findBooks")
        public ModelAndView findBooks() {
            ModelAndView mv = new ModelAndView();
            List<Book> books = bookService.findBooks("101");
            mv.addObject("books", books);
            mv.setViewName("books");
            return mv;
        }

        @GetMapping("/deleteBook/{id}")
        public ModelAndView deleteBook(@PathVariable("id") int id) {
            ModelAndView mv = new ModelAndView();
            bookService.deleteBook(id);
            List<Book> books = bookService.findBooks("101");
            books.remove(id - 1);
            bookService.saveBooks("101", books);
            mv.addObject("books", books);
            mv.setViewName("books");
            return mv;
        }


        @Autowired
        private RedisTemplate redisTemplate;


        @RequestMapping("/index") //访问首页功能。
        public String index(){
            return "index";
        }

        @RequestMapping("/detail/{id}")  //更新浏览量，获取最新点赞数到页面
        public String detail(@PathVariable("id")int id, Model model) {
            //从redis中读取数据。数据结构是HashSet,以news_+id为键，分别以read字段存储阅读次数，zan字段存储点赞次数
            Map<String,String> map=redisTemplate.opsForHash().entries("news_"+id);
            String countRead=map.get("read");//获取阅读数。
            if(countRead==null||countRead.equals("")){
                countRead="1"; //如果之前没有阅读过，则阅读次数设置为1
            }else{
                countRead=(Integer.parseInt(countRead)+1)+"";//如果有阅读过，则阅读次数加1
            }
            map.put("read",countRead);//更新hashmap
            redisTemplate.opsForHash().putAll("news_"+id,map);//更新数据到redis
            model.addAllAttributes(map); //数据放入model域，前端就能展示。
            return "news"+id;

        }

        @RequestMapping("/addZan")
        @ResponseBody
        public String addZan(int id){ //点赞功能
            Map<String,String> map=redisTemplate.opsForHash().entries("news_"+id);

            String  countZan= map.get("zan"); //取出原来的赞数
            if(countZan==null||countZan.equals("")){
                countZan="1"; //如果没有赞过，则赞数为1
            }else{
                countZan=(Integer.parseInt(countZan)+1)+""; //如果以前有赞过，则赞数加1
            }
            redisTemplate.opsForHash().put("news_"+id,"zan",countZan);//保存最新的赞数到redis
            return countZan;
        }

    }

}
