package com.community.service;

import com.community.dto.CommentDTO;
import com.community.enums.CommentTypeEnum;
import com.community.exception.CustomizedErrorCode;
import com.community.exception.CustomizedException;
import com.community.mapper.*;
import com.community.model.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CommentService {

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private CommentExtMapper commentExtMapper;

    @Autowired
    private QuestionExtMapper questionExtMapper;

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private UserMapper userMapper;

    @Transactional
    public void insert(Comment comment) {
        if (comment.getParentId() == null || comment.getParentId() == 0) {
            throw new CustomizedException(CustomizedErrorCode.TARGET_PARAM_NOT_FOUND);
        }
        if (comment.getType() == null || !CommentTypeEnum.isExist(comment.getType())) {
            throw new CustomizedException(CustomizedErrorCode.TYPE_PARAM_WRONG);
        }
        if (comment.getType() == CommentTypeEnum.COMMENT.getType()) {
            //reply to a comment (parentId is a comment's id)
            Comment dbComment = commentMapper.selectByPrimaryKey(comment.getParentId());
            if (dbComment == null) {
                throw new CustomizedException(CustomizedErrorCode.COMMENT_NOT_FOUND);
            }
            commentMapper.insert(comment);

            //increat the sub commentCount
            Comment parentComment = new Comment();
            parentComment.setId(comment.getParentId());
            parentComment.setCommentCount((long) 1);
            commentExtMapper.incCommentCount(parentComment);

        } else { // reply to a question (parentId is a question's id)
            Question question = questionMapper.selectByPrimaryKey(comment.getParentId());
            if (question == null) {
                throw new CustomizedException(CustomizedErrorCode.QUESTION_NOT_FOUND);
            }
            commentMapper.insert(comment);
            question.setCommentCount((long) 1);
            questionExtMapper.incCommentCount(question);
        }
    }

    public List<CommentDTO> listByTargetId(Long id, CommentTypeEnum typeEnum) {
        CommentExample commentExample = new CommentExample();
        commentExample.setOrderByClause("gmt_modified desc");
        commentExample.createCriteria()
                .andParentIdEqualTo(id)
                .andTypeEqualTo(typeEnum.getType());
        List<Comment> comments = commentMapper.selectByExample(commentExample);

        if (comments.size() == 0) {
            return new ArrayList<>();
        }

        // get the deduplicated commentors
        Set<Long> commentors = comments.stream().map(comment -> comment.getCommentor()).collect(Collectors.toSet());
        List<Long> userIds = new ArrayList();
        userIds.addAll(commentors);

        //get the commentors and convert to map
        UserExample userExample = new UserExample();
        userExample.createCriteria()
                .andIdIn(userIds);
        List<User> users = userMapper.selectByExample(userExample);
        Map<Long, User> userMap = users.stream().collect(Collectors.toMap(user -> user.getId(), user -> user));

        //Package each comment and it's commentor to a CommentDTO object.
        List<CommentDTO> commentDTOS = comments.stream().map(comment -> {
            CommentDTO commentDTO = new CommentDTO();
            BeanUtils.copyProperties(comment, commentDTO);
            commentDTO.setUser(userMap.get(comment.getCommentor()));
            return commentDTO;
        }).collect(Collectors.toList());

        return commentDTOS;
    }
}
