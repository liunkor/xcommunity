package com.community.dto;

import com.community.model.Question;
import com.community.model.User;
import lombok.Data;

@Data
public class QuestionDTO {
        private Long id;
        private String title;
        private String description;
        private String tag;
        private Long gmtCreate;
        private Long gmtModified;
        private Long creator;
        private Long viewCount;
        private Long likeCount;
        private Long commentCount;
        private User user;
}
