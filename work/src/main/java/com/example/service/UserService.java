package com.example.service;

import com.example.domain.Menu;
import com.example.domain.Role;
import com.example.domain.TUser;
import com.example.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        TUser tuser=userMapper.findUserByName(username);//从数据库中查询用户
        if(tuser==null){
            throw new UsernameNotFoundException("帐户不存在");
        }
        tuser.setRoles(userMapper.findRolesByUserId(tuser.getUid()));//从数据库中查询到该用户的所有角色（含权限）
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        for(Role role:tuser.getRoles()){//取出用户的角色，封装到authorities中
            authorities.add(new SimpleGrantedAuthority(role.getName()));
            for(Menu menu:role.getMenus()){ //如果权限精确到权限菜单级别，则要补充这个
                authorities.add(new SimpleGrantedAuthority(menu.getMenuname()));
            }
        }
        //下面的密码一般要加密
        return new User(tuser.getUsername(),new BCryptPasswordEncoder().encode(tuser.getPassword()),authorities);

    }
}
