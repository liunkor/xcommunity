package com.community.mapper;

import com.community.dto.QuestionQueryDTO;
import com.community.model.Question;

import java.util.List;

public interface QuestionExtMapper {
    int incView(Question question);

    void incCommentCount(Question question);

    List<Question> selectRelated(Question question);

    Integer countBySearch(QuestionQueryDTO questionQueryDTO);

    List<Question> selectBySearch(QuestionQueryDTO questionQueryDTO);

    List<Question> selectPopular();
}