package com.community.mapper;

import com.community.model.User;
import com.community.provider.UserSqlProvider;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserMapper {
    @Insert("insert into user(name, account_id, token, gmt_create, gmt_modified, avatar_url) " +
            "values(#{name}, #{accountId}, #{token}, #{gmtCreate}, #{gmtModified}, #{avatarUrl})")
    void insert(User user);

    @Results({
            @Result(property = "accountId", column = "account_id"),
            @Result(property = "gmtCreate", column = "gmt_create"),
            @Result(property = "gmtModified", column = "gmt_modifed"),
            @Result(property = "avatarUrl", column = "avatar_url")
    })
    @SelectProvider(type = UserSqlProvider.class, method = "getAllUser")
    List<User> getAllUser();

    @Results({
            @Result(property = "accountId", column = "account_id"),
            @Result(property = "gmtCreate", column = "gmt_create"),
            @Result(property = "gmtModified", column = "gmt_modifed"),
            @Result(property = "avatarUrl", column = "avatar_url")
    })
    @SelectProvider(type = UserSqlProvider.class, method = "getAllUser")
    User getUserById(Integer id);

    @Results({
            @Result(property = "accountId", column = "account_id"),
            @Result(property = "gmtCreate", column = "gmt_create"),
            @Result(property = "gmtModified", column = "gmt_modified"),
            @Result(property = "avatarUrl", column = "avatar_url")
    })
    @SelectProvider(type = UserSqlProvider.class, method = "getUserByToken")
    User getUserByToken(String token);

    @DeleteProvider(type = UserSqlProvider.class, method = "deleteUserByToken")
    boolean deleteUserByToken(String token);
}
