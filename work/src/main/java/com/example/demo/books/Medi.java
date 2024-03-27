package com.example.demo.books;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("book")
public class Medi {
    @TableId(value="id",type= IdType.AUTO)
    private Integer id;

    @TableField("name")
    private String name;
    private double price;
    private String gcategory;
    private int pnum;
    private String imgurl;
    private String description;
    private String author;
    private int sales;
}