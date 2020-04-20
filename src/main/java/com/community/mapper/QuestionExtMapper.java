package com.community.mapper;

import com.community.model.Question;

public interface QuestionExtMapper {
    int incView(Question question);

    void incCommentCount(Question question);
}