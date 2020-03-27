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
    public String getUserByAccountId(@Param("accountId") String accountId) {
        return new SQL() {
            {
                SELECT("*");
                FROM("user");
                if (accountId != null) {
                    WHERE("account_id = #{accountId}");
                } /** else {
                    WHERE("1=2");
                } **/
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
                }
            }
        }.toString();
    }

    public String deleteUserByToken(@Param("token") String token) {
        return new SQL() {
            {
                DELETE_FROM("user");
                WHERE("token = #{token}");
            }
        }.toString();
    }
}
