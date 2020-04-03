package com.community.dto;

import com.community.model.Question;
import com.community.model.User;
import lombok.Data;

@Data
public class QuestionDTO {
        private Integer id;
        private String title;
        private String description;
        private String tag;
        private Long gmtCreate;
        private Long gmtModified;
        private Integer creator;
        private Integer viewCount;
        private Integer likeCount;
        private Integer commentCount;
        private User user;
}
