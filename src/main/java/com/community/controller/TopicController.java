package com.community.controller;

import com.community.dto.PaginationDTO;
import com.community.dto.QuestionDTO;
import com.community.model.Topic;
import com.community.service.QuestionService;
import com.community.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class TopicController {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private TopicService topicService;

    @GetMapping("/topic/{id}")
    public String topic(@PathVariable(name = "id") Integer id,
                        @RequestParam(name = "page", defaultValue = "1") Integer page,
                        @RequestParam(name = "size", defaultValue = "10") Integer size,
                        Model model) {

        //get the pagination data
        PaginationDTO pagination = questionService.listByTopic(id, page, size);
        model.addAttribute("pagination", pagination);
        List<QuestionDTO> popularQuestions = questionService.selectPopular();
        model.addAttribute("popularQuestions", popularQuestions);
        model.addAttribute("id", id);
        model.addAttribute("topicName", topicService.getTopicNameById(id));
        return "topic";
    }
}
