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


    @SelectProvider(type = UserSqlProvider.class, method = "getAllUser")
    List<User> getAllUser();

    @SelectProvider(type = UserSqlProvider.class, method = "getAllUser")
    User getUserById(Integer id);

    @SelectProvider(type = UserSqlProvider.class, method = "getUserByToken")
    User getUserByToken(String token);

    @DeleteProvider(type = UserSqlProvider.class, method = "deleteUserByToken")
    boolean deleteUserByToken(String token);

    @SelectProvider(type = UserSqlProvider.class, method = "getUserByAccountId")
    User getUserByAccountId(Long id);

    @UpdateProvider(type = UserSqlProvider.class, method = "updateUser")
    void updateUser(User user, Integer id);
}
