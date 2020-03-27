package com.community.controller;

import com.community.mapper.UserMapper;
import com.community.model.User;
import jdk.jfr.Frequency;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller
public class IndexController {

    @Autowired
    UserMapper userMapper;

    @GetMapping("/")
    public String index(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies.length > 0) {
            for (Cookie cookie: cookies) {
                if(cookie.getName().equals("token")) {
                    String token = cookie.getValue();
                    User user = userMapper.getUserByToken(token);
                    if (user != null) {
                        request.getSession().setAttribute("user", user);
                        System.out.println("Login succeed! " + user);
                    }
                    break;
                }
            }
        }
        return "index";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, String token) throws ServletException {
        if (token != null) {
            userMapper.deleteUserByToken(token);
        }
        request.getSession().removeAttribute("user");
        return "redirect:/";
    }
}
