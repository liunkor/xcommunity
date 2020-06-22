package com.community.service;

import com.community.mapper.TopicMapper;
import com.community.model.Topic;
import com.community.model.TopicExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TopicService {
    @Autowired
    private TopicMapper topicMapper;

    public List<Topic> getAllTopics() {
        TopicExample topicExample = new TopicExample();
        List<Topic> topics = topicMapper.selectByExample(topicExample);
        return topics;
    }

    public String getTopicNameById(Integer id) {
        Topic topic = topicMapper.selectByPrimaryKey(id);
        if (topic != null) {
            return topic.getName();
        }
        return null;
    }
}
