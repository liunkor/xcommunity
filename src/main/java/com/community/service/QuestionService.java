package com.community.service;

import com.community.dto.PaginationDTO;
import com.community.dto.QuestionDTO;
import com.community.mapper.QuestionMapper;
import com.community.mapper.UserMapper;
import com.community.model.Question;
import com.community.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private UserMapper userMapper;

    public PaginationDTO list(Integer page, Integer size) {
        /**
         * page: the number of the current page;
         * size: the number of questions in one page;
         * totalCount: the total number of questions in DB.
         */
        PaginationDTO paginationDTO = new PaginationDTO();
        Integer totalCount = questionMapper.count();
        paginationDTO.setPagination(totalCount, page, size);

        //size * (page - 1)
        Integer offset = size * (paginationDTO.getPage() - 1);
        List<Question> questions = questionMapper.getQuestions(offset, size);
        if (questions != null) {
            List<QuestionDTO> questionDTOList = new ArrayList<>();
            for (Question question: questions) {
                User user = userMapper.getUserById(question.getCreator());
                QuestionDTO questionDTO = new QuestionDTO();
                BeanUtils.copyProperties(question, questionDTO);
                questionDTO.setUser(user);
                questionDTOList.add(questionDTO);
            }
            paginationDTO.setQuestions(questionDTOList);
        }

        return paginationDTO;
    }

    public PaginationDTO listByUserId(Integer userId, Integer page, Integer size) {
        /**
         * page: the number of the current page;
         * size: the number of questions in one page;
         * totalCount: the total number of questions in DB.
         */
        PaginationDTO paginationDTO = new PaginationDTO();
        Integer totalCount = questionMapper.countByUserId(userId);
        paginationDTO.setPagination(totalCount, page, size);

        //size * (page - 1)
        Integer offset = size * (paginationDTO.getPage() - 1);
        List<Question> questions = questionMapper.getQuestionsByUserId(userId, offset, size);
        User user = userMapper.getUserById(userId);
        if (questions != null) {
            List<QuestionDTO> questionDTOList = new ArrayList<>();
            for (Question question: questions) {
                QuestionDTO questionDTO = new QuestionDTO();
                BeanUtils.copyProperties(question, questionDTO);
                questionDTO.setUser(user);
                questionDTOList.add(questionDTO);
            }
            paginationDTO.setQuestions(questionDTOList);
        }

        return paginationDTO;
    }

    public QuestionDTO getQuestionById(Integer id) {

        QuestionDTO questionDTO = new QuestionDTO();
        Question question = questionMapper.getQuestionById(id);
        User user = userMapper.getUserById(id);
        BeanUtils.copyProperties(question, questionDTO);
        questionDTO.setUser(user);

        return questionDTO;
    }
}
