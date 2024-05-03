package com.example.medis;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("yaoping")
public class Medi {
    @TableId(type= IdType.AUTO)
    private int id;
    private String mediname;
    private String shuxing;
    private String username;
    private String address;

}