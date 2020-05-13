package com.community.service;

import com.community.dto.PaginationDTO;
import com.community.dto.QuestionDTO;
import com.community.exception.CustomizedErrorCode;
import com.community.exception.CustomizedException;
import com.community.mapper.QuestionExtMapper;
import com.community.mapper.QuestionMapper;
import com.community.mapper.UserMapper;
import com.community.model.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class QuestionService {

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private QuestionExtMapper questionExtMapper;

    public PaginationDTO list(Integer page, Integer size) {
        /**
         * page: the number of the current page;
         * size: the number of questions in one page;
         * totalCount: the total number of questions in DB.
         */
        PaginationDTO<QuestionDTO> paginationDTO = new PaginationDTO<>();
        Integer totalCount = (int) questionMapper.countByExample(new QuestionExample());
        paginationDTO.setPagination(totalCount, page, size);

        //size * (page - 1)
        Integer offset = size * (paginationDTO.getPage() - 1);
        QuestionExample questionExample = new QuestionExample();
        questionExample.setOrderByClause("gmt_modified desc"); // order by gmtModified desc
        List<Question> questions = questionMapper.selectByExampleWithRowbounds(
                questionExample,
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
            paginationDTO.setData(questionDTOList);
        }

        return paginationDTO;
    }

    public PaginationDTO listByUserId(Long userId, Integer page, Integer size) {
        /**
         * page: the number of the current page;
         * size: the number of questions in one page;
         * totalCount: the total number of questions in DB.
         */
        PaginationDTO paginationDTO = new PaginationDTO();
        QuestionExample questionExample = new QuestionExample();
        questionExample.createCriteria()
                .andCreatorEqualTo(userId);
        Integer totalCount = (int)questionMapper.countByExample(questionExample);
        paginationDTO.setPagination(totalCount, page, size);

        //size * (page - 1)
        Integer offset = size * (paginationDTO.getPage() - 1);
        QuestionExample questionExample2 = new QuestionExample();
        questionExample2.setOrderByClause("gmt_modified desc");
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
            paginationDTO.setData(questionDTOList);
        }

        return paginationDTO;
    }

    public QuestionDTO getQuestionById(Long id) {

        Question question = questionMapper.selectByPrimaryKey(id);
        if (question == null) {
            throw new CustomizedException(CustomizedErrorCode.QUESTION_NOT_FOUND);
        }
        QuestionDTO questionDTO = new QuestionDTO();
        User user = userMapper.selectByPrimaryKey(question.getCreator());
        BeanUtils.copyProperties(question, questionDTO);
        questionDTO.setUser(user);

        return questionDTO;
    }

    public void createOrUpdate(Question question) {
        if (question.getId() == null) {
            question.setGmtCreate(System.currentTimeMillis());
            question.setGmtModified(question.getGmtCreate());
            question.setViewCount((long) 0);
            question.setCommentCount((long) 0);
            question.setLikeCount((long) 0);
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
            int updated = questionMapper.updateByExampleSelective(question, questionExample);
            if (updated != 1) {
                throw new CustomizedException(CustomizedErrorCode.QUESTION_NOT_FOUND);
            }
        }
    }

    public void incView(Long id) {
        Question question = new Question();
        question.setId(id);
        question.setViewCount((long) 1);
        questionExtMapper.incView(question);

    }

    /**
     * select reated quesiton of the given question
     * @param queryDTO
     * @return
     */
    public List<QuestionDTO> selectRelated(QuestionDTO queryDTO) {
        if (StringUtils.isBlank(queryDTO.getTag())) {
            return new ArrayList<>();
        }
        String[] tags = StringUtils.split(queryDTO.getTag(), ",");
        String regexpTag = Arrays.stream(tags).collect(Collectors.joining("|"));;
        Question question = new Question();
        question.setId(queryDTO.getId());
        question.setTag(regexpTag);

        List<Question> questions = questionExtMapper.selectRelated(question);
        //using stream and lambda to cast questions list into questionDTO list
        List<QuestionDTO> questionDTOS = questions.stream().map(p -> {
          QuestionDTO questionDTO = new QuestionDTO();
          BeanUtils.copyProperties(p, questionDTO);
          return questionDTO;
        }).collect(Collectors.toList());
        return questionDTOS;
    }

}
