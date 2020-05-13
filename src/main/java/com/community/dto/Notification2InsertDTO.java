package com.community.dto;

import com.community.enums.NotificationTypeEnum;
import com.community.model.Comment;
import lombok.Data;

@Data
public class Notification2InsertDTO {
    private Comment comment;
    private Long receiver;
    private String notifier;
    private String outerTitle;
    private Integer notificationType;
    private Long outerId;
}
