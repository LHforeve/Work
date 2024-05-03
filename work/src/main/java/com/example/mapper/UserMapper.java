package com.example.mapper;

import com.example.domain.Role;
import com.example.domain.TUser;
import com.example.domain.Menu;
import com.example.domain.Role;
import com.example.domain.TUser;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
@Mapper
public interface UserMapper {
    TUser findUserByName(String username); //根据姓名查找用户User
    List<Role> findRolesByUserId(int id); //根据用户id查找角色Role集合，角色中又包含权限
}
