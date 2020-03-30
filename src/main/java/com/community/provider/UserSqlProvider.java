package com.community.provider;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

public class UserSqlProvider {

    /**
     * Method 1: write raw sql
     */
    public String getAllUser() {
        return "select * from user";
    }

    /**
     * Method 2: construct sql by using API
     */
    public String getUserById(@Param("id") String id) {
        return new SQL() {
            {
                SELECT("*");
                FROM("user");
                if (id != null) {
                    WHERE("id = #{id}");
                }  else {
                    WHERE("1=2");
                }
            }
        }.toString();
    }

    public String getUserByToken(@Param("token") String token) {
        return new SQL() {
            {
                SELECT("*");
                FROM("user");
                if (token != null) {
                    WHERE("token = #{token}");
                } else {
                    WHERE("1=2");
                }
            }
        }.toString();
    }

    public String deleteUserByToken(@Param("token") String token) {
        return new SQL() {
            {
                DELETE_FROM("user");
                if (token != null) {
                    WHERE("token = #{token}");
                } else {
                    WHERE("1=2");
                }
            }
        }.toString();
    }
}