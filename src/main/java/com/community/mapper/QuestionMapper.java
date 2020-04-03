package com.community.mapper;

import com.community.model.Question;
import com.community.model.User;
import com.community.provider.QuestionSqlProvider;
import com.community.provider.UserSqlProvider;
import org.apache.ibatis.annotations.*;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Mapper
public interface QuestionMapper {

    @Insert("insert into question (title, description, gmt_create, gmt_modified, creator, tag) " +
            "values (#{title}, #{description}, #{gmtCreate}, #{gmtModified}, #{creator}, #{tag})")
    void insert(Question question);

    @SelectProvider(type = QuestionSqlProvider.class, method = "getQuestionById")
    Question getQuestionById(Integer id);

    @SelectProvider(type = QuestionSqlProvider.class, method = "getQuestions")
    List<Question> getQuestions(Integer offset, Integer size);

    @Select("select count(1) from question")
    Integer count();

    @Select("select count(1) from question where creator = #{userId}")
    Integer countByUserId(@Param("userId") Integer userId);

    @Select("select * from question where creator = #{userId} limit #{offset}, #{size}")
    List<Question> getQuestionsByUserId(@Param("userId") Integer userId,
                                        @Param("offset") Integer offset,
                                        @Param("size") Integer size);
}

