package com.community.controller;

import com.community.cache.TagCache;
import com.community.dto.QuestionDTO;
import com.community.model.Question;
import com.community.model.Topic;
import com.community.model.User;
import com.community.service.QuestionService;
import com.community.service.TopicService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class PublishController {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private TopicService topicService;

    @GetMapping("/publish")
    public String toPublish(HttpServletRequest request,
                            Model model) {
        List<Topic> topics = topicService.getAllTopics();
        model.addAttribute("topics", topics);
        model.addAttribute("tags", TagCache.get());
        if (request.getSession().getAttribute("user") == null) {
            return "redirect:/";
        }
        return "publish";
    }

    @PostMapping("/publish")
    public String publish(@RequestParam(name="title", required = false) String title,
                          @RequestParam(name="description", required = false) String description,
                          @RequestParam(name="tag", required = false) String tag,
                          @RequestParam(name = "id", required = false) Long id,
                          @RequestParam(name = "topic", required = false) Integer topicId,
                          HttpServletRequest request,
                          Model model) {

        model.addAttribute("title", title);
        model.addAttribute("description", description);
        model.addAttribute("tag", tag);
        model.addAttribute("tags", TagCache.get());
        model.addAttribute("topicId", topicId);
        List<Topic> topics = topicService.getAllTopics();
        model.addAttribute("topics", topics);

        if (title == null || title == "") {
            model.addAttribute("error", "The title cann't be blank");
            return "publish";
        }
        if (description == null || description == "") {
            model.addAttribute("error", "The description cann't be blank");
            return "publish";
        }
        if (topicId == null || topicId == 0) {
            model.addAttribute("error", "Please elect a topic");
            return "publish";
        }
        if (tag == null || tag == "") {
            model.addAttribute("error", "The tag cann't be blank");
            return "publish";
        }

        String invalid = TagCache.isValid(tag);
        if (StringUtils.isNotBlank(invalid)) {
            model.addAttribute("error", "Invalid tag: " + invalid);
            return "publish";
        }

        //verify if the user logined
        User user = (User)request.getSession().getAttribute("user");
        if (user == null) {
            model.addAttribute("error", "Not login now!");
            return "publish";
        }

        Question question = new Question();
        question.setTitle(title);
        question.setTag(tag);
        question.setDescription(description);
        question.setCreator(user.getId());
        question.setGmtCreate(System.currentTimeMillis());
        question.setGmtModified(question.getGmtCreate());
        question.setId(id);
        question.setTopic(topicId);
        questionService.createOrUpdate(question);

        return "redirect:/";
    }

    @GetMapping("/publish/{id}")
    public String edit(@PathVariable(name = "id") Long id,
                       Model model) {

        QuestionDTO question = questionService.getQuestionById(id);
        model.addAttribute("title", question.getTitle());
        model.addAttribute("description", question.getDescription());
        model.addAttribute("tag", question.getTag());
        model.addAttribute("id", id);
        model.addAttribute("tags", TagCache.get());
        model.addAttribute("topicId", question.getTopic().getId());
        List<Topic> topics = topicService.getAllTopics();
        model.addAttribute("topics", topics);
        return "publish";
    }
}
