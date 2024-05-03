package com.example.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Role {
    private int rid; //角色编号
    private String name; //角色英文名称
    private String nameChinese; //角色中文名称
    private List<Menu> menus;//一个角色可能有多个权限（菜单）
}
