package com.community.mapper;

import com.community.model.User;
import com.community.provider.UserSqlProvider;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserMapper {
    @Insert("insert into user(name, account_id, token, gmt_create, gmt_modified) values(#{name}, #{accountId}, #{token}, #{gmtCreate}, #{gmtModified})")
    void insert(User user);

    @Results({
            @Result(property = "accountId", column = "account_id"),
            @Result(property = "gmtCreate", column = "gmt_create"),
            @Result(property = "gmtModified", column = "gmt_modifed")
    })
    @SelectProvider(type = UserSqlProvider.class, method = "getAllUser")
    List<User> getAllUser();

    @Results({
            @Result(property = "accountId", column = "account_id"),
            @Result(property = "gmtCreate", column = "gmt_create"),
            @Result(property = "gmtModified", column = "gmt_modifed")
    })
    @SelectProvider(type = UserSqlProvider.class, method = "getUserByToken")
    List<User> getUserByAccountId(String accountId);



}
