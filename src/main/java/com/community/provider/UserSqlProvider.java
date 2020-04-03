package com.community.provider;

import com.community.model.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
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
    public String getUserById(@Param("id") Integer id) {
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

    public String getUserByAccountId(@Param("accountId") Long accountId) {
        return new SQL() {
            {
                SELECT("*");
                FROM("user");
                if (accountId != null) {
                    WHERE("account_id = #{accountId}");
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

    public String updateUser(@Param("user") User user,
                             @Param("id") Integer id ) {
        return new SQL() {
            {
                UPDATE("user");
                SET("token = #{user.token}, name = #{user.name}, " +
                        "avatar_url = #{user.avatarUrl}, gmt_modified = #{user.gmtModified}");
                if (user != null) {
                    WHERE("id = #{id}");
                } else {
                    WHERE("1=2");
                }
            }
        }.toString();
    }
}
