package com.community.provider;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.jdbc.SQL;

public class QuestionSqlProvider {

    public String getQuestionById(@Param("id") Integer id) {
        return new SQL() {
            {
                SELECT("*");
                FROM("question");
                if (id != null) {
                    WHERE("id = #{id}");
                } else {
                    WHERE("1=2");
                }
            }
        }.toString();
    }

    public String getQuestions() {
        return "select * from question";
    }
}
