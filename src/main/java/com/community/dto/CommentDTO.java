package com.community.dto;

import com.community.model.User;
import lombok.Data;

@Data
public class CommentDTO {
    private Long id;

    private Long parentId;

    private Integer type;

    private Long commentor;

    private Long gmtCreate;

    private Long gmtModified;

    private Long likeCount;

    private Long commentCount;

    private String content;

    private User user;
}
