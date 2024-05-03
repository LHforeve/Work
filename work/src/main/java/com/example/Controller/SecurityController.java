package com.example.Controller;


import com.example.util.ValidCode;
import jakarta.annotation.security.DenyAll;
import jakarta.annotation.security.PermitAll;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.*;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.awt.image.BufferedImage;
import java.io.IOException;

@Controller
public class SecurityController {

    @GetMapping("/index") //首页的访问路径
    public String index(){
        return "index";
    }

    @Secured({"ROLE_user","ROLE_manager","ROLE_admin"}) //有三种角色之一可以访问
    @GetMapping("/menu1/{id}") //示例：访问menu1/1将返回menu1/1.html
    public String menu1(@PathVariable("id")int id){
        return "menu1/"+id;
    }

    @PreAuthorize("hasAnyRole('manager','admin')") //有二种角色之一可以访问
    @GetMapping("/menu2/{id}") //示例：访问menu1/1将返回menu2/1.html
    public String menu2(@PathVariable("id")int id){
        return "menu2/"+id;
    }

    @PreAuthorize("hasRole('admin')") //有admin角色可以访问
    @GetMapping("/menu3/{id}") //示例：访问menu1/1将返回menu3/1.html
    public String menu3(@PathVariable("id")int id){
        return "menu3/"+id;
    }

    @ResponseBody
    @PreAuthorize("hasAuthority('商品管理')") //有商品管理权限可以访问
    @GetMapping("/test1")
    public String test1(){
        return "商品管理hasAuthority";
    }

    @ResponseBody
    @PreAuthorize("hasAuthority('用户管理')") //有用户管理权限可以访问
    @GetMapping("/test2")
    public String test2(){
        return "用户管理hasAuthority";
    }


    @ResponseBody
    @DenyAll
    @GetMapping("/test3") //所有角色都不可访问
    public String test3(){
        return "DenyAll";
    }

    @ResponseBody
    @PermitAll
    @GetMapping("/test4") //所有角色都可访问
    public String test4(){
        return "PermitAll";
    }

    @GetMapping("/toLogin") //自定义登录页的访问路径
    public String toLogin(HttpServletRequest request){
        String msg= (String) request.getSession().getAttribute("msg");
        if(msg!=null){
            request.setAttribute("msg",msg);
        }
        return "login";
    }

    @GetMapping("/validcode")
    public void getValidPicture(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ValidCode validCode = new ValidCode();
        BufferedImage image = validCode.getImage();
        String validcode = validCode.getValidcode();//获取随机验证码(字符串)
        System.out.println("validcode:"+validcode);
        HttpSession session = request.getSession();
        session.setAttribute("validcode", validcode);//将随机验证码存入session
        validCode.output(image, response.getOutputStream());//输出图片
    }

    @GetMapping("/toLogin/error") //登录失败发生异常时的访问路径
    public String tologin(HttpServletRequest request, Model model){
        AuthenticationException authenticationException = (AuthenticationException) request.getSession()
                .getAttribute("SPRING_SECURITY_LAST_EXCEPTION");
        if (authenticationException instanceof UsernameNotFoundException || authenticationException instanceof BadCredentialsException) {
            model.addAttribute("msg","用户名或密码错误");
        } else if (authenticationException instanceof DisabledException) {
            model.addAttribute("msg","用户已被禁用");
        } else if (authenticationException instanceof LockedException) {
            model.addAttribute("msg","账户被锁定");
        } else if (authenticationException instanceof AccountExpiredException) {
            model.addAttribute("msg","账户过期");
        } else if (authenticationException instanceof CredentialsExpiredException) {
            model.addAttribute("msg","证书过期");
        }

        return "login";
    }
    @GetMapping("/errorRole")
    public String errorRole(){
        return "errorRole";
    }
}
