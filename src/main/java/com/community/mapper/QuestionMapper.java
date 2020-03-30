package com.community.mapper;

import com.community.model.Question;
import com.community.model.User;
import com.community.provider.QuestionSqlProvider;
import com.community.provider.UserSqlProvider;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface QuestionMapper {

    @Insert("insert into question (title, description, gmt_create, gmt_modified, creator, tag) " +
            "values (#{title}, #{description}, #{gmtCreate}, #{gmtModified}, #{creator}, #{tag})")
    void insert(Question question);

    @Results({
            @Result(property = "gmtCreate", column = "gmt_create"),
            @Result(property = "gmtModified", column = "gmt_modified"),
            @Result(property = "viewCount", column = "view_count"),
            @Result(property = "likeCount", column = "like_count")
    })
    @SelectProvider(type = QuestionSqlProvider.class, method = "getQuestionById")
    Question getQuestionById(Integer id);

    @Results({
            @Result(property = "gmtCreate", column = "gmt_create"),
            @Result(property = "gmtModified", column = "gmt_modified"),
            @Result(property = "viewCount", column = "view_count"),
            @Result(property = "likeCount", column = "like_count")
    })
    @SelectProvider(type = QuestionSqlProvider.class, method = "getQuestions")
    List<Question> getQuestions();
}

