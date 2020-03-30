package com.community.controller;

import com.community.mapper.QuestionMapper;
import com.community.mapper.UserMapper;
import com.community.model.Question;
import com.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller
public class PublishController {

    @Autowired
    QuestionMapper questionMapper;

    @Autowired
    UserMapper userMapper;

    @GetMapping("/publish")
    public String toPublish(HttpServletRequest request) {
        if (request.getSession().getAttribute("user") == null) {
            return "redirect:/";
        }
        return "publish";
    }

    @PostMapping("/publish")
    public String publish(@RequestParam(name="title") String title,
                          @RequestParam(name="description") String description,
                          @RequestParam(name="tag") String tag,
                          HttpServletRequest request,
                          Model model) {

        model.addAttribute("title", title);
        model.addAttribute("description", description);
        model.addAttribute("tag", tag);

        if (title == null || title == "") {
            model.addAttribute("error", "The title cann't be blank");
            return "publish";
        }
        if (description == null || description == "") {
            model.addAttribute("error", "The description cann't be blank");
            return "publish";
        }
        if (tag == null || tag == "") {
            model.addAttribute("error", "The tag cann't be blank");
            return "publish";
        }
        //verify if the user logined
        User user = null;
        Cookie[] cookies = request.getCookies();
        if (cookies != null && cookies.length > 0)
            for (Cookie cookie: cookies) {
                if (cookie.getName().equals("token")) {
                    String token = cookie.getValue();
                    user = userMapper.getUserByToken(token);
                    if (user != null) {
                        request.getSession().setAttribute("user", user);
                    }
                    break;
                }
            }
        if (user == null) {
            model.addAttribute("error", "Not login now!");
            return "publish";
        }

        Question question = new Question();
        question.setTitle(title);
        question.setTag(tag);
        question.setDescription(description);
        question.setCreator(Integer.valueOf(user.getId()));
        question.setGmtCreate(System.currentTimeMillis());
        question.setGmtModified(question.getGmtCreate());
        questionMapper.insert(question);

        return "redirect:/";
    }

}
