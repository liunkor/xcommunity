package com.community.cache;

import com.community.dto.TagDTO;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class TagCache {
    public static List<TagDTO> get() {
        List<TagDTO> tagDTOS = new ArrayList<>();
        TagDTO program = new TagDTO();
        program.setCategoryName("Developer-Language");
        program.setTags(Arrays.asList("Javascript", "Php", "CSS", "HTML", "HTML5", "Java", "Node.js", "Python", "C++",
                "C", "Golang", "Objective-C", "typescript", "shell", "swift", "c#", "sass", "ruby", "bash", "less",
                "asp.net", "lua", "scala", "coffeescript", "actionscript", "rust", "erlang", "perl"));
        tagDTOS.add(program);

        TagDTO framework = new TagDTO();
        framework.setCategoryName("Frameworks");
        framework.setTags(Arrays.asList("laravel", "Spring", "express", "Django", "flask", "yii", "ruby-on-rails", "tornado", "koa", "struts"));
        tagDTOS.add(framework);

        TagDTO server = new TagDTO();
        server.setCategoryName("Server");
        server.setTags(Arrays.asList("linux", "Nginx", "Docker", "Apache", "Ubuntu", "CentOS", "Tomcat", "Unix", "Hadoop", "Windows-server"));
        tagDTOS.add(server);

        TagDTO db = new TagDTO();
        db.setCategoryName("Database");
        db.setTags(Arrays.asList("MySQL", "Redis", "MongoDB", "SQL", "Oracle", "nosql memcached", "SQLServer", "Postgresql", "Sqlite"));
        tagDTOS.add(db);

        TagDTO tool = new TagDTO();
        tool.setCategoryName("Tools");
        tool.setTags(Arrays.asList("Git", "Github", "visual-studio-code", "vim", "sublime-text", "xCode", "intellij-idea", "eclipse", "maven",
                "ide", "svn", "visual-studio", "atom emacs", "textmate", "hg"));
        tagDTOS.add(tool);

        return tagDTOS;
    }

    public static String  isValid(String tags) {
        String[] split = StringUtils.split(tags, ",");
        List<TagDTO> tagDTOS = get();

        List<String> tagList = tagDTOS.stream().flatMap(tag -> tag.getTags().stream()).collect(Collectors.toList());
        String invalid = Arrays.stream(split).filter(t -> !tagList.contains(t)).collect(Collectors.joining(","));
        return invalid;

    }
}
