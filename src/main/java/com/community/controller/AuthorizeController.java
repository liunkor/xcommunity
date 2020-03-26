package com.community.controller;

import com.community.dto.AccessTokenDTO;
import com.community.dto.GithupUser;
import com.community.mapper.UserMapper;
import com.community.model.User;
import com.community.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Controller
public class AuthorizeController {

    @Autowired
    private GithubProvider githubProvider;

    @Value("${github.client.id}")
    private String clientId;

    @Value("${github.client.secret}")
    private String clientSecret;

    @Value("${git.redirect.uri}")
    private String redirectUri;

    @Autowired
    private UserMapper userMapper;

    @GetMapping("/callback")
    public String callback(@RequestParam(name="code") String code,
                           @RequestParam(name="state") String state,
                           HttpServletRequest request) throws IOException {
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setCode(code);
        accessTokenDTO.setRedirect_uri(redirectUri);
        accessTokenDTO.setClient_id(clientId);
        accessTokenDTO.setClient_secret(clientSecret);
        accessTokenDTO.setState(state);
        //visit https://github.com/login/oauth/access_token to get access_token
        String accessToken = githubProvider.getAccessToken(accessTokenDTO);
        //vist https://api.github.com/user?access_token=accessToken to get User
        GithupUser githupUser = githubProvider.getUser(accessToken);

        if (githupUser != null ) {
            request.getSession().setAttribute("user", githupUser);
            User user = new User();
            user.setToken(UUID.randomUUID().toString());
            user.setName(githupUser.getName());
            user.setAccountId(String.valueOf(githupUser.getId()));
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());
            userMapper.insert(user);
            List<User> users = userMapper.getUserByAccountId(user.getToken());
            for (User u: users) {
                System.out.println(u);
            }
            System.out.println();
            return "redirect:/";
        } else {
            return "redirect:/";
        }
    }
}
