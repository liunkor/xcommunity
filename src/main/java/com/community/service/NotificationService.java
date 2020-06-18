package com.community.service;

import com.community.dto.NotificationDTO;
import com.community.dto.PaginationDTO;
import com.community.enums.NotificationStatusEnum;
import com.community.enums.NotificationTypeEnum;
import com.community.exception.CustomizedErrorCode;
import com.community.exception.CustomizedException;
import com.community.mapper.NotificationMapper;
import com.community.mapper.UserMapper;
import com.community.model.*;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class NotificationService {

    @Autowired
    private NotificationMapper notificationMapper;

    @Autowired
    private UserMapper userMapper;

    /**
     * Find all notification with pagination by UserId
     * @param userId
     * @param page
     * @param size
     * @return
     */
    public PaginationDTO listByUserId(Long userId, Integer page, Integer size) {

        PaginationDTO<NotificationDTO> paginationDTO = new PaginationDTO<>();
        NotificationExample notificationExample = new NotificationExample();
        notificationExample.createCriteria()
                .andReceiverEqualTo(userId);
        Integer totalCount = (int)notificationMapper.countByExample(notificationExample);
        paginationDTO.setPagination(totalCount, page, size);

        //size * (page - 1)
        Integer offset = size * (paginationDTO.getPage() - 1);
        NotificationExample notificationExample2 = new NotificationExample();
        notificationExample2.setOrderByClause("gmt_create desc");
        notificationExample2.createCriteria()
                .andReceiverEqualTo(userId);
        List<Notification> notifications = notificationMapper.selectByExampleWithRowbounds(
                notificationExample2, new RowBounds(offset, size));
        if (notifications.size() == 0) {
            return paginationDTO;
        }

        ArrayList<NotificationDTO> notificationDTOS = new ArrayList<>();
        for (Notification notification: notifications) {
            NotificationDTO notificationDTO = new NotificationDTO();
            BeanUtils.copyProperties(notification, notificationDTO);
            notificationDTO.setTypeName(NotificationTypeEnum.nameOfType(notification.getType()));
            notificationDTOS.add(notificationDTO);
        }
        paginationDTO.setData(notificationDTOS);
        return paginationDTO;
    }

    /**
     * Get the count of unread notification
     * @param userid
     * @return
     */
    public Long getUnreadCount(Long userid) {
        NotificationExample notificationExample = new NotificationExample();
        notificationExample.createCriteria()
                .andReceiverEqualTo(userid)
                .andStatusEqualTo(NotificationStatusEnum.UNREAD.getStatus());
        Long unreadCount = notificationMapper.countByExample(notificationExample);
        return unreadCount;
    }

    /**
     * find a notification by id
     * @param id
     * @param user
     * @return
     */
    public NotificationDTO readById(Long id, User user) {
        Notification notification = notificationMapper.selectByPrimaryKey(id);
        if (notification == null) {
            throw new CustomizedException(CustomizedErrorCode.NOTIFICATION_NOT_FOUND);
        }
        if (!Objects.equals(notification.getReceiver(), user.getId())) {
            throw new CustomizedException(CustomizedErrorCode.READ_NOTIFICATION_FAIL);
        }
        //update the status from unread to read
        notification.setStatus(NotificationStatusEnum.READED.getStatus());
        notificationMapper.updateByPrimaryKey(notification);

        NotificationDTO notificationDTO = new NotificationDTO();
        BeanUtils.copyProperties(notification, notificationDTO);
        notificationDTO.setTypeName(NotificationTypeEnum.nameOfType(notification.getType()));
        return notificationDTO;
    }
}
