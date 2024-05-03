package com.example.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Menu {
    private Integer mid;//权限编号
    private String menuname;//权限名称
    private String url; //该权限对应能访问的URL
    private List<Role> roles; //拥有该权限的所有角色
}

