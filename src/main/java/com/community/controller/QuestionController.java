package com.community.controller;

import com.community.dto.CommentDTO;
import com.community.dto.PaginationDTO;
import com.community.dto.QuestionDTO;
import com.community.enums.CommentTypeEnum;
import com.community.mapper.QuestionMapper;
import com.community.model.Question;
import com.community.service.CommentService;
import com.community.service.QuestionService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private CommentService commentService;

    @GetMapping("/question/{id}")
    public String question(@PathVariable(name = "id") Long id,
                           Model model) {
        List<CommentDTO> comments = commentService.listByTargetId(id, CommentTypeEnum.QUESTION);
        // add view count
        questionService.incView(id);
        QuestionDTO questionDTO = questionService.getQuestionById(id);
        List<QuestionDTO> relatedQuestions = questionService.selectRelated(questionDTO);
        List<QuestionDTO> popularQuestions = questionService.selectPopular();
        model.addAttribute("questionDTO", questionDTO);
        model.addAttribute("comments", comments);
        model.addAttribute("relatedQuestions", relatedQuestions);
        model.addAttribute("popularQuestions", popularQuestions);
        return "question";
    }

}
