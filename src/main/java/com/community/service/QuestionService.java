package com.community.service;

import com.community.dto.PaginationDTO;
import com.community.dto.QuestionDTO;
import com.community.mapper.QuestionMapper;
import com.community.mapper.UserMapper;
import com.community.model.Question;
import com.community.model.QuestionExample;
import com.community.model.User;
import com.community.model.UserExample;
import org.apache.ibatis.session.RowBounds;
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
        Integer totalCount = (int) questionMapper.countByExample(new QuestionExample());
        paginationDTO.setPagination(totalCount, page, size);

        //size * (page - 1)
        Integer offset = size * (paginationDTO.getPage() - 1);
        List<Question> questions = questionMapper.selectByExampleWithRowbounds(
                new QuestionExample(),
                new RowBounds(offset, size));
        if (questions != null) {
            List<QuestionDTO> questionDTOList = new ArrayList<>();
            for (Question question: questions) {
                User user = userMapper.selectByPrimaryKey(question.getCreator());
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
        QuestionExample questionExample = new QuestionExample();
        questionExample.createCriteria()
                .andIdEqualTo(userId);
        Integer totalCount = (int)questionMapper.countByExample(questionExample);
        paginationDTO.setPagination(totalCount, page, size);

        //size * (page - 1)
        Integer offset = size * (paginationDTO.getPage() - 1);
        QuestionExample questionExample2 = new QuestionExample();
        questionExample2.createCriteria()
                .andCreatorEqualTo(userId);
        List<Question> questions = questionMapper.selectByExampleWithRowbounds(
                questionExample2, new RowBounds(offset, size));
        User user = userMapper.selectByPrimaryKey(userId);
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
        Question question = questionMapper.selectByPrimaryKey(id);
        User user = userMapper.selectByPrimaryKey(question.getCreator());
        BeanUtils.copyProperties(question, questionDTO);
        questionDTO.setUser(user);

        return questionDTO;
    }

    public void createOrUpdate(Question question) {
        if (question.getId() == null) {
            question.setGmtCreate(System.currentTimeMillis());
            question.setGmtModified(question.getGmtCreate());
            questionMapper.insert(question);
        } else {
            Question updateQuestion = new Question();
            updateQuestion.setGmtModified(System.currentTimeMillis());
            updateQuestion.setTitle(question.getTitle());
            updateQuestion.setDescription(question.getDescription());
            updateQuestion.setTag(question.getTag());
            QuestionExample questionExample = new QuestionExample();
            questionExample.createCriteria()
                    .andIdEqualTo(question.getId());
            questionMapper.updateByExampleSelective(question, questionExample);
        }
    }
}
