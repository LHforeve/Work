package com.example.config;

import com.example.filter.ValidCodeFilter;
import com.example.service.UserService;
import com.example.filter.ValidCodeFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true,securedEnabled = true,jsr250Enabled = true)
public class WebSecurityConfig {

    @Autowired
    ValidCodeFilter validCodeFilter;


    @Autowired
    UserDetailsService userService;

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
        //如果不想加密就返回
//        return NoOpPasswordEncoder.getInstance();
    }

    //静态资源直接放行
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        //忽略这些静态资源（不拦截）
        return (web) -> web.ignoring().requestMatchers("/js/**", "/css/**","/images/**");
    }


    @Bean
    SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        //先验证验证码
        httpSecurity.addFilterBefore(validCodeFilter, UsernamePasswordAuthenticationFilter.class);
        httpSecurity.authorizeRequests() //开启登录配置
                .requestMatchers("/","/index","/validcode").permitAll() //允许直接访问的路径
                .anyRequest().authenticated();//其他任何请求都必须经过身份验证

        httpSecurity.formLogin()//开启表单验证
                .loginPage("/toLogin")//跳转到自定义的登录页面
                .usernameParameter("username")//自定义表单的用户名的name,默认为username
                .passwordParameter("password")//自定义表单的密码的name,默认为password
                .loginProcessingUrl("/login")//表单请求的地址，使用Security定义好的/login，并且与自定义表单的action一致
                .failureUrl("/toLogin/error")//如果登录失败跳转到
                .permitAll();//允许访问登录有关的路径
        //开启注销
        httpSecurity.logout().logoutSuccessUrl("/index");//注销后跳转到index页面
        httpSecurity.csrf().disable();//关闭csrf
        //没有权限时跳转页
        httpSecurity.exceptionHandling().accessDeniedPage("/errorRole");
        httpSecurity.rememberMe(); //记住我
        httpSecurity.userDetailsService(userService);
        return httpSecurity.build();

    }



}

